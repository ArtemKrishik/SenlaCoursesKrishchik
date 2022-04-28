package com.github.krishchik.whowithme.controller.mapper.mapperImpl;

import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.mapper.IProfileMapper;
import com.github.krishchik.whowithme.model.Profile;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class ProfileMapper implements IProfileMapper {


    private final ModelMapper mapper;

    @Override
    public Profile toEntity(ProfileDto dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, Profile.class);
    }

    @Override
    public ProfileDto toDto(Profile entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, ProfileDto.class);
    }

}
