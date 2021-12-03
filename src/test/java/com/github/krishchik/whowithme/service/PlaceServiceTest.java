package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.PlaceRepository;
import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.service.converter.PlaceConverter;
import com.github.krishchik.whowithme.service.converter.UserConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceTest {

    @InjectMocks
    private PlaceServiceImpl placeService;

    @Mock
    private PlaceConverter placeConverter;

    @Mock
    private PlaceRepository placeRepository;

    private final Place place = Place.builder().id(1l).price(10).capacity(10).build();

    private final PlaceDto placeDto = PlaceDto.builder().id(1l).placeName("Name").price(10).build();
    @Test
    public void getById() throws Exception {
        when(placeRepository.getById(any())).thenReturn(place);
        when(placeConverter.toDto(any(Place.class))).thenReturn(placeDto);
        final PlaceDto placeDto = placeService.getPlaceById(1l);
        assertEquals(1l,placeDto.getId());
    }

    @Test
    public void deletePlace() throws Exception {
        doNothing().when(placeRepository).delete(place);
        when(placeConverter.toEntity(any(PlaceDto.class))).thenReturn(place);
        placeService.deletePlace(placeDto);
        verify(placeRepository, times(1)).delete(place);
    }

    @Test
    public void updatePlace() throws Exception {
        when(placeRepository.getById(any())).thenReturn(place);
        doNothing().when(placeRepository).update(place);
        placeService.updatePlace(placeDto);
        verify(placeRepository, times(1)).update(place);
    }

    @Test
    public void savePlace() throws Exception {
        doNothing().when(placeRepository).save(place);
        when(placeConverter.toEntity(any(PlaceDto.class))).thenReturn(place);

        placeService.createPlace(placeDto);
        verify(placeRepository, times(1)).save(place);
    }

    @Test
    public void getAll() throws Exception {
        List<Place> places = new ArrayList<>();
        places.add(place);
        List<PlaceDto> placeDtos = new ArrayList<>();
        placeDtos.add(placeDto);
        when(placeRepository.getAll()).thenReturn(places);
        when(placeConverter.listToDto(anyList())).thenReturn(placeDtos);
        final List<PlaceDto> placeDtos1 = placeService.getAllPlaces();
        assertEquals(1, placeDtos1.size());
    }

    @Test
    public void getPlacesSortedByCapacity() {
        List<Place> places = new ArrayList<>();
        List<PlaceDto> placeDtoList = new ArrayList<>();
        when(placeRepository.getPlacesSortedByCapacity()).thenReturn(places);
        when(placeConverter.listToDto(anyList())).thenReturn(placeDtoList);
        assertEquals(placeDtoList, placeService.getPlacesSortedByCapacity());
    }

    @Test
    public void getCheapestPlaces() {
        List<Place> places = new ArrayList<>();
        List<PlaceDto> placeDtoList = new ArrayList<>();
        when(placeRepository.getThreeCheapestPlaces()).thenReturn(places);
        when(placeConverter.listToDto(anyList())).thenReturn(placeDtoList);
        assertEquals(placeDtoList, placeService.getThreeCheapestPlaces());
    }

}
