package com.crud.library.domain;

import com.crud.library.exceptions.BookNotFoundException;
import com.crud.library.exceptions.RentalNotFoundException;
import com.crud.library.exceptions.UserNotFoundException;
import com.crud.library.repository.BookDao;
import com.crud.library.repository.RentalDao;
import com.crud.library.repository.TitleDao;
import com.crud.library.repository.UserDao;
import com.crud.library.utility.TimeProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    public void testCreateRental() {
        //given
        Title title = new Title("title", "author", 2020);
        titleDao.save(title);
        Book book = new Book(title);
        bookDao.save(book);
        User user = new User("Fred", "Flintstone", timeProvider.getTime());
        userDao.save(user);
        Rental rental = new Rental(book, user, timeProvider.getTime());

        //when
        rentalDao.save(rental);

        //then
        assertNotEquals(0L, rental.getId());

        //clean
        rentalDao.deleteAll();
        bookDao.deleteAll();
        titleDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testReadRental() throws RentalNotFoundException {
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
        Rental rentalFromDb = rentalDao.findById(rental.getId()).orElseThrow(() -> new RentalNotFoundException(rental.getId()));

        //then
        assertEquals("title", rentalFromDb.getBook().getTitle().getTitle());
        assertEquals("author", rentalFromDb.getBook().getTitle().getAuthor());
        assertEquals(2020, rentalFromDb.getBook().getTitle().getPublicationYear());
        assertEquals("Fred", rentalFromDb.getUser().getFirstName());
        assertEquals("Flintstone", rentalFromDb.getUser().getLastName());

        //clean
        rentalDao.deleteAll();
        bookDao.deleteAll();
        titleDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void testUpdateRental() {
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
        rental.setReturnDate(timeProvider.getTime().plusDays(2));
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

    @Test
    public void testRelationsWithUserAndBook() throws UserNotFoundException, BookNotFoundException {
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
        List<Rental> userRentals = userDao.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId())).getListOfRentals();
        List<Rental> bookRentals = bookDao.findById(book.getId()).orElseThrow(() -> new BookNotFoundException(book.getId())).getListOfRentals();

        //then
        assertEquals(1, userRentals.size());
        assertEquals(1, bookRentals.size());

        //clean
        rentalDao.deleteAll();
        bookDao.deleteAll();
        titleDao.deleteAll();
        userDao.deleteAll();
    }
}
