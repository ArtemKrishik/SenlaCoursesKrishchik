package com.github.krishchik.whowithme.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends AbstractEntity{

    @Column(name = "event_name")
    private String eventName;
    @Transient
    private EventStatus eventStatus;
    @Column(name = "number_of_slots")
    private Integer numberOfSlots;
    @Column(name = "available_slots")
    private Integer availableSlots;
    @Column(name = "age_limit")
    private Integer ageLimit;

    @Column(name = "end_time")
    private Timestamp endTime;
    @Column(name = "start_time")
    private Timestamp startTime;

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

    public void addUser(User user) {
        users.add(user);
    }



    @Override
    public String toString() {
        return "Event{" +
                "id=" + getId() +
                ", eventName='" + eventName + '\'' +
                ", eventStatus=" + eventStatus +
                ", ageLimit=" + ageLimit +
                ", startTime=" + startTime +
                '}';
    }

    @Builder
    public Event(Long id, String eventName, EventStatus eventStatus, Integer ageLimit, Integer numberOfSlots, Timestamp startTime, Timestamp endTime, User creator) {
        super(id);
        this.eventName = eventName;
        this.eventStatus = eventStatus;
        this.ageLimit = ageLimit;
        this.numberOfSlots = numberOfSlots;
        this.startTime = startTime;
        this.endTime = endTime;
        this.creator = creator;
    }
}
