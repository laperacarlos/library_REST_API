package com.crud.library.service;

import com.crud.library.domain.Book;
import com.crud.library.exceptions.BookNotFoundException;
import com.crud.library.repository.BookDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDao bookDao;

    public Book saveBook(final Book book) {
        return bookDao.save(book);
    }

    public Book getBookById(final Long bookId) throws Exception {
        return bookDao.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
    }
}
