package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.RoleRepository;
import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.model.Role;
import com.github.krishchik.whowithme.model.User;

public class RoleRepositoryImpl extends AbstractRepositoryImpl<Role, Long> implements RoleRepository {

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }
}
