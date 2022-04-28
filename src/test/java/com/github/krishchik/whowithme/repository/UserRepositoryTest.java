package com.github.krishchik.whowithme.repository;




import com.github.krishchik.whowithme.RepositoryTest;
import com.github.krishchik.whowithme.config.DatabaseConfiguration;
import com.github.krishchik.whowithme.model.*;
import com.github.krishchik.whowithme.repository.repositoryApi.ProfileCrudRepository;
import com.github.krishchik.whowithme.repository.repositoryApi.RoleCrudRepository;
import com.github.krishchik.whowithme.repository.repositoryApi.UserCrudRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebAppConfiguration
@ContextConfiguration(
        classes = DatabaseConfiguration.class,
        loader = AnnotationConfigWebContextLoader.class
)
@Transactional
public class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserCrudRepository userRepository;
    @Autowired
    private ProfileCrudRepository profileRepository;
    @Autowired
    private RoleCrudRepository roleRepository;

    private User user;
    private Profile profile;
    private Role role;


    @BeforeAll
    public void getTestUser() throws Exception {

        user = new User();
        user.setLogin("login");
        user.setPassword("password");
        profile = new Profile();

        profile.setAge(10);
        profile.setName("Name");
        profile.setPhoneNumber(111l);
        role = new Role();

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
        assertThrows(
                JpaObjectRetrievalFailureException.class,
                () -> userRepository.getById(user.getId())
        );
    }

    @Test
    public void shouldFindUserByIdCorrect() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        assertEquals(userRepository.findById(user.getId()).get().getId(), user.getId());
    }



    @Test
    public void shouldGiveUsersProfileCorrect() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        assertEquals(profileRepository.findProfileByUserId(user.getId()).get().getId(), profile.getId());
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        final List<User> users = userRepository.findAll();
        assertEquals(4, users.size());
        assertEquals(1l, users.get(0).getId());

    }

    @Test
    public void shouldUpdateUserCorrect() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        User updatedUser = userRepository.getById(user.getId());
        updatedUser.setLogin("newLogin");
        userRepository.save(updatedUser);
        assertEquals("newLogin", userRepository.getById(user.getId()).getLogin());
    }

}
