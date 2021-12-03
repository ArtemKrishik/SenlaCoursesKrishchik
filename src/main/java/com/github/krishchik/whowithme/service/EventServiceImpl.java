package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.EventRepository;
import com.github.krishchik.whowithme.api.service.EventService;
import com.github.krishchik.whowithme.controller.Mapper.Mapper;
import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.service.converter.EventConverter;
import com.github.krishchik.whowithme.service.converter.UserConverter;
import com.github.krishchik.whowithme.service.exception.OperationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    @Autowired
    private final EventRepository eventRepository;
    private final EventConverter eventConverter;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public List<EventDto> getEventsByPlace(Long placeId) {
        return eventConverter.listToDto(eventRepository.getEventsByPlace(placeId));
    }

    @Override
    @Transactional
    public List<EventDto> getUsersEvents(UserDto userDto) {
        return eventConverter.listToDto(eventRepository.getUsersEvents(userConverter.toEntity(userDto)));
    }

    @Override
    @Transactional
    public void createEvent(EventDto eventDto) throws Exception {
        eventRepository.save(eventConverter.toEntity(eventDto));
    }

    @Override
    @Transactional
    public void updateEvent(EventDto eventDto) throws Exception {
        Event eventToChange = eventRepository.getById(eventDto.getId());
        if(eventToChange == null) throw new OperationException("Incorrect input when tryin change user");
        eventToChange.setEventName(eventDto.getEventName());
        eventToChange.setEventStatus(eventDto.getEventStatus());
        eventToChange.setAgeLimit(eventDto.getAgeLimit());
        eventToChange.setNumberOfPeople(eventDto.getNumberOfPeople());
        eventRepository.update(eventToChange);
    }

    @Override
    @Transactional
    public EventDto getEventById(Long eventId) throws Exception {
        Event event = eventRepository.getById(eventId);
        if(event == null) throw new OperationException("event with id "+eventId+" wasn`t found");
        return eventConverter.toDto(event);
    }

    @Override
    @Transactional
    public void deleteEvent(EventDto eventDto) throws Exception {
        eventRepository.delete(eventConverter.toEntity(eventDto));
    }

    @Override
    @Transactional
    public List<EventDto> getAllEvents() throws Exception {
        return eventConverter.listToDto(eventRepository.getAll());
    }
}
