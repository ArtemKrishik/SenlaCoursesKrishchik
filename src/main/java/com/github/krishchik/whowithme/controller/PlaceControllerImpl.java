package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.service.serviceImpl.PlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/places")
@Validated
public class PlaceControllerImpl {

    @Autowired
    private final PlaceServiceImpl placeService;

    public PlaceControllerImpl(PlaceServiceImpl placeService) {
        this.placeService = placeService;
    }


    @PostMapping(value = "/admin")
    public void createPlace(@Valid @RequestBody PlaceDto placeDto) {
        placeService.createPlace(placeDto);
    }

    @GetMapping(value = "user/{id}")
    public PlaceDto getPlaceById(@PathVariable Long id) {
        return placeService.getPlaceById(id);
    }

    @PutMapping(value = "/admin")
    public void updatePlace(@Valid @RequestBody PlaceDto placeDto) {
        placeService.updatePlace(placeDto);
    }

    @DeleteMapping(value = "/admin/{id}")
    public void deletePlace(@PathVariable Long id) {
        placeService.deletePlace(id);
    }

    @GetMapping(value = "/user")
    public List<PlaceDto> getAll(
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "paramToSort", required = false, defaultValue = "id") String paramToSort
            ) {
        return placeService.getAllPlaces(PageRequest.of(page, size, Sort.by(paramToSort)))
                .stream().toList();
    }



}
