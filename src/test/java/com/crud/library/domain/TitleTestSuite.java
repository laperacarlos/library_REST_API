package com.crud.library.domain;

import com.crud.library.exceptions.TitleNotFoundException;
import com.crud.library.repository.BookDao;
import com.crud.library.repository.TitleDao;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
public class TitleTestSuite {

    @Autowired
    TitleDao titleDao;

    @Autowired
    BookDao bookDao;

    @Test
    public void testCreateTitle() {
        //given
        Title title = new Title("title", "author", 2020);

        //when
        titleDao.save(title);

        //then
        assertNotEquals(0L, title.getId());

        //clean
        titleDao.deleteAll();
    }

    @Test
    public void testReadTitle() throws IOException {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);

        //when
        Long id = title.getId();
        Title titleFromDb = titleDao.findById(id).orElseThrow(IOException::new);

        //then
        assertEquals("title", titleFromDb.getTitle());
        assertEquals("author", titleFromDb.getAuthor());
        assertEquals(2020, titleFromDb.getPublicationYear());

        //clean
        titleDao.deleteAll();
    }

    @Test
    public void testUpdateTitle() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);

        //when
        title.setTitle("newTitle");
        title.setAuthor("newAuthor");
        title.setPublicationYear(2022);
        titleDao.save(title);

        //then
        assertEquals("newTitle", title.getTitle());
        assertEquals("newAuthor", title.getAuthor());
        assertEquals(2022, title.getPublicationYear());

        //clean
        bookDao.deleteAll();
        titleDao.deleteAll();
    }

    @Test
    public void testDeleteTitle() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);

        //when
        Long id = title.getId();
        titleDao.deleteById(id);

        //then
        assertFalse(titleDao.existsById(id));
    }

    @Test
    public void testRelationWithBook() throws Exception {
        //given
        Title title = new Title("title", "author", 2021);
        titleDao.save(title);
        Book book = new Book(title);
        bookDao.save(book);

        //when
        List<Book> books = titleDao.findById(title.getId()).orElseThrow(() -> new TitleNotFoundException(title.getId())).getBooks();
        //Hibernate.initialize(books);

        //then
        assertEquals(1, books.size());
    }
}
