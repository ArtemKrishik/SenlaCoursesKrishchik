package com.github.krishchik.whowithme.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonMapper {


    private ObjectMapper objectMapper = new ObjectMapper();

    public User convertToUser(String jsonUserString) throws JsonProcessingException {
        return objectMapper.readValue(jsonUserString, User.class);
    }

    public String convertUserTOJson(User user) throws JsonProcessingException {
        return objectMapper.writeValueAsString(user);
    }

    public String convertUserTOJson(List<User> allUsers) throws JsonProcessingException {
        return objectMapper.writeValueAsString(allUsers);
    }

    public Place convertToPlace(String jsonPlaceString) throws JsonProcessingException {
        return objectMapper.readValue(jsonPlaceString, Place.class);
    }

    public String convertPlaceToJson(Place place) throws JsonProcessingException {
        return objectMapper.writeValueAsString(place);
    }

    public String convertPlaceToJson(List<Place> allPlaces) throws JsonProcessingException {
        return objectMapper.writeValueAsString(allPlaces);
    }

    public Event convertToEvent(String jsonEventString) throws JsonProcessingException {
        return objectMapper.readValue(jsonEventString, Event.class);
    }

    public String convertEventToJson(Event event) throws JsonProcessingException {
        return objectMapper.writeValueAsString(event);
    }

    public String convertEventToJson(List<Event> allEvents) throws JsonProcessingException {
        return objectMapper.writeValueAsString(allEvents);
    }

}
