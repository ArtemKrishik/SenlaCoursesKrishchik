package com.github.krishchik.whowithme.api.service;

import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Event;

import java.util.List;

public interface EventService {

    void createEvent(EventDto createdEvent) throws Exception;

    void updateEvent(EventDto updatedEvent) throws Exception;

    EventDto getEventById(Long eventId) throws Exception;

    void deleteEvent(EventDto eventDto) throws Exception;

    List<EventDto> getAllEvents() throws Exception;

    List<EventDto> getEventsByPlace(Long placeId);

    List<EventDto> getUsersEvents(UserDto userDto);

}
