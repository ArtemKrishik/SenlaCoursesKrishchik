package com.github.krishchik.whowithme.controller.mapper.mapperImpl;

import com.github.krishchik.whowithme.controller.mapper.IUserMapper;
import com.github.krishchik.whowithme.repository.ProfileCrudRepository;
import com.github.krishchik.whowithme.repository.RoleCrudRepository;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
        TypeMap<User, UserDto> typeMap = mapper.getTypeMap(User.class, UserDto.class);
        if (typeMap == null) {
            mapper.createTypeMap(User.class, UserDto.class)
                    .addMappings(m -> m.skip(UserDto::setProfileId)).setPostConverter(toDtoConverter())
                    .addMappings(m -> m.skip(UserDto::setRoleId)).setPostConverter(toDtoConverter());
            mapper.createTypeMap(UserDto.class, User.class)
                    .addMappings(m -> m.skip(User::setProfile)).setPostConverter(toEntityConverter())
                    .addMappings(m -> m.skip(User::setRole)).setPostConverter(toEntityConverter());
        }
    }

    void mapSpecificFields(User source, UserDto destination) {
        destination.setProfileId(getProfileId(source));
        destination.setRoleId(getRoleId(source));
    }

    private Long getProfileId(User source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getProfile().getId();
    }

    private Long getRoleId(User source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getRole().getId();
    }

    void mapSpecificFields(UserDto source, User destination) throws Exception {

        destination.setProfile(profileRepository.getById(source.getProfileId()));
        destination.setRole(roleRepository.getById(source.getRoleId()));

    }

    Converter<User, UserDto> toDtoConverter() {
        return context -> {
            User source = context.getSource();
            UserDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<UserDto, User> toEntityConverter() {
        return context -> {
            UserDto source = context.getSource();
            User destination = context.getDestination();
            try {
                mapSpecificFields(source, destination);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return context.getDestination();
        };
    }

    @Override
    public User toEntity(UserDto dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, User.class);
    }

    @Override
    public UserDto toDto(User entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, UserDto.class);
    }

}
