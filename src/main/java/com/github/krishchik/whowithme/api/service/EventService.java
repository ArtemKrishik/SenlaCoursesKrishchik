package com.github.krishchik.whowithme.api.service;

import com.github.krishchik.whowithme.model.Event;

import java.util.List;

public interface EventService {

    void createEvent(Event createdEvent) throws Exception;

    void updateEvent(Event updatedEvent) throws Exception;

    Event getEventById(Long eventId) throws Exception;

    void deleteEvent(Long id) throws Exception;

    List<Event> getAllEvents() throws Exception;

}
