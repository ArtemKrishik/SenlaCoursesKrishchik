package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.repository.PlaceCrudRepository;
import com.github.krishchik.whowithme.service.converter.PlaceConverter;
import com.github.krishchik.whowithme.exception.OperationException;
import com.github.krishchik.whowithme.service.serviceImpl.PlaceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceTest {

    @InjectMocks
    private PlaceServiceImpl placeService;

    @Mock
    private PlaceConverter placeConverter;

    @Mock
    private PlaceCrudRepository placeCrudRepository;



    private final Place place = Place.builder().id(1l).price(10).capacity(10).build();

    private final PlaceDto placeDto = PlaceDto.builder().id(1l).placeName("Name").price(10).build();
    @Test
    public void getById() throws Exception {
        when(placeCrudRepository.findById(any())).thenReturn(Optional.of(place));
        when(placeConverter.toDto(any(Place.class))).thenReturn(placeDto);
        final PlaceDto placeDto = placeService.getPlaceById(1l);
        assertEquals(1l,placeDto.getId());
    }

    @Test
    public void getByIdReturnOperationException() throws Exception {
        when(placeCrudRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(
                OperationException.class,
                () -> placeService.getPlaceById(5l)
        );
    }

    @Test
    public void deletePlace() throws Exception {
        doNothing().when(placeCrudRepository).delete(place);
        when(placeCrudRepository.findById(1l)).thenReturn(Optional.of(place));
        placeService.deletePlace(1l);
        verify(placeCrudRepository, times(1)).delete(place);
    }

    @Test
    public void deletePlaceReturnOperationException() throws Exception {
        when(placeCrudRepository.findById(5l)).thenReturn(Optional.ofNullable(null));
        assertThrows(
                OperationException.class,
                () -> placeService.deletePlace(5l)
        );
    }

    @Test
    public void updatePlace() throws Exception {
        when(placeCrudRepository.findById(any())).thenReturn(Optional.of(place));
        when(placeCrudRepository.save(place)).thenReturn(place);
        placeService.updatePlace(placeDto);
        verify(placeCrudRepository, times(1)).save(place);
    }

    @Test
    public void updatePlaceReturnOperationException() throws Exception {
        when(placeCrudRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(
                OperationException.class,
                () -> placeService.updatePlace(placeDto)
        );
    }

    @Test
    public void savePlace() throws Exception {
        when(placeCrudRepository.save(place)).thenReturn(place);
        when(placeConverter.toEntity(any(PlaceDto.class))).thenReturn(place);

        placeService.createPlace(placeDto);
        verify(placeCrudRepository, times(1)).save(place);
    }

    @Test
    public void getAll() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
        List<Place> places = new ArrayList<>();
        places.add(place);
        Page<Place> pageOfPlaces = new PageImpl<>(places, pageable, places.size());
        when(placeCrudRepository.findAll(pageable)).thenReturn(pageOfPlaces);
        when(placeConverter.toDto(any())).thenReturn(placeDto);
        final Page<PlaceDto> placeDtos1 = placeService.getAllPlaces(pageable);
        assertEquals(1, placeDtos1.getTotalElements());
        assertEquals(1, placeDtos1.getTotalPages());

    }
}
