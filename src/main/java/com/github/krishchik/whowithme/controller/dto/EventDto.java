package com.github.krishchik.whowithme.controller.dto;

import com.github.krishchik.whowithme.model.EventStatus;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.model.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto extends AbstractDto{

    @NotNull
    private String eventName;
    @NotNull
    private EventStatus eventStatus;
    @NotNull
    private Integer numberOfSlots;
    @NotNull
    private Integer availableSlots;
    @NonNull
    private Integer ageLimit;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    @NotNull
    private Long placeId;
    private Long creatorId;

    @Builder
    public EventDto(Long id, String eventName, LocalDateTime startTime, LocalDateTime endTime) {
        super(id);
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
    }


}
