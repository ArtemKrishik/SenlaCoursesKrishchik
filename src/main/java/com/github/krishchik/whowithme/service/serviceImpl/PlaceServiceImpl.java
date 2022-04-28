package com.github.krishchik.whowithme.service.serviceImpl;

import com.github.krishchik.whowithme.repository.repositoryApi.PlaceCrudRepository;
import com.github.krishchik.whowithme.service.serviceApi.PlaceService;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.service.converter.PlaceConverter;
import com.github.krishchik.whowithme.service.exception.OperationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PlaceServiceImpl implements PlaceService {


    @Autowired
    private final PlaceCrudRepository placeCrudRepository;
    private final PlaceConverter placeConverter;

    @Override
    @Transactional
    public Page<PlaceDto> getAllPlaces(Pageable pageable) {
        Page<Place> places = placeCrudRepository.findAll(pageable);
        List<PlaceDto> listplaceDto = places.getContent().stream()
                .map(placeConverter::toDto)
                .collect(Collectors.toList());
        log.info("places were received");

        return new PageImpl<>(listplaceDto, pageable, places.getTotalElements());
    }

    @Override
    @Transactional
    public void createPlace(PlaceDto placeDto) {

        placeCrudRepository.save(placeConverter.toEntity(placeDto));
        log.info("place with name "+ placeDto.getPlaceName()+ "\n id"+ placeDto.getId() + "was awarded");

    }

    @Override
    @Transactional
    public void updatePlace(PlaceDto placeDto) {
        Place placeToChange = placeCrudRepository.findById(placeDto.getId())
                .orElseThrow(() -> new OperationException("Incorrect input when trying change place"));
        placeToChange.setPrice(placeDto.getPrice());
        placeToChange.setCapacity(placeDto.getCapacity());
        placeCrudRepository.save(placeToChange);
        log.info("place with id "+ placeDto.getId()+ "and name " + placeDto.getPlaceName() + "has been updated");

    }

    @Override
    @Transactional
    public PlaceDto getPlaceById(Long placeId) {
        log.info("place with id "+ placeId+ "was received");
        return placeConverter.toDto(placeCrudRepository.findById(placeId)
                    .orElseThrow(() -> new OperationException("place with id " + placeId + " wasn`t found")));
    }

    @Override
    @Transactional
    public void deletePlace(Long id) {
        Place place = placeCrudRepository.findById(id)
                .orElseThrow(() -> new OperationException("place with id " + id + " wasn`t found"));
        placeCrudRepository.delete(place);
        log.info("place with id"+ id + " has been deleted");
    }

}
