package com.github.krishchik.whowithme.service.converter;

import com.github.krishchik.whowithme.controller.Mapper.Mapper;
import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.Place;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class EventConverter {

    private final Mapper<EventDto, Event> eventMapper;

    public Event toEntity(EventDto eventDto){
        return eventMapper.toEntity(eventDto, Event.class);
    }

    public EventDto toDto(Event event){
        return eventMapper.toDto(event, EventDto.class);
    }

    public List<EventDto> listToDto(List<Event> events) {
        return eventMapper.listToDto(events, EventDto.class);
    }

}
