package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.EventRepository;
import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.service.converter.EventConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventConverter eventConverter;

    @Mock
    private EventRepository eventRepository;

    private final Event event = Event.builder().id(1l).build();

    private final EventDto eventDto = EventDto.builder().id(1l).build();
    @Test
    public void getById() throws Exception {
        when(eventRepository.getById(any())).thenReturn(event);
        when(eventConverter.toDto(any(Event.class))).thenReturn(eventDto);
        final EventDto eventDto = eventService.getEventById(1l);
        assertEquals(1l,eventDto.getId());
    }

    @Test
    public void deleteEvent() throws Exception {
        doNothing().when(eventRepository).delete(event);
        when(eventConverter.toEntity(any(EventDto.class))).thenReturn(event);
        eventService.deleteEvent(eventDto);
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    public void updateEvent() throws Exception {
        when(eventRepository.getById(any())).thenReturn(event);
        doNothing().when(eventRepository).update(event);
        eventService.updateEvent(eventDto);
        verify(eventRepository, times(1)).update(event);
    }

    @Test
    public void saveEvent() throws Exception {
        doNothing().when(eventRepository).save(event);
        when(eventConverter.toEntity(any(EventDto.class))).thenReturn(event);

        eventService.createEvent(eventDto);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void getAll() throws Exception {
        List<Event> events = new ArrayList<>();
        events.add(event);
        List<EventDto> eventDtos = new ArrayList<>();
        eventDtos.add(eventDto);
        when(eventRepository.getAll()).thenReturn(events);
        when(eventConverter.listToDto(anyList())).thenReturn(eventDtos);
        final List<EventDto> eventDtos1 = eventService.getAllEvents();
        assertEquals(1, eventDtos1.size());
    }

    @Test
    public void getEventsByPlace() {
        List<Event> events = new ArrayList<>();
        List<EventDto> eventDtoList = new ArrayList<>();
        when(eventRepository.getEventsByPlace(any())).thenReturn(events);
        when(eventConverter.listToDto(anyList())).thenReturn(eventDtoList);
        assertEquals(eventDtoList, eventService.getEventsByPlace(any()));
    }

}
