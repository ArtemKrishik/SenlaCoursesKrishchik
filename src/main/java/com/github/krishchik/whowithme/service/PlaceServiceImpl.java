package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.PlaceRepository;
import com.github.krishchik.whowithme.api.service.PlaceService;
import com.github.krishchik.whowithme.controller.Mapper.Mapper;
import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private final PlaceRepository placeRepository;

    private final Mapper<PlaceDto, Place> placeMapper;

    private final Mapper<EventDto, Event> eventMapper;

    public PlaceServiceImpl(PlaceRepository placeRepository, Mapper<PlaceDto, Place> placeMapper, Mapper<EventDto, Event> eventMapper) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
        this.eventMapper = eventMapper;
    }


    @Override
    @Transactional
    public void createPlace(PlaceDto placeDto) throws Exception {
        placeRepository.save(placeMapper.toEntity(placeDto, Place.class));
    }

    @Override
    @Transactional
    public void updatePlace(PlaceDto placeDto) throws Exception {
        placeRepository.update(placeMapper.toEntity(placeDto, Place.class));
    }

    @Override
    @Transactional
    public PlaceDto getPlaceById(Long placeId) throws Exception {

        Place place = placeRepository.getById(placeId);
        PlaceDto placeDto = placeMapper.toDto(place, PlaceDto.class);
        return placeDto;

    }

    @Override
    @Transactional
    public void deletePlace(PlaceDto placeDto) throws Exception {
        placeRepository.delete(placeRepository.getById(placeMapper.toEntity(placeDto, Place.class).getId()));
    }

    @Override
    @Transactional
    public List<PlaceDto> getAllPlaces() throws Exception {
        return placeMapper.listToDto(placeRepository.getAll(), Place.class);
    }

    @Override
    @Transactional
    public List<PlaceDto> getPlacesSortedByCapacity() {
        return placeMapper.listToDto(placeRepository.getPlacesSortedByCapacity(), Place.class);
    }

    @Override
    @Transactional
    public List<PlaceDto> getThreeCheapestPlaces() {
        return placeMapper.listToDto(placeRepository.getThreeCheapestPlaces(), Place.class);
    }

}
