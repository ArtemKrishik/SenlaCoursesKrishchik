package com.github.krishchik.whowithme.repository;




import com.github.krishchik.whowithme.RepositoryTest;
import com.github.krishchik.whowithme.config.DatabaseConfiguration;
import com.github.krishchik.whowithme.model.*;
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
public class CredentialRepositoryTest extends RepositoryTest {

    @Autowired
    private UserCrudRepository userRepository;
    @Autowired
    private ProfileCrudRepository profileRepository;
    @Autowired
    private RoleCrudRepository roleRepository;

    private Credential credential;
    private Profile profile;
    private Role role;


    @BeforeAll
    public void getTestUser() throws Exception {

        credential = new Credential();
        credential.setLogin("login");
        credential.setPassword("password");
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
        credential.setProfile(profile);
        credential.setRole(role);
        userRepository.save(credential);
        userRepository.delete(credential);
        assertThrows(
                JpaObjectRetrievalFailureException.class,
                () -> userRepository.getById(credential.getId())
        );
    }

    @Test
    public void shouldFindUserByIdCorrect() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        credential.setProfile(profile);
        credential.setRole(role);
        userRepository.save(credential);
        assertEquals(userRepository.findById(credential.getId()).get().getId(), credential.getId());
    }



    @Test
    public void shouldGiveUsersProfileCorrect() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        credential.setProfile(profile);
        credential.setRole(role);
        userRepository.save(credential);
        assertEquals(profileRepository.findProfileByCredentialId(credential.getId()).get().getId(), profile.getId());
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        credential.setProfile(profile);
        credential.setRole(role);
        userRepository.save(credential);
        final List<Credential> credentials = userRepository.findAll();
        assertEquals(4, credentials.size());
        assertEquals(1l, credentials.get(0).getId());

    }

    @Test
    public void shouldUpdateUserCorrect() throws Exception {
        profileRepository.save(profile);
        roleRepository.save(role);
        credential.setProfile(profile);
        credential.setRole(role);
        userRepository.save(credential);
        Credential updatedCredential = userRepository.getById(credential.getId());
        updatedCredential.setLogin("newLogin");
        userRepository.save(updatedCredential);
        assertEquals("newLogin", userRepository.getById(credential.getId()).getLogin());
    }

}
