package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileCrudRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findProfileByUserId(Long id);

}
