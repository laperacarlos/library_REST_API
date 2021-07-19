package com.crud.library.domain;

import com.crud.library.exceptions.BookNotFoundException;
import com.crud.library.exceptions.TitleNotFoundException;
import com.crud.library.repository.BookDao;
import com.crud.library.repository.TitleDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        Book book = new Book(title);

        //when
        bookDao.save(book);

        //then
        assertNotEquals(0L, book.getId());

        //clean
        bookDao.deleteAll();
        titleDao.deleteAll();
    }

    @Test
    public void testReadBook() throws  BookNotFoundException{
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title);
        bookDao.save(book);

        //when
        Book bookFromDb = bookDao.findById(book.getId()).orElseThrow(() -> new BookNotFoundException(book.getId()));

        //then
        assertEquals("title", bookFromDb.getTitle().getTitle());
        assertEquals("author", bookFromDb.getTitle().getAuthor());
        assertEquals(2020, bookFromDb.getTitle().getPublicationYear());
        assertEquals("AVAILABLE", bookFromDb.getStatus().toString());

        //clean
        bookDao.deleteAll();
        titleDao.deleteAll();
    }

    @Test
    public void testUpdateBook() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title);
        bookDao.save(book);

        //when
        book.setStatus(Status.DESTROYED);
        bookDao.save(book);

        //then
        assertEquals("DESTROYED", book.getStatus().toString());

        //clean
        bookDao.deleteAll();
        titleDao.deleteAll();
    }

    @Test
    public void testDeleteBook() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title);
        bookDao.save(book);

        //when
        Long id = book.getId();
        bookDao.deleteById(id);

        //then
        assertFalse(bookDao.existsById(id));

        //clean
        titleDao.deleteAll();
    }

    @Test
    public void testRelationWithTitle() throws TitleNotFoundException {
        //given
        Title title = new Title("title", "author", 2021);
        titleDao.save(title);
        Book book = new Book(title);
        bookDao.save(book);

        //when
        List<Book> books = titleDao.findById(title.getId()).orElseThrow(() -> new TitleNotFoundException(title.getId())).getBooks();

        //then
        assertEquals(1, books.size());

        //clean
        bookDao.deleteAll();
        titleDao.deleteAll();
    }
}
