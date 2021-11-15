package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.service.EventServiceImpl;
import com.github.krishchik.whowithme.util.JsonMapper;
import org.springframework.stereotype.Component;

@Component
public class EventControllerImpl {

    private final EventServiceImpl eventService;
    private final JsonMapper jsonMapper;

    public EventControllerImpl(EventServiceImpl eventService, JsonMapper jsonMapper) {
        this.eventService = eventService;
        this.jsonMapper = jsonMapper;
    }

    public void createEvent(String eventJsonString) throws Exception {
        Event event = jsonMapper.convertToEvent(eventJsonString);
        eventService.createEvent(event);
    }

    public String getEventById(Long eventId) throws Exception {
        Event event = eventService.getEventById(eventId);
        return jsonMapper.convertEventToJson(event);
    }

    public void updateEvent(String eventJsonString) throws Exception {
        Event event = jsonMapper.convertToEvent(eventJsonString);
        eventService.updateEvent(event);
    }

    public void deleteEvent(Long id) throws Exception {
        eventService.deleteEvent(id);
    }

    public String getAll() throws Exception {
        return jsonMapper.convertEventToJson(eventService.getAllEvents());
    }

}
