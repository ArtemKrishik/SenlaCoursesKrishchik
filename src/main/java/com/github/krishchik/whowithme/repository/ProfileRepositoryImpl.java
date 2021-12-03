package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.ProfileRepository;
import com.github.krishchik.whowithme.model.Profile;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class ProfileRepositoryImpl extends AbstractRepositoryImpl<Profile, Long> implements ProfileRepository {

    @Override
    protected Class<Profile> getEntityClass() {
        return Profile.class;
    }

}
