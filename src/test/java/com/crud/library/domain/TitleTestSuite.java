package com.crud.library.domain;

import com.crud.library.repository.BookDao;
import com.crud.library.repository.TitleDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void testReadTitle() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);

        //when
        Long id = title.getId();
        Optional<Title> titleFromDb = titleDao.findById(id);

        //then
        assertTrue(titleFromDb.isPresent());

        //clean
        titleDao.deleteAll();
    }

    @Test
    public void testUpdateTitle() {
        //given
        Title title = new Title("title", "author", 2021);
        titleDao.save(title);
        Book book = new Book(title, Status.RENTED);
        bookDao.save(book);

        //when
        title.getBooks().add(book);
        titleDao.save(title);

        //then
        assertEquals(1, title.getBooks().size());

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
}
