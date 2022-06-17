package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.model.Credential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<Credential, Long>, JpaSpecificationExecutor<Credential>
{

    @EntityGraph(value = "user-roles-entity-graph", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Credential> findCredentialByLogin(String login);

    Page<Credential> findCredentialsByEventsId(Pageable pageable, Long eventId);

}
