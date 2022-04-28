package com.github.krishchik.whowithme.service.serviceImpl;

import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.repository.EventCrudRepository;
import com.github.krishchik.whowithme.repository.ProfileCrudRepository;
import com.github.krishchik.whowithme.repository.RoleCrudRepository;
import com.github.krishchik.whowithme.repository.UserCrudRepository;
import com.github.krishchik.whowithme.service.UserService;
import com.github.krishchik.whowithme.controller.dto.MessageDto;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.service.converter.ProfileConverter;
import com.github.krishchik.whowithme.service.converter.UserConverter;
import com.github.krishchik.whowithme.exception.OperationException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final EventCrudRepository eventCrudRepository;
    private final UserCrudRepository userCrudRepository;
    private final ProfileCrudRepository profileCrudRepository;
    private final RoleCrudRepository roleCrudRepository;
    private final UserConverter userConverter;
    private final ProfileConverter profileConverter;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        if (loginExists(userDto.getLogin())) {
            throw new OperationException("There is an account with that login: "
                    + userDto.getLogin());
        }
        User user =new User();
        Profile profile = new Profile();
        profileCrudRepository.save(profile);
        user.setProfile(profile);
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(roleCrudRepository.getById(userDto.getRoleId()));
        userCrudRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        User userToChange = userCrudRepository.findById(userDto.getId())
                .orElseThrow(() -> new OperationException("Incorrect input when tryin change user"));
        userToChange.setLogin(userDto.getLogin());
        userToChange.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userCrudRepository.save(userToChange);
    }

    @Override
    @Transactional
    public UserDto getUserById(Long userId)  {
        User user = userCrudRepository.findById(userId)
                .orElseThrow(() -> new OperationException("user with id "+userId+" wasn`t found"));
        return userConverter.toDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userCrudRepository.delete(userCrudRepository.findById(userId)
                .orElseThrow(() -> new OperationException("user with id "+userId+" wasn`t found")));
    }

    @Override
    @Transactional
    public Page<UserDto> getAllUsers(Pageable pageable) {
        Page<User> users = userCrudRepository.findAll(pageable);
        List<UserDto> listusersDto = users.getContent().stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(listusersDto, pageable, users.getTotalElements());
    }


    @Override
    @Transactional
    public ProfileDto getUsersProfile(Principal principal) {
        User user = userCrudRepository.findUserByLogin(principal.getName())
                .orElseThrow(() -> new OperationException("user wasn't found"));
        return profileConverter.toDto(profileCrudRepository.findProfileByUserId(user.getId())
        .orElseThrow(() -> new OperationException("user`s profile wasn`t found")));
    }

    @Override
    @Transactional
    public User getUserByLogin(String login) {
        return userCrudRepository.findUserByLogin(login)
                .orElseThrow(() -> new OperationException("user with login "+ login+ " wasn`t found"));


    }


    @Override
    @Transactional
    public MessageDto subscribeUserOnEvent(Principal principal, Long eventId) {

        User userToSubscribe = userCrudRepository.findUserByLogin(principal.getName())
                .orElseThrow(() -> new OperationException("user with login "+principal.getName()+" wasn`t found"));
        Event event = eventCrudRepository.findById(eventId)
                .orElseThrow(() -> new OperationException("event with id "+eventId+" wasn`t found"));

        if(event.getAvailableSlots()==0) throw new OperationException("There are no available slots");
        event.addUser(userToSubscribe);
        event.setAvailableSlots(event.getAvailableSlots()-1);
        eventCrudRepository.save(event);
        return new MessageDto("complited");
    }

    @Transactional
    @Override
    public MessageDto unsubscribeUserFromEvent(Principal principal, Long eventId) {
        User userToUnsubscribe = userCrudRepository.findUserByLogin(principal.getName())
                .orElseThrow(() -> new OperationException("user with login "+principal.getName()+" wasn`t found"));
        Event event = eventCrudRepository.findById(eventId)
                .orElseThrow(() -> new OperationException("event with id "+eventId+" wasn`t found"));

        event.getUsers().remove(userToUnsubscribe);
        userToUnsubscribe.getEvents().remove(event);
        event.setAvailableSlots(event.getAvailableSlots()+1);
        eventCrudRepository.save(event);
        return new MessageDto("complited");
    }

    @Override
    public void updateProfile(ProfileDto profileDto, Principal principal) {
        Profile profile = profileConverter.toEntity(this.getUsersProfile(principal));
        if (profileDto.getName()!=null) profile.setName(profileDto.getName());
        if (profileDto.getPhoneNumber()!=null) profile.setPhoneNumber(profileDto.getPhoneNumber());
        if (profileDto.getAge()!=null) profile.setAge(profileDto.getAge());
        profileCrudRepository.save(profile);
    }

    private boolean loginExists(String login) {
        return userCrudRepository.findUserByLogin(login).isPresent();
    }

}
