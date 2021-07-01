package com.crud.library.domain;

import com.crud.library.repository.BookDao;
import com.crud.library.repository.RentalDao;
import com.crud.library.repository.TitleDao;
import com.crud.library.repository.UserDao;
import com.crud.library.utility.TimeProvider;
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
    RentalDao rentalDao;

    @Autowired
    TimeProvider timeProvider;

    @Test
    public void testCreateRentalEntry() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title);
        bookDao.save(book);
        User user = new User("Fred", "Flintstone", timeProvider.getTime());
        userDao.save(user);
        Rental rentalEntry = new Rental(book, user, timeProvider.getTime());

        //when
        rentalDao.save(rentalEntry);

        //then
        assertNotEquals(0L, rentalEntry.getId());

        //clean
        rentalDao.deleteAll();
        bookDao.deleteAll();
        titleDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testReadEntry() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title);
        bookDao.save(book);
        User user = new User("Fred", "Flintstone", timeProvider.getTime());
        userDao.save(user);
        Rental rentalEntry = new Rental(book, user, timeProvider.getTime());
        rentalDao.save(rentalEntry);

        //when
        Long id = rentalEntry.getId();
        Optional<Rental>  entryFormDb = rentalDao.findById(id);

        //then
        assertTrue(entryFormDb.isPresent());

        //clean
        rentalDao.deleteAll();
        bookDao.deleteAll();
        titleDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testUpdateEntry() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title);
        bookDao.save(book);
        User user = new User("Fred", "Flintstone", timeProvider.getTime());
        userDao.save(user);
        Rental rental = new Rental(book, user, timeProvider.getTime());
        rentalDao.save(rental);

        //when
        rental.setReturnDate(LocalDateTime.now().plusDays(2));
        rentalDao.save(rental);

        //then
        assertNotNull(rental.getReturnDate());

        //clean
        rentalDao.deleteAll();
        bookDao.deleteAll();
        titleDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testDeleteEntry() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title);
        bookDao.save(book);
        User user = new User("Fred", "Flintstone", timeProvider.getTime());
        userDao.save(user);
        Rental rentalEntry = new Rental(book, user, timeProvider.getTime());
        rentalDao.save(rentalEntry);

        //when
        Long id = rentalEntry.getId();
        rentalDao.deleteById(id);

        //then
        assertFalse(rentalDao.existsById(id));

        //clean
        bookDao.deleteAll();
        titleDao.deleteAll();
        userDao.deleteAll();
    }

}
