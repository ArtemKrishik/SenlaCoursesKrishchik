package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.metamodel.Roles;
import com.github.krishchik.whowithme.service.serviceImpl.PlaceServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/places")
@Validated
@AllArgsConstructor
public class PlaceController {

    private final PlaceServiceImpl placeService;

    @PostMapping
    @Secured(Roles.ADMIN)
    public void createPlace(@Valid @RequestBody PlaceDto placeDto) {
        placeService.createPlace(placeDto);
    }

    @GetMapping(value = "/{id}")
    @Secured({Roles.ADMIN, Roles.USER})
    public PlaceDto getPlaceById(@PathVariable Long id) {
        return placeService.getPlaceById(id);
    }

    @PutMapping
    @Secured(Roles.ADMIN)
    public void updatePlace(@Valid @RequestBody PlaceDto placeDto) {
        placeService.updatePlace(placeDto);
    }

    @DeleteMapping(value = "/{id}")
    @Secured(Roles.ADMIN)
    public void deletePlace(@PathVariable Long id) {
        placeService.deletePlace(id);
    }

    @GetMapping
    @Secured({Roles.USER, Roles.ADMIN})
    public Page<PlaceDto> getAll(
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "paramToSort", required = false, defaultValue = "id") String paramToSort
            ) {
        return placeService.getAllPlaces(PageRequest.of(page, size, Sort.by(paramToSort)));

    }



}
