package com.crud.library.domain;

import com.crud.library.exceptions.TitleNotFoundException;
import com.crud.library.repository.BookDao;
import com.crud.library.repository.TitleDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void testReadTitle() throws TitleNotFoundException {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);

        //when
        Title titleFromDb = titleDao.findById(title.getId()).orElseThrow(() -> new TitleNotFoundException(title.getId()));

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
}
