package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.service.PlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/places")
public class PlaceControllerImpl {

    @Autowired
    private final PlaceServiceImpl placeService;

    public PlaceControllerImpl(PlaceServiceImpl placeService) {
        this.placeService = placeService;
    }

    @PostMapping
    public void createPlace(@RequestBody PlaceDto placeDto) throws Exception {
        placeService.createPlace(placeDto);
    }

    @GetMapping(value = "/{placeId}")
    public PlaceDto getPlaceById(@PathVariable Long placeId) throws Exception {
        return placeService.getPlaceById(placeId);
    }

    @PutMapping
    public void updatePlace(@RequestBody PlaceDto placeDto) throws Exception {
        placeService.updatePlace(placeDto);
    }

    @DeleteMapping
    public void deletePlace(@RequestBody PlaceDto placeDto) throws Exception {
        placeService.deletePlace(placeDto);
    }

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getAll() throws Exception {
        return new ResponseEntity<>(placeService.getAllPlaces(), HttpStatus.OK);
    }

    public List<PlaceDto> getPlacesSortedByCapacity() throws Exception {
        return placeService.getPlacesSortedByCapacity();
    }

    public List<PlaceDto> getThreeCheapestPlaces() throws Exception {
        return placeService.getThreeCheapestPlaces();
    }

}
