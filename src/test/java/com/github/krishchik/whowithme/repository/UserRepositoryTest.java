package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.RepositoryTest;
import com.github.krishchik.whowithme.api.repository.ProfileRepository;
import com.github.krishchik.whowithme.api.repository.RoleRepository;
import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.model.*;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = { UserRepositoryImpl.class, ProfileRepositoryImpl.class, RoleRepositoryImpl.class })
public class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private RoleRepository roleRepository;

    private User user;
    private Profile profile;
    private Role role;


    @BeforeAll
    public void getTestUser() throws Exception {

        user = new User();
        user.setId(2l);
        user.setLogin("login");
        user.setPassword("password");
        profile = new Profile();
        profile.setId(1l);
        profile.setAge(10);
        profile.setName("Name");
        profile.setPhoneNumber(111l);
        role = new Role();
        role.setId(1l);
        role.setName("admin");

    }

    @Test
    void shouldDeleteUserCorrect() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        userRepository.delete(user);
        assertNull(userRepository.getById(2l));
    }

    @Test
    public void shouldFindUserByIdCorrect() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        final User potentialUser = userRepository.getById(2l);
        assertEquals(user.getId(), potentialUser.getId());
    }


    @Test
    public void shouldFinishWithNullPointerException() throws Exception {

        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        assertThrows(
                LazyInitializationException.class,
                () -> userRepository.getById(2l).getRole().getName()
        );
    }

    @Test
    public void shouldGiveUsersProfileCorrect() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        assertEquals(userRepository.getUsersProfile(2l).getId(), 1l);
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        final List<User> users = userRepository.getAll();
        assertEquals(1, users.size());
        assertEquals("login", users.get(0).getLogin());

    }

    @Test
    public void shouldUpdateUserCorrect() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        User updatedUser = userRepository.getById(2l);
        updatedUser.setLogin("newLogin");
        userRepository.update(updatedUser);
        assertEquals("newLogin", userRepository.getById(2l).getLogin());
    }

}
