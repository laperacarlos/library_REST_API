package com.crud.library.service;

import com.crud.library.domain.Book;
import com.crud.library.domain.RentalEntry;
import com.crud.library.domain.Title;
import com.crud.library.domain.User;
import com.crud.library.exceptions.BookNotFoundException;
import com.crud.library.repository.BookDao;
import com.crud.library.repository.RentalEntryDao;
import com.crud.library.repository.TitleDao;
import com.crud.library.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {
    private final UserDao userDao;
    private final TitleDao titleDao;
    private final BookDao bookDao;
    private final RentalEntryDao rentalEntryDao;

    public Book saveBook(final Book book) {
        return bookDao.save(book);
    }

    public Book getBookById(final Long bookId) throws BookNotFoundException{
        Optional<Book> optionalBook = bookDao.findById(bookId);
        if(optionalBook.isPresent()) {
            return optionalBook.get();
        } else throw new BookNotFoundException();
    }

    public User saveUser(final User user) {
        return userDao.save(user);
    }

    public Title saveTitle(final Title title) {
        return titleDao.save(title);
    }

    public Optional<Title> getTitleById(final Long titleId) {
        return titleDao.findById(titleId);
    }

    public RentalEntry saveRentalEntry(final RentalEntry rentalEntry) {
        return rentalEntryDao.save(rentalEntry);
    }
}
