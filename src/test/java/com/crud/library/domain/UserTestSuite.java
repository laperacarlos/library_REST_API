package com.crud.library.domain;

import com.crud.library.repository.UserDao;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@SpringBootTest()
public class UserTestSuite {

    @Autowired
    private UserDao userDao;

    @Test
    public void testCreateUser() {
        //given
        User user = new User("Fred", "Flintstone", LocalDateTime.now());

        //when
        userDao.save(user);

        //then
        assertNotEquals(0L, user.getId());

        //clean
        userDao.deleteAll();
    }

    @Test
    public void testReadDataFromUser() {
        //given
        User user = new User("Fred", "Flintstone", LocalDateTime.now());
        userDao.save(user);

        //when
        Long id = user.getId();
        Optional<User> userFromDb = userDao.findById(id);

        //then
        assertTrue(userFromDb.isPresent());

        //clean
        userDao.deleteAll();
    }

    @Test
    public void testUpdateUser() {
        //given
        User user = new User("Fred", "Flintstone", LocalDateTime.now());
        userDao.save(user);

        //when
        user.setFirstName("Marian");
        userDao.save(user);

        //then
        assertEquals("Marian", user.getFirstName());

        //clean
        userDao.deleteAll();
    }

    @Test
    public void testDeleteUser() {
        //given
        User user = new User("Fred", "Flintstone", LocalDateTime.now());
        userDao.save(user);

        //when
        Long id = user.getId();
        userDao.deleteById(id);
        Optional<User> deletedUser = userDao.findById(id);

        //then
        assertFalse(deletedUser.isPresent());
    }
}
