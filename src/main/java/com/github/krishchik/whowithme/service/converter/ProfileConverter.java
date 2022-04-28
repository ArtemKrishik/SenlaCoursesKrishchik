package com.github.krishchik.whowithme.service.converter;

import com.github.krishchik.whowithme.controller.mapper.IProfileMapper;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.model.Profile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProfileConverter {

    private final IProfileMapper profileMapper;

    public Profile toEntity(ProfileDto profileDto){
        return profileMapper.toEntity(profileDto);
    }

    public ProfileDto toDto(Profile profile){
        return profileMapper.toDto(profile);
    }

}
