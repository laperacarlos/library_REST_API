package com.crud.library.mapper;

import com.crud.library.domain.*;
import com.crud.library.exceptions.BookNotFoundException;
import com.crud.library.exceptions.UserNotFoundException;
import com.crud.library.repository.BookDao;
import com.crud.library.repository.UserDao;
import com.crud.library.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalMapper {
    private final BookDao bookDao;
    private final UserDao userDao;
    private final TimeProvider timeProvider;

    public Rental mapToRentalEntry(final RentalDto rentalDto) throws Exception {
        return new Rental(
                retrievedBook(rentalDto.getBookId()),
                retrievedUser(rentalDto.getUserId()),
                timeProvider.getTime()
        );
    }

    public RentalDto mapToRentalEntryDto(final Rental rental) {
        return new RentalDto(
                rental.getId(),
                rental.getBook().getId(),
                rental.getUser().getId(),
                rental.getRentalDate(),
                rental.getReturnDate()
        );
    }

    private Book retrievedBook(final Long bookId) throws Exception {
            return bookDao.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private User retrievedUser(final Long userId) throws Exception {
            return userDao.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
