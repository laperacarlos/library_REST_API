package com.crud.library.domain;

import com.crud.library.repository.UserDao;
import com.crud.library.utility.TimeProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
public class UserTestSuite {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TimeProvider timeProvider;

    @Test
    public void testCreateUser() {
        //given
        User user = new User("Fred", "Flintstone", timeProvider.getTime());

        //when
        User savedUser = userDao.save(user);

        //then
        assertNotEquals(0L, savedUser.getId());

        //clean
        userDao.deleteAll();
    }

    @Test
    public void testReadDataFromUser() throws IOException {
        //given
        User user = new User("Fred", "Flintstone", timeProvider.getTime());
        User savedUser = userDao.save(user);

        //when
        Long id = savedUser.getId();
        User userFromDb = userDao.findById(id).orElseThrow(IOException::new);

        //then
        assertEquals("Fred", userFromDb.getFirstName());
        assertEquals("Flintstone", userFromDb.getLastName());

        //clean
        userDao.deleteAll();
    }

    @Test
    public void testUpdateUser() {
        //given
        User user = new User("Fred", "Flintstone", timeProvider.getTime());
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
        User user = new User("Fred", "Flintstone", timeProvider.getTime());
        userDao.save(user);

        //when
        Long id = user.getId();
        userDao.deleteById(id);
        Optional<User> deletedUser = userDao.findById(id);

        //then
        assertFalse(deletedUser.isPresent());
    }
}
