package com.github.krishchik.whowithme.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "places")
public class Place extends AbstractEntity{

    @Column(name = "place_name")
    private String placeName;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "price")
    private Integer price;
    @OneToMany(mappedBy = "place")
    private List<Event> eventList;

    @Override
    public String toString() {
        return "Place{" +
                "id=" + getId() +
                ", capacity=" + capacity +
                ", price=" + price +
                '}';
    }
    @Builder
    public Place(Long id, Integer price, Integer capacity) {
        super(id);
        this.price = price;
        this.capacity = capacity;

    }

}
