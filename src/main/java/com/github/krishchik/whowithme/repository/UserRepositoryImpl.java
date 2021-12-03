package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityGraph;
import javax.transaction.Transactional;
import java.util.Map;

@Repository
@Transactional
public class UserRepositoryImpl extends AbstractRepositoryImpl<User, Long> implements UserRepository {

    public static final String JAVAX_PERSISTENCE_FETCHGRAPH = "javax.persistence.fetchgraph";
    public static final String USER_PROFILE_ENTITY_GRAPH = "user-profile-entity-graph";

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public Profile getUsersProfile(Long userId) {
        EntityGraph graph = entityManager.getEntityGraph(USER_PROFILE_ENTITY_GRAPH);
        Map<String, Object> properties = Map.of(JAVAX_PERSISTENCE_FETCHGRAPH, graph);
        return entityManager.find(User.class, userId, properties).getProfile();
    }


}
