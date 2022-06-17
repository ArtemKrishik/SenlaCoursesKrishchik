package com.github.krishchik.whowithme.controller.mapper.mapperImpl;

import com.github.krishchik.whowithme.controller.mapper.IUserMapper;
import com.github.krishchik.whowithme.model.Credential;
import com.github.krishchik.whowithme.repository.ProfileCrudRepository;
import com.github.krishchik.whowithme.repository.RoleCrudRepository;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@AllArgsConstructor
public class UserMapper implements IUserMapper {

    private final ModelMapper mapper;

    private final RoleCrudRepository roleRepository;

    private final ProfileCrudRepository profileRepository;


    @PostConstruct
    public void setupMapper() {
        TypeMap<Credential, UserDto> typeMap = mapper.getTypeMap(Credential.class, UserDto.class);
        if (typeMap == null) {
            mapper.createTypeMap(Credential.class, UserDto.class)
                    .addMappings(m -> m.skip(UserDto::setProfileId)).setPostConverter(toDtoConverter())
                    .addMappings(m -> m.skip(UserDto::setRoleId)).setPostConverter(toDtoConverter());
            mapper.createTypeMap(UserDto.class, Credential.class)
                    .addMappings(m -> m.skip(Credential::setProfile)).setPostConverter(toEntityConverter())
                    .addMappings(m -> m.skip(Credential::setRole)).setPostConverter(toEntityConverter());
        }
    }

    void mapSpecificFields(Credential source, UserDto destination) {
        destination.setProfileId(getProfileId(source));
        destination.setRoleId(getRoleId(source));
    }

    private Long getProfileId(Credential source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getProfile().getId();
    }

    private Long getRoleId(Credential source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getRole().getId();
    }

    void mapSpecificFields(UserDto source, Credential destination) throws Exception {

        destination.setProfile(profileRepository.getById(source.getProfileId()));
        destination.setRole(roleRepository.getById(source.getRoleId()));

    }

    Converter<Credential, UserDto> toDtoConverter() {
        return context -> {
            Credential source = context.getSource();
            UserDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<UserDto, Credential> toEntityConverter() {
        return context -> {
            UserDto source = context.getSource();
            Credential destination = context.getDestination();
            try {
                mapSpecificFields(source, destination);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return context.getDestination();
        };
    }

    @Override
    public Credential toEntity(UserDto dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, Credential.class);
    }

    @Override
    public UserDto toDto(Credential entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, UserDto.class);
    }

}
