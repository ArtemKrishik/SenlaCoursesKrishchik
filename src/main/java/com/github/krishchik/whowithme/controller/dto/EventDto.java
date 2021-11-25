package com.github.krishchik.whowithme.controller.dto;

import com.github.krishchik.whowithme.model.EventStatus;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.model.User;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventDto {

    private Long id;
    private String eventName;
    private EventStatus eventStatus;
    private Integer numberOfPeople;
    private Integer ageLimit;
    private LocalDateTime date;
    private LocalDateTime startTime;
    private UserDto creator;
    private PlaceDto place;


}
