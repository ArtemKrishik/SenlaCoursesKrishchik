package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.service.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class EventControllerImpl {

    @Autowired
    private final EventServiceImpl eventService;

    public EventControllerImpl(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    public void createEvent(EventDto eventDto) throws Exception {
        eventService.createEvent(eventDto);
    }

    public EventDto getEventById(Long eventId) throws Exception {
        return eventService.getEventById(eventId);
    }

    public void updateEvent(EventDto eventDto) throws Exception {
        eventService.updateEvent(eventDto);
    }

    public void deleteEvent(EventDto eventDto) throws Exception {
        eventService.deleteEvent(eventDto);
    }

    public List<EventDto> getAll() throws Exception {
        return eventService.getAllEvents();
    }

    public List<EventDto> getEventsByPlace(Long placeId) {
        return eventService.getEventsByPlace(placeId);
    }

    public List<EventDto> getUsersEvents(UserDto userDto) {
        return eventService.getUsersEvents(userDto);
    }

}
