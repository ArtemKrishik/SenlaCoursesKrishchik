package com.github.krishchik.whowithme.api.repository;

import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.User;

import java.util.List;

public interface UserRepository extends AbstractRepository<User, Long>{

    Profile getUsersProfile(Long userId);

    User getUserByLogin(String login);
}
