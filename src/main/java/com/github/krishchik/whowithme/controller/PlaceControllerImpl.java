package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.service.PlaceServiceImpl;
import com.github.krishchik.whowithme.util.JsonMapper;
import org.springframework.stereotype.Component;

@Component
public class PlaceControllerImpl {

    private final PlaceServiceImpl placeService;
    private final JsonMapper jsonMapper;

    public PlaceControllerImpl(PlaceServiceImpl placeService ,JsonMapper jsonMapper) {
        this.placeService = placeService;
        this.jsonMapper = jsonMapper;
    }

    public void createPlace(String userJsonString) throws Exception {
        Place place = jsonMapper.convertToPlace(userJsonString);
        placeService.createPlace(place);
    }

    public String getPlaceById(Long placeId) throws Exception {
        Place place = placeService.getPlaceById(placeId);
        return jsonMapper.convertPlaceToJson(place);
    }

    public void updatePlace(String placeJsonString) throws Exception {
        Place place = jsonMapper.convertToPlace(placeJsonString);
        placeService.updatePlace(place);
    }

    public void deletePlace(String placeJsonString) throws Exception {
        Place place = jsonMapper.convertToPlace(placeJsonString);
        placeService.deletePlace(place);
    }

    public String getAll() throws Exception {
        return jsonMapper.convertPlaceToJson(placeService.getAllPlaces());
    }

}
