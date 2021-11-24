package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.EventRepository;
import com.github.krishchik.whowithme.api.service.EventService;
import com.github.krishchik.whowithme.controller.Mapper.Mapper;
import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private final EventRepository eventRepository;

    private final Mapper<EventDto, Event> eventMapper;

    private final Mapper<UserDto, User> userMapper;

    public EventServiceImpl(EventRepository eventRepository, Mapper<EventDto, Event> eventMapper, Mapper<UserDto, User> userMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public List<EventDto> getEventsByPlace(Long placeId) {
        return eventMapper.listToDto(eventRepository.getEventsByPlace(placeId),Event.class);
    }

    @Override
    @Transactional
    public List<EventDto> getUsersEvents(UserDto userDto) {
        return eventMapper.listToDto(eventRepository.getUsersEvents(userMapper.toEntity(userDto, User.class)),EventDto.class);
    }


    @Override
    @Transactional
    public void createEvent(EventDto eventDto) throws Exception {
        eventRepository.save(eventMapper.toEntity(eventDto, Event.class));
    }

    @Override
    @Transactional
    public void updateEvent(EventDto eventDto) throws Exception {
        eventRepository.update(eventMapper.toEntity(eventDto, Event.class));
    }

    @Override
    @Transactional
    public EventDto getEventById(Long eventId) throws Exception {
        return eventMapper.toDto(eventRepository.getById(eventId), EventDto.class);
    }

    @Override
    @Transactional
    public void deleteEvent(EventDto eventDto) throws Exception {
        eventRepository.delete(eventRepository.getById(eventMapper.toEntity(eventDto, Event.class).getId()));
    }

    @Override
    @Transactional
    public List<EventDto> getAllEvents() throws Exception {
        return eventMapper.listToDto(eventRepository.getAll(), Event.class);
    }
}
