package com.github.krishchik.whowithme.service.converter;

import com.github.krishchik.whowithme.controller.Mapper.Mapper;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class UserConverter {

    private final Mapper<UserDto, User> userMapper;
    private final Mapper<ProfileDto, Profile> profileMapper;


    public User toEntity(UserDto userDto){
        return userMapper.toEntity(userDto, User.class);
    }

    public UserDto toDto(User user){
        return userMapper.toDto(user, UserDto.class);
    }

    public List<UserDto> listToDto(List<User> users) {
        return userMapper.listToDto(users, UserDto.class);
    }

    public Profile toEntity(ProfileDto profileDto){
        return profileMapper.toEntity(profileDto, Profile.class);
    }

    public ProfileDto toDto(Profile profile){
        return profileMapper.toDto(profile, ProfileDto.class);
    }





}
