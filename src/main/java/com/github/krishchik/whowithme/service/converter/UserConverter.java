package com.github.krishchik.whowithme.service.converter;

import com.github.krishchik.whowithme.controller.Mapper.IEventMapper;
import com.github.krishchik.whowithme.controller.Mapper.IProfileMapper;
import com.github.krishchik.whowithme.controller.Mapper.IUserMapper;
import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


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
