package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.EventRepository;
import com.github.krishchik.whowithme.model.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class EventRepositoryImpl implements EventRepository {

    private List<Event> repository = new ArrayList<>();

    @Override
    public Event getById(Long id) {
        for (Event entity : repository) {
            if(id.equals(entity.getId())) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public void save(Event entity) {
        repository.add(entity);
    }



    @Override
    public void delete(Long id) {
        repository.remove(id);
    }


    @Override
    public List<Event> getAll() {
        return new ArrayList<>(repository);
    }


    @Override
    public void update(Event updatedEvent) {
        Event event = getById(updatedEvent.getId());
        event.setEventName(updatedEvent.getEventName());
        event.setEventStatus(updatedEvent.getEventStatus());
        event.setAgeLimit(updatedEvent.getAgeLimit());
        event.setNumberOfPeople(updatedEvent.getNumberOfPeople());
    }

}
