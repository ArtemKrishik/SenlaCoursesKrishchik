package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractRepositoryImpl<User> implements UserRepository {

    @Override
    public void update(User updatedUser) {
        User user = getById(updatedUser.getId());
        user.setLogin(updatedUser.getLogin());
        user.setPassword(updatedUser.getPassword());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
    }

}
