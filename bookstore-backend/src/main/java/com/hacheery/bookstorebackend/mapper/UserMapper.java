package com.hacheery.bookstorebackend.mapper;

import com.hacheery.bookstorebackend.dto.UserDto;
import com.hacheery.bookstorebackend.security.entity.User;

public class UserMapper {
    public static User mapToUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setRole(userDto.getRole());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setPhoneNumber(userDto.getPhoneNumber());
        return user;
    }

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setRole(user.getRole());
        userDto.setAddress(user.getAddress());
        return userDto;
    }
}
