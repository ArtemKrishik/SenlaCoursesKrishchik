package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.repository.filter.EventSpecificationsBuilder;
import com.github.krishchik.whowithme.service.serviceImpl.EventServiceImpl;
import com.github.krishchik.whowithme.service.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
public class EventControllerImpl {

    @Autowired
    private final EventServiceImpl eventService;
    @Autowired
    private final UserServiceImpl userService;



    @PostMapping(value = "/user")
    public void createEvent(
            Principal principal,
            @Valid @RequestBody EventDto eventDto) {
        eventService.createEvent(eventDto, principal);
    }

    @GetMapping(value = "user/{id}")
    public EventDto getEventById(@PathVariable Long id){
        return eventService.getEventById(id);
    }

    @PutMapping(value = "/user")
    public void updateEvent(
            Principal principal,
            @Valid @RequestBody EventDto eventDto
    ) {
        eventService.updateEvent(eventDto, principal);
    }

    @DeleteMapping(value = "/user/{id}")
    public void deleteEvent(@PathVariable Long id, Principal principal) {
        eventService.deleteEvent(id, principal);
    }

    @GetMapping(value = "/user")
    public Page<EventDto> getAll(
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "paramToSort", required = false, defaultValue = "id") String paramToSort,
            @RequestParam(value = "search", required = false) String search
    ) {
        EventSpecificationsBuilder builder = new EventSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search+",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), true);
        }
        Specification<Event> specification = builder.build();
        return eventService.getAllEvents(PageRequest.of(page, size, Sort.by(paramToSort)), specification);

    }

    @GetMapping(value = "user/eventsByPlace/{id}")
    public List<EventDto> getEventsByPlace(
            @PathVariable Long id,
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "paramToSort", required = false, defaultValue = "id") String paramToSort
    ) {
        return eventService.getEventsByPlace(PageRequest.of(page, size, Sort.by(paramToSort)),id)
                .stream().toList();
    }

    @GetMapping(value = "user/usersEvents")
    public List<EventDto> getUsersEvents(
            Principal userDto,
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "paramToSort", required = false, defaultValue = "id") String paramToSort
    ) {


        return eventService.getUsersEvents(PageRequest.of(page, size, Sort.by(paramToSort)),
                userService.getUserByLogin(userDto.getName()).getId())
                .stream().toList();
    }

    @GetMapping(value = "/user/usersOfEvent/{id}")
    public List<UserDto> getUsersOfEvent(
            @PathVariable Long id,
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "paramToSort", required = false, defaultValue = "id") String paramToSort
    )  {
        return eventService.getUsersOfEvent(PageRequest.of(page, size, Sort.by(paramToSort)),id)
                .stream().toList();
    }

}
