package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.metamodel.Roles;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.repository.filter.EventSpecificationsBuilder;
import com.github.krishchik.whowithme.service.serviceImpl.EventServiceImpl;
import com.github.krishchik.whowithme.service.serviceImpl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    private final EventServiceImpl eventService;
    private final UserServiceImpl userService;
    private final EventSpecificationsBuilder builder;


    @PostMapping
    @Secured({Roles.ADMIN, Roles.USER})
    public void createEvent(
            Principal principal,
            @Valid @RequestBody EventDto eventDto) {
        eventService.createEvent(eventDto, principal);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("permitAll")
    public EventDto getEventById(@PathVariable Long id){
        return eventService.getEventById(id);
    }

    @PutMapping
    @Secured({Roles.ADMIN, Roles.USER})
    public void updateEvent(
            Principal principal,
            @Valid @RequestBody EventDto eventDto
    ) {
        eventService.updateEvent(eventDto, principal);
    }

    @DeleteMapping(value = "/{id}")
    @Secured({Roles.ADMIN, Roles.USER})
    public void deleteEvent(@PathVariable Long id, Principal principal) {
        eventService.deleteEvent(id, principal);
    }

    @GetMapping
    @PreAuthorize("permitAll")
    @CrossOrigin(origins = "http://localhost:8080")
    public Page<EventDto> getAll(
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "paramToSort", required = false, defaultValue = "id") String paramToSort,
            @RequestParam(value = "search", required = false) String search
    ) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search+",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), true);
        }
        Specification<Event> specification = builder.build();
        return eventService.getAllEvents(PageRequest.of(page, size, Sort.by(paramToSort)), specification);
    }

    @GetMapping(value = "/eventsByPlace/{id}")
    @Secured({Roles.USER, Roles.ADMIN})
    public Page<EventDto> getEventsByPlace(
            @PathVariable Long id,
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "paramToSort", required = false, defaultValue = "id") String paramToSort
    ) {
        return eventService.getEventsByPlace(PageRequest.of(page, size, Sort.by(paramToSort)),id);

    }

    @GetMapping(value = "usersEvents")
    @Secured({Roles.ADMIN, Roles.USER})
    public Page<EventDto> getUsersEvents(
            Principal userDto,
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "paramToSort", required = false, defaultValue = "id") String paramToSort
    ) {
        return eventService.getUsersEvents(PageRequest.of(page, size, Sort.by(paramToSort)),
                userService.getUserByLogin(userDto.getName()).getId());
    }

    @GetMapping(value = "/usersOfEvent/{id}")
    @PreAuthorize("permitAll")
    public Page<UserDto> getUsersOfEvent(
            @PathVariable Long id,
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "paramToSort", required = false, defaultValue = "id") String paramToSort
    )  {
        return eventService.getUsersOfEvent(PageRequest.of(page, size, Sort.by(paramToSort)),id);

    }

    @GetMapping(value = "/advertiseEvent/{id}")
    @Secured({Roles.USER, Roles.ADMIN})
    public String advertiseEvent(@PathVariable Long id) {

        eventService.getEventById(id);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity("http://rental-system22.herokuapp.com/api/cities", String.class);

        return response.getBody();



    }



}
