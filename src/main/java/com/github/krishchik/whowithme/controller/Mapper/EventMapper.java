package com.github.krishchik.whowithme.controller.Mapper;

import com.github.krishchik.whowithme.repository.repositoryApi.PlaceCrudRepository;
import com.github.krishchik.whowithme.repository.repositoryApi.UserCrudRepository;
import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.model.Event;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class EventMapper implements IEventMapper{

    private final ModelMapper mapper;

    private final PlaceCrudRepository placeRepository;

    private final UserCrudRepository userRepository;


    @PostConstruct
    public void setupMapper() {
        TypeMap<Event, EventDto> typeMap = mapper.getTypeMap(Event.class, EventDto.class);
        if (typeMap == null) {
            mapper.createTypeMap(Event.class, EventDto.class)
                    .addMappings(m -> m.skip(EventDto::setStartTime)).setPostConverter(toDtoConverter())
                    .addMappings(m -> m.skip(EventDto::setEndTime)).setPostConverter(toDtoConverter())
                    .addMappings(m -> m.skip(EventDto::setCreatorId)).setPostConverter(toDtoConverter())
                    .addMappings(m -> m.skip(EventDto::setPlaceId)).setPostConverter(toDtoConverter());
            mapper.createTypeMap(EventDto.class, Event.class)
                    .addMappings(m -> m.skip(Event::setStartTime)).setPostConverter(toEntityConverter())
                    .addMappings(m -> m.skip(Event::setEndTime)).setPostConverter(toEntityConverter())
                    .addMappings(m -> m.skip(Event::setCreator)).setPostConverter(toEntityConverter())
                    .addMappings(m -> m.skip(Event::setPlace)).setPostConverter(toEntityConverter());
        }
    }

    void mapSpecificFields(Event source, EventDto destination) {
        destination.setStartTime(getStartTime(source));
        destination.setEndTime(getEndTime(source));
        destination.setCreatorId(getCreatorId(source));
        destination.setPlaceId(getPlaceId(source));
    }

    private Long getCreatorId(Event source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getCreator().getId();
    }

    private Long getPlaceId(Event source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getPlace().getId();
    }

    private LocalDateTime getStartTime(Event source) {
        return Objects.isNull(source) || Objects.isNull(source.getStartTime()) ? null : source.getStartTime().toLocalDateTime();
    }

    private LocalDateTime getEndTime(Event source) {
        return Objects.isNull(source) || Objects.isNull(source.getEndTime()) ? null : source.getEndTime().toLocalDateTime();
    }


    void mapSpecificFields(EventDto source, Event destination) throws Exception {
        destination.setStartTime(Timestamp.valueOf(source.getStartTime()));
        destination.setEndTime(Timestamp.valueOf(source.getEndTime()));
        destination.setCreator(userRepository.getById(source.getCreatorId()));
        destination.setPlace(placeRepository.getById(source.getPlaceId()));

    }

    Converter<Event, EventDto> toDtoConverter() {
        return context -> {
            Event source = context.getSource();
            EventDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<EventDto, Event> toEntityConverter() {
        return context -> {
            EventDto source = context.getSource();
            Event destination = context.getDestination();
            try {
                mapSpecificFields(source, destination);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return context.getDestination();
        };
    }

    @Override
    public Event toEntity(EventDto dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, Event.class);
    }

    @Override
    public EventDto toDto(Event entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, EventDto.class);
    }
}
