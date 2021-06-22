package com.crud.library.controller;

import com.crud.library.domain.User;
import com.crud.library.domain.UserDto;
import com.crud.library.mapper.UserMapper;
import com.crud.library.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserMapper userMapper;
    private final DbService dbService;

    @PostMapping(value = "createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        User createdUser = dbService.saveUser(new User(
                user.getFirstName(),
                user.getLastName(),
                LocalDateTime.now()
        ));
        return userMapper.mapToUserDto(createdUser);
    }
}
