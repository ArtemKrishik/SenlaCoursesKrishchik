package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.EventRepository;
import com.github.krishchik.whowithme.api.service.EventService;
import com.github.krishchik.whowithme.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void createEvent(Event createdEvent) throws Exception {
        eventRepository.save(createdEvent);
    }

    @Override
    public void updateEvent(Event updatedEvent) throws Exception {
        eventRepository.update(updatedEvent);
    }

    @Override
    public Event getEventById(Long eventId) throws Exception {
        return eventRepository.getById(eventId);
    }

    @Override
    public void deleteEvent(Long id) throws Exception {
        eventRepository.delete(id);
    }

    @Override
    public List<Event> getAllEvents() throws Exception {
        return eventRepository.getAll();
    }

}
