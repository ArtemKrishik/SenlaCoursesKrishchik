package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.Role;
import com.github.krishchik.whowithme.model.Credential;
import com.github.krishchik.whowithme.repository.EventCrudRepository;
import com.github.krishchik.whowithme.repository.filter.EventSpecification;
import com.github.krishchik.whowithme.repository.filter.SearchCriteria;
import com.github.krishchik.whowithme.service.converter.EventConverter;
import com.github.krishchik.whowithme.exception.OperationException;
import com.github.krishchik.whowithme.service.serviceImpl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.management.remote.JMXPrincipal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventConverter eventConverter;

    @Mock
    private EventCrudRepository eventRepository;

    @Mock
    private UserService userService;

    private final Principal principal = new JMXPrincipal("artem");
    private final Role roleAdmin = Role.builder().id(1l).name("ADMIN").build();
    private final Role roleUser = Role.builder().id(2l).name("USER").build();
    private final Credential credential = Credential.builder().id(1l).login("artem").role(roleAdmin).build();
    private final Event event = Event.builder().id(1l).creator(credential).startTime(new java.sql.Timestamp(System.currentTimeMillis())).endTime(new java.sql.Timestamp(System.currentTimeMillis())).build();

    private final EventDto eventDto = EventDto.builder().id(1l).startTime(LocalDateTime.now()).endTime(LocalDateTime.now()).build();
    @Test
    public void getById() {
        when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        when(eventConverter.toDto(any(Event.class))).thenReturn(eventDto);
        final EventDto eventDto = eventService.getEventById(1l);
        assertEquals(1l,eventDto.getId());
    }

    @Test
    public void deleteEvent()  {
        doNothing().when(eventRepository).delete(event);
        when(userService.getUserByLogin(principal.getName())).thenReturn(credential);
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "artem";
            }
        };
        when(eventRepository.findById(1l)).thenReturn(Optional.of(event));
        eventService.deleteEvent(1l, principal);
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    public void updateEvent() {
        when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        when(eventRepository.save(event)).thenReturn(event);
        when(userService.getUserByLogin(any())).thenReturn(credential);
        eventService.updateEvent(eventDto, principal);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void updateEventShouldPass() {
        when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        when(eventRepository.save(event)).thenReturn(event);
        when(userService.getUserByLogin(any())).thenReturn(Credential.builder().login("nikita").role(roleAdmin).build());
        eventService.updateEvent(eventDto, principal);
        verify(eventRepository, times(1)).save(event);

    }
    @Test
    public void updateEventShouldThrowOperationException() {
        when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        when(userService.getUserByLogin(any())).thenReturn(Credential.builder().login("nikita").role(roleUser).build());
        assertThrows(
                OperationException.class,
                () -> eventService.updateEvent(eventDto, principal)
        );
    }
    @Test
    public void saveEvent()  {
        when(eventRepository.save(any())).thenReturn(event);
        when(eventConverter.toEntity(any(EventDto.class))).thenReturn(event);
        when(userService.getUserByLogin(any())).thenReturn(credential);
        eventService.createEvent(eventDto, principal);
        verify(eventRepository, times(1)).save(event);
    }

    /*@Test
    public void getAll() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
        SearchCriteria searchCriteria = new SearchCriteria("id", ":", 1l, true);
        Specification<Event> specification = new EventSpecification(searchCriteria);
        List<Event> events = new ArrayList<>();
        events.add(event);
        Page<Event> pageOfEvents = new PageImpl<>(events, pageable, events.size());
        List<EventDto> eventDtos = new ArrayList<>();
        eventDtos.add(eventDto);
        when(eventRepository.findAll(specification, pageable)).thenReturn(pageOfEvents);
        final Page<EventDto> eventDtos1 = eventService.getAllEvents(pageable, specification);
        assertEquals(1, eventDtos1.getTotalElements());
        assertEquals(1, eventDtos1.getTotalPages());
    }*/

    @Test
    public void getEventsByPlace() {
        List<Event> events = new ArrayList<>();
        List<EventDto> eventDtoList = new ArrayList<>();
        //when(eventRepository.findEventsByPlaceId(any(), any())).thenReturn(events);
        //assertEquals(eventDtoList, eventService.getEventsByPlace(any()));
    }

}
