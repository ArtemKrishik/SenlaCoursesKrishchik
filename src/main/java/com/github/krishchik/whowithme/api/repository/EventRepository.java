package com.github.krishchik.whowithme.api.repository;

import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.User;

import java.util.List;

public interface EventRepository extends AbstractRepository<Event, Long>{

    List<Event> getEventsByPlace(Long placeId);

    List<Event> getUsersEvents(User user);

}
