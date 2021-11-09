package com.github.krishchik.whowithme.model;

import java.util.Objects;

public class Place extends AbstractEntity{

    private Long id;
    private String placeName;
    private Integer capacity;
    private Integer price;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
        Place place = (Place) o;
        return Objects.equals(id, place.id) && Objects.equals(placeName, place.placeName) && Objects.equals(capacity, place.capacity) && Objects.equals(price, place.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, placeName, capacity, price);
    }
}
