package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.service.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/events")
public class EventControllerImpl {

    @Autowired
    private final EventServiceImpl eventService;

    public EventControllerImpl(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public void createEvent(@RequestBody EventDto eventDto) throws Exception {
        eventService.createEvent(eventDto);
    }

    @GetMapping(value = "/{id}")
    public EventDto getEventById(@PathVariable Long id) throws Exception {
        return eventService.getEventById(id);
    }


    @PutMapping
    public void updateEvent(@RequestBody EventDto eventDto) throws Exception {
        eventService.updateEvent(eventDto);
    }

    @DeleteMapping
    public void deleteEvent(@RequestBody EventDto eventDto) throws Exception {
        eventService.deleteEvent(eventDto);
    }

    @GetMapping
    public List<EventDto> getAll() throws Exception {
        return eventService.getAllEvents();
    }

    @GetMapping(value = "/eventsByPlace/{id}")
    public List<EventDto> getEventsByPlace(@RequestParam Long id) {
        return eventService.getEventsByPlace(id);
    }

    @GetMapping(value = "usersEvents")
    public List<EventDto> getUsersEvents(@RequestBody UserDto userDto) {
        return eventService.getUsersEvents(userDto);
    }

}
