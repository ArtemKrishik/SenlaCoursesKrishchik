package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.EventRepository;
import com.github.krishchik.whowithme.api.service.EventService;
import com.github.krishchik.whowithme.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void createEvent(Event createdEvent) {
        eventRepository.save(createdEvent);
    }

    @Override
    public void updateEvent(Event updatedEvent) {
        eventRepository.update(updatedEvent);
    }

    @Override
    public Event getEventById(Long eventId) {
        return eventRepository.getById(eventId);
    }

    @Override
    public void deleteEvent(Event deletedEvent) {
        eventRepository.delete(deletedEvent);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.getAll();
    }

}
