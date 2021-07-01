package com.crud.library.mapper;

import com.crud.library.utility.TimeProvider;
import com.crud.library.domain.User;
import com.crud.library.domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final TimeProvider timeProvider;

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                timeProvider.getTime()
        );
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreationDate(),
                user.getListOfRentals()
        );
    }
}
