package com.crud.library.mapper;

import com.crud.library.domain.Book;
import com.crud.library.domain.RentalEntry;
import com.crud.library.domain.RentalEntryDto;
import com.crud.library.domain.User;
import com.crud.library.exceptions.BookNotFoundException;
import com.crud.library.exceptions.UserNotFoundException;
import com.crud.library.repository.BookDao;
import com.crud.library.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalEntryMapper {
    private final BookDao bookDao;
    private final UserDao userDao;

    public RentalEntry mapToRentalEntry(final RentalEntryDto rentalEntryDto) throws Exception {
        return new RentalEntry(
                retrievedBook(rentalEntryDto.getBookId()),
                retrievedUser(rentalEntryDto.getUserId())
        );
    }

    public RentalEntryDto mapToRentalEntryDto(final RentalEntry rentalEntry) {
        return new RentalEntryDto(
                rentalEntry.getId(),
                rentalEntry.getBook().getId(),
                rentalEntry.getUser().getId(),
                rentalEntry.getRentalDate(),
                rentalEntry.getReturnDate()
        );
    }

    private Book retrievedBook(final Long bookId) throws BookNotFoundException {
        Optional<Book> bookFromDb = bookDao.findById(bookId);
        if (bookFromDb.isPresent()) {
            return bookFromDb.get();
        } else throw new BookNotFoundException();
    }

    private User retrievedUser(final Long userId) throws UserNotFoundException {
        Optional<User> userFromDb = userDao.findById(userId);
        if (userFromDb.isPresent()) {
            return userFromDb.get();
        } else throw new UserNotFoundException();
    }
}
