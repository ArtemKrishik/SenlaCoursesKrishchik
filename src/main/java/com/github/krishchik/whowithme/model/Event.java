package com.github.krishchik.whowithme.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event extends AbstractEntity{
    @Id
    private Long id;
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;
    @Column(name = "number_of_people")
    private Integer numberOfPeople;
    @Column(name = "age_limit")
    private Integer ageLimit;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_events",
            joinColumns = @JoinColumn (name = "event_id"),
            inverseJoinColumns = @JoinColumn (name = "user_id")
    )
    private List<User> users;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", eventStatus=" + eventStatus +
                ", numberOfPeople=" + numberOfPeople +
                ", ageLimit=" + ageLimit +
                ", date=" + date +
                ", startTime=" + startTime +
                '}';
    }
}
