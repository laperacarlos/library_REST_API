package com.crud.library.domain;

import com.crud.library.Status;
import com.crud.library.repository.BookDao;
import com.crud.library.repository.RentalEntryDao;
import com.crud.library.repository.TitleDao;
import com.crud.library.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
public class RentalEntryTestSuite {

    @Autowired
    TitleDao titleDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    UserDao userDao;

    @Autowired
    RentalEntryDao rentalEntryDao;

    @Test
    public void testCreateRentalEntry() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title, Status.AVAILABLE);
        bookDao.save(book);
        User user = new User("Fred", "Flintstone", LocalDateTime.now());
        userDao.save(user);
        RentalEntry rentalEntry = new RentalEntry(book, user, LocalDateTime.now());

        //when
        rentalEntryDao.save(rentalEntry);

        //then
        assertNotEquals(0L, rentalEntry.getId());

        //clean
        rentalEntryDao.deleteAll();
        bookDao.deleteAll();
        titleDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testReadEntry() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title, Status.AVAILABLE);
        bookDao.save(book);
        User user = new User("Fred", "Flintstone", LocalDateTime.now());
        userDao.save(user);
        RentalEntry rentalEntry = new RentalEntry(book, user, LocalDateTime.now());
        rentalEntryDao.save(rentalEntry);

        //when
        Long id = rentalEntry.getId();
        Optional<RentalEntry>  entryFormDb = rentalEntryDao.findById(id);

        //then
        assertTrue(entryFormDb.isPresent());

        //clean
        rentalEntryDao.deleteAll();
        bookDao.deleteAll();
        titleDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testUpdateEntry() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title, Status.AVAILABLE);
        bookDao.save(book);
        User user = new User("Fred", "Flintstone", LocalDateTime.now());
        userDao.save(user);
        RentalEntry rentalEntry = new RentalEntry(book, user, LocalDateTime.now());
        rentalEntryDao.save(rentalEntry);

        //when
        rentalEntry.setReturnDate(LocalDateTime.now().plusDays(2));
        rentalEntryDao.save(rentalEntry);

        //then
        assertNotNull(rentalEntry.getReturnDate());

        //clean
        rentalEntryDao.deleteAll();
        bookDao.deleteAll();
        titleDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testDeleteEntry() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title, Status.AVAILABLE);
        bookDao.save(book);
        User user = new User("Fred", "Flintstone", LocalDateTime.now());
        userDao.save(user);
        RentalEntry rentalEntry = new RentalEntry(book, user, LocalDateTime.now());
        rentalEntryDao.save(rentalEntry);

        //when
        Long id = rentalEntry.getId();
        rentalEntryDao.deleteById(id);

        //then
        assertFalse(rentalEntryDao.existsById(id));

        //clean
        bookDao.deleteAll();
        titleDao.deleteAll();
        userDao.deleteAll();
    }

}
