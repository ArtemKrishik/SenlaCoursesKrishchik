package com.github.krishchik.whowithme.api.service;

import com.github.krishchik.whowithme.model.Event;

import java.util.List;

public interface EventService {

    void createEvent(Event createdEvent);

    void updateEvent(Event updatedEvent);

    Event getEventById(Long eventId);

    void deleteEvent(Event deletedEvent);

    List<Event> getAllEvents();

}
