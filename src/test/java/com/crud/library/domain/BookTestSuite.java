package com.crud.library.domain;

import com.crud.library.Status;
import com.crud.library.repository.BookDao;
import com.crud.library.repository.TitleDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
public class BookTestSuite {

    @Autowired
    TitleDao titleDao;

    @Autowired
    BookDao bookDao;

    @Test
    public void testCreateBook() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title, Status.AVAILABLE);

        //when
        bookDao.save(book);

        //then
        assertNotEquals(0L, book.getId());

        //clean
        bookDao.deleteAll();
        titleDao.deleteAll();
    }

    @Test
    public void testReadBook() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title, Status.AVAILABLE);
        bookDao.save(book);

        //when
        Long id = book.getId();
        Optional<Book> bookFromDb = bookDao.findById(id);

        //then
        assertTrue(bookFromDb.isPresent());

        //clean
        bookDao.deleteAll();
        titleDao.deleteAll();
    }

    @Test
    public void testUpdateBook() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title, Status.AVAILABLE);
        bookDao.save(book);

        //when
        book.setStatus(Status.DESTROYED);
        bookDao.save(book);

        //then
        assertEquals("destroyed", book.getStatus().getStatus());

        //clean
        bookDao.deleteAll();
        titleDao.deleteAll();
    }

    @Test
    public void testDeleteBook() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title, Status.AVAILABLE);
        bookDao.save(book);

        //when
        Long id = book.getId();
        bookDao.deleteById(id);

        //then
        assertFalse(bookDao.existsById(id));

        //clean
        titleDao.deleteAll();
    }
}
