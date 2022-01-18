package com.github.krishchik.whowithme.repository.repositoryApi;

import com.github.krishchik.whowithme.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>
{

    @EntityGraph(value = "user-roles-entity-graph", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findUserByLogin(String login);

    Page<User> findUsersByEventsId(Pageable pageable, Long eventId);





}
