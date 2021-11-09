package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.EventRepository;
import com.github.krishchik.whowithme.model.Event;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepositoryImpl extends AbstractRepositoryImpl<Event> implements EventRepository {

    @Override
    public void update(Event updatedEvent) {
        Event event = getById(updatedEvent.getId());
        event.setEventName(updatedEvent.getEventName());
        event.setEventStatus(updatedEvent.getEventStatus());
        event.setAgeLimit(updatedEvent.getAgeLimit());
        event.setNumberOfPeople(updatedEvent.getNumberOfPeople());
    }

}
