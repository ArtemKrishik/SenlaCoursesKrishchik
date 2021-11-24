package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.service.PlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceControllerImpl {

    @Autowired
    private final PlaceServiceImpl placeService;

    public PlaceControllerImpl(PlaceServiceImpl placeService) {
        this.placeService = placeService;
    }

    public void createPlace(PlaceDto placeDto) throws Exception {
        placeService.createPlace(placeDto);
    }

    public PlaceDto getPlaceById(Long placeId) throws Exception {
        return placeService.getPlaceById(placeId);
    }

    public void updatePlace(PlaceDto placeDto) throws Exception {
        placeService.updatePlace(placeDto);
    }

    public void deletePlace(PlaceDto placeDto) throws Exception {
        placeService.deletePlace(placeDto);
    }

    public List<PlaceDto> getAll() throws Exception {
        return placeService.getAllPlaces();
    }

    public List<PlaceDto> getPlacesSortedByCapacity() throws Exception {
        return placeService.getPlacesSortedByCapacity();
    }

    public List<PlaceDto> getThreeCheapestPlaces() throws Exception {
        return placeService.getThreeCheapestPlaces();
    }

}
