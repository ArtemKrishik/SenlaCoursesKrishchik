package com.github.krishchik.whowithme.service.serviceImpl;

import com.github.krishchik.whowithme.model.Credential;
import com.github.krishchik.whowithme.repository.EventCrudRepository;
import com.github.krishchik.whowithme.repository.UserCrudRepository;
import com.github.krishchik.whowithme.service.EventService;
import com.github.krishchik.whowithme.service.UserService;
import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.service.converter.EventConverter;
import com.github.krishchik.whowithme.service.converter.UserConverter;
import com.github.krishchik.whowithme.exception.OperationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.krishchik.whowithme.model.EventStatus.*;

@Slf4j
@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventCrudRepository eventCrudRepository;
    private final UserCrudRepository userCrudRepository;
    private final UserService userService;
    private final EventConverter eventConverter;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public Page<EventDto> getEventsByPlace(Pageable pageable, Long placeId) {
        Page<Event> events = eventCrudRepository.findEventsByPlaceId(pageable, placeId);
        events.forEach(this::updateEventStatus);
        List<EventDto> listpleisovDto = events.getContent().stream()
                .map(eventConverter::toDto)
                .collect(Collectors.toList());
        log.info("events by place with id"+ placeId+ " were received");

        return new PageImpl<>(listpleisovDto, pageable, events.getTotalElements());
    }


    @Override
    @Transactional
    public Page<EventDto> getUsersEvents(Pageable pageable, Long id) {
        Page<Event> events = eventCrudRepository.findEventsByCredentials(pageable, id);
        events.forEach(this::updateEventStatus);
        List<EventDto> listpleisovDto = events.getContent().stream()
                .map(eventConverter::toDto)
                .collect(Collectors.toList());
        log.info("user`s events were received");

        return new PageImpl<>(listpleisovDto, pageable, events.getTotalElements());
    }

    @Override
    @Transactional
    public Page<UserDto> getUsersOfEvent(Pageable pageable, Long id) {
        Page<Credential> users = userCrudRepository.findCredentialsByEventsId(pageable, id);
        List<UserDto> userDtoList = users.getContent().stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
        log.info("users of event were received");
        return new PageImpl<>(userDtoList, pageable, users.getTotalElements());
    }

    @Override
    @Transactional
    public void createEvent(EventDto eventDto, Principal principal) {
        Credential creator = userService.getUserByLogin(principal.getName());
        if(eventDto.getStartTime().isAfter(eventDto.getEndTime())) throw new OperationException("incorrect data input");
        eventDto.setCreatorId(creator.getId());
        Event event = eventCrudRepository.save(eventConverter.toEntity(eventDto));
        log.info("event with name "+ eventDto.getEventName()+ "\n id"+ event.getId() + "was awarded");

    }

    @Override
    @Transactional
    public void updateEvent(EventDto eventDto, Principal principal) {
        Credential updater = userService.getUserByLogin(principal.getName());
        Event eventToChange = eventCrudRepository.findById(eventDto.getId())
                .orElseThrow(() -> new OperationException("Incorrect input when trying change event"));
        if(updater.equals(eventToChange.getCreator())||updater.getRole().getName().equals("ADMIN")) {
            eventToChange.setEventName(eventDto.getEventName());
            eventToChange.setEventStatus(eventDto.getEventStatus());
            eventToChange.setAgeLimit(eventDto.getAgeLimit());
            eventToChange.setNumberOfSlots(eventDto.getNumberOfSlots());
            eventCrudRepository.save(eventToChange);
            log.info("event with id "+ eventToChange.getId()+ "and name " + eventToChange.getEventName() + "has been updated");

        } else {
            log.info("event has not been updated");

            throw new OperationException("you aren`t creator");
        }

    }

    @Override
    @Transactional
    public EventDto getEventById(Long eventId) {
        Event event = eventCrudRepository.findById(eventId)
                .orElseThrow(() -> new OperationException("event with id "+ eventId +" wasn`t found"));
        this.updateEventStatus(event);
        log.info("event with id "+ eventId+ "was received");
        return eventConverter.toDto(event);
    }

    @Override
    @Transactional
    public void deleteEvent(Long eventId, Principal principal) {
        Credential deleter = userService.getUserByLogin(principal.getName());
        Event eventToDelete = eventCrudRepository.findById(eventId)
                .orElseThrow(() -> new OperationException("event with id "+eventId+" wasn`t found"));
        if(deleter.equals(eventToDelete.getCreator())||deleter.getRole().getName().equals("ADMIN")){
            eventCrudRepository.delete(eventToDelete);
            log.info("event with id "+ eventId+ "has been deleted");

        } else throw new OperationException("you aren`t creator");

    }


    @Transactional
    private void updateEventStatus(Event event)  {
        if(event.getStartTime().after(new Timestamp(System.currentTimeMillis())))
        {event.setEventStatus(PLANNED);}
        if(event.getStartTime().before(new Timestamp(System.currentTimeMillis()))
                && event.getEndTime().after(new Timestamp(System.currentTimeMillis())))
        {event.setEventStatus(ACTIVE);}
        if(event.getEndTime().before(new Timestamp(System.currentTimeMillis())))
        {event.setEventStatus(COMPLITED);}
        eventCrudRepository.save(event);
        log.info("event status has been updated");

    }

    @Override
    @Transactional
    @org.springframework.transaction.annotation.Transactional
    public Page<EventDto> getAllEvents(Pageable pageable, Specification<Event> specification)  {
        Page<Event> events = eventCrudRepository.findAll(specification, pageable);

        events.forEach(this::updateEventStatus);
        events.getContent().get(0).getCredentials().size();
        List<EventDto> listpleisovDto = events.getContent().stream()
                .map(eventConverter::toDto)
                .collect(Collectors.toList());
        log.info("events were received");
        return new PageImpl<>(listpleisovDto, pageable, events.getTotalElements());
    }
}
