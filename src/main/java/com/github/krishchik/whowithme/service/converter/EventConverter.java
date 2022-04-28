package com.github.krishchik.whowithme.service.converter;

import com.github.krishchik.whowithme.controller.Mapper.IEventMapper;
import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventConverter {

    private final IEventMapper eventMapper;

    public Event toEntity(EventDto eventDto){
        return eventMapper.toEntity(eventDto);
    }

    public EventDto toDto(Event event){
        return eventMapper.toDto(event);
    }



}
