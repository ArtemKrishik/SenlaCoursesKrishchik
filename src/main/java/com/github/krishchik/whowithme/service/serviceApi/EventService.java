package com.github.krishchik.whowithme.service.serviceApi;

import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.security.Principal;

public interface EventService {

    void createEvent(EventDto createdEvent, Principal principal);

    void updateEvent(EventDto updatedEvent, Principal principal);

    EventDto getEventById(Long eventId);

    void deleteEvent(Long eventId, Principal principal);

    Page<EventDto> getAllEvents(Pageable pageable, Specification<Event> specification);

    Page<EventDto> getEventsByPlace(Pageable pageable,Long placeId);

    Page<EventDto> getUsersEvents(Pageable pageable, Long userId);

    Page<UserDto> getUsersOfEvent(Pageable pageable, Long id);

}
