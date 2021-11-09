package com.github.krishchik.whowithme.model;

import java.util.Objects;

public class Event extends AbstractEntity{

    private Long id;
    private String eventName;
    private EventStatus eventStatus;
    private Integer numberOfPeople;
    private Integer ageLimit;


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
                eventStatus == event.eventStatus &&
                Objects.equals(numberOfPeople, event.numberOfPeople) &&
                Objects.equals(ageLimit, event.ageLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventStatus, numberOfPeople, ageLimit);
    }
}
