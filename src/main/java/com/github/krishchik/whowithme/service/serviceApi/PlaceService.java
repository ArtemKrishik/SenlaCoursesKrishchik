package com.github.krishchik.whowithme.service.serviceApi;

import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceService {

    void createPlace(PlaceDto createdPlace);

    void updatePlace(PlaceDto updatedPlace);

    PlaceDto getPlaceById(Long placeId);

    void deletePlace(Long placeId);

    Page<PlaceDto> getAllPlaces(Pageable pageable);



}
