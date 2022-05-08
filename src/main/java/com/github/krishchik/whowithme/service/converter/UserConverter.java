package com.github.krishchik.whowithme.service.converter;

import com.github.krishchik.whowithme.controller.mapper.IUserMapper;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Credential;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class UserConverter {

    private final IUserMapper userMapper;

    public Credential toEntity(UserDto userDto){
        return userMapper.toEntity(userDto);
    }

    public UserDto toDto(Credential credential){
        return userMapper.toDto(credential);
    }


}
