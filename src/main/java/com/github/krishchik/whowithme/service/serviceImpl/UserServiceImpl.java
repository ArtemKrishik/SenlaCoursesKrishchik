package com.github.krishchik.whowithme.service.serviceImpl;

import com.github.krishchik.whowithme.model.Credential;
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
import com.github.krishchik.whowithme.service.converter.ProfileConverter;
import com.github.krishchik.whowithme.service.converter.UserConverter;
import com.github.krishchik.whowithme.exception.OperationException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    //private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        if (loginExists(userDto.getLogin())) {
            throw new OperationException("There is an account with that login: "
                    + userDto.getLogin());
        }
        Credential credential =new Credential();
        Profile profile = new Profile();
        profileCrudRepository.save(profile);
        credential.setProfile(profile);
        credential.setLogin(userDto.getLogin());
        //credential.setPassword(passwordEncoder.encode(userDto.getPassword()));
        credential.setRole(roleCrudRepository.getById(userDto.getRoleId()));
        userCrudRepository.save(credential);
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        Credential credentialToChange = userCrudRepository.findById(userDto.getId())
                .orElseThrow(() -> new OperationException("Incorrect input when tryin change user"));
        credentialToChange.setLogin(userDto.getLogin());
        //credentialToChange.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userCrudRepository.save(credentialToChange);
    }

    @Override
    @Transactional
    public UserDto getUserById(Long userId)  {
        Credential credential = userCrudRepository.findById(userId)
                .orElseThrow(() -> new OperationException("credential with id "+userId+" wasn`t found"));
        return userConverter.toDto(credential);
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
        Page<Credential> users = userCrudRepository.findAll(pageable);
        List<UserDto> listusersDto = users.getContent().stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(listusersDto, pageable, users.getTotalElements());
    }


    @Override
    @Transactional
    public ProfileDto getUsersProfile(Principal principal) {
        Credential credential = userCrudRepository.findCredentialByLogin(principal.getName())
                .orElseThrow(() -> new OperationException("credential wasn't found"));
        return profileConverter.toDto(profileCrudRepository.findProfileByCredentialId(credential.getId())
        .orElseThrow(() -> new OperationException("credential`s profile wasn`t found")));
    }

    @Override
    @Transactional
    public Credential getUserByLogin(String login) {
        return userCrudRepository.findCredentialByLogin(login)
                .orElseThrow(() -> new OperationException("user with login "+ login+ " wasn`t found"));


    }


    @Override
    @Transactional
    public MessageDto subscribeUserOnEvent(Principal principal, Long eventId) {

        Credential credentialToSubscribe = userCrudRepository.findCredentialByLogin(principal.getName())
                .orElseThrow(() -> new OperationException("user with login "+principal.getName()+" wasn`t found"));
        Event event = eventCrudRepository.findById(eventId)
                .orElseThrow(() -> new OperationException("event with id "+eventId+" wasn`t found"));

        if(event.getAvailableSlots()==0) throw new OperationException("There are no available slots");
        event.addUser(credentialToSubscribe);
        event.setAvailableSlots(event.getAvailableSlots()-1);
        eventCrudRepository.save(event);
        return new MessageDto("complited");
    }

    @Transactional
    @Override
    public MessageDto unsubscribeUserFromEvent(Principal principal, Long eventId) {
        Credential credentialToUnsubscribe = userCrudRepository.findCredentialByLogin(principal.getName())
                .orElseThrow(() -> new OperationException("user with login "+principal.getName()+" wasn`t found"));
        Event event = eventCrudRepository.findById(eventId)
                .orElseThrow(() -> new OperationException("event with id "+eventId+" wasn`t found"));

        event.getCredentials().remove(credentialToUnsubscribe);
        credentialToUnsubscribe.getEvents().remove(event);
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
        return userCrudRepository.findCredentialByLogin(login).isPresent();
    }

}
