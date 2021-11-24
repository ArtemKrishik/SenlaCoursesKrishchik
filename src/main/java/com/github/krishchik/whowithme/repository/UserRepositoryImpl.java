package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepositoryImpl extends AbstractRepositoryImpl<User, Long> implements UserRepository {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }




}
