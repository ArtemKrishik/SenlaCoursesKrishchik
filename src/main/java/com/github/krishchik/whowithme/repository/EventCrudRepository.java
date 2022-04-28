package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface EventCrudRepository
        extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {


    Page<Event> findEventsByUsersId(Pageable pageable, Long userId);

    Page<Event> findEventsByPlaceId(Pageable pageable, Long id);


}
