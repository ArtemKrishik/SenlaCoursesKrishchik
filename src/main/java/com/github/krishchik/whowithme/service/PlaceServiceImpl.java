package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.PlaceRepository;
import com.github.krishchik.whowithme.api.service.PlaceService;
import com.github.krishchik.whowithme.controller.Mapper.Mapper;
import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.service.converter.EventConverter;
import com.github.krishchik.whowithme.service.converter.PlaceConverter;
import com.github.krishchik.whowithme.service.exception.OperationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private final PlaceRepository placeRepository;
    private final PlaceConverter placeConverter;

    @Override
    @Transactional
    public void createPlace(PlaceDto placeDto) throws Exception {
        placeRepository.save(placeConverter.toEntity(placeDto));
    }

    @Override
    @Transactional
    public void updatePlace(PlaceDto placeDto) throws Exception {
        Place placeToChange = placeRepository.getById(placeDto.getId());
        if(placeToChange == null) throw new OperationException("Incorrect input when tryin change place");
        placeToChange.setPrice(placeDto.getPrice());
        placeToChange.setCapacity(placeDto.getCapacity());
        placeRepository.update(placeToChange);
    }

    @Override
    @Transactional
    public PlaceDto getPlaceById(Long placeId) throws Exception {
        Place place = placeRepository.getById(placeId);
        if(place == null) throw new OperationException("place with id "+placeId+" wasn`t found");
        return placeConverter.toDto(place);
    }

    @Override
    @Transactional
    public void deletePlace(PlaceDto placeDto) throws Exception {
        placeRepository.delete(placeConverter.toEntity(placeDto));
    }

    @Override
    @Transactional
    public List<PlaceDto> getAllPlaces() throws Exception {
        return placeConverter.listToDto(placeRepository.getAll());
    }

    @Override
    @Transactional
    public List<PlaceDto> getPlacesSortedByCapacity() {
        return placeConverter.listToDto(placeRepository.getPlacesSortedByCapacity());
    }

    @Override
    @Transactional
    public List<PlaceDto> getThreeCheapestPlaces() {
        return placeConverter.listToDto(placeRepository.getThreeCheapestPlaces());
    }

}
