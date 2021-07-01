package com.crud.library.service;

import com.crud.library.domain.User;
import com.crud.library.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User saveUser(final User user) {
        return userDao.save(user);
    }
}
