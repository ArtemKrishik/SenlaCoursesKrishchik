package com.github.krishchik.whowithme.service.converter;

import com.github.krishchik.whowithme.controller.mapper.IUserMapper;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class UserConverter {

    private final IUserMapper userMapper;

    public User toEntity(UserDto userDto){
        return userMapper.toEntity(userDto);
    }

    public UserDto toDto(User user){
        return userMapper.toDto(user);
    }


}
