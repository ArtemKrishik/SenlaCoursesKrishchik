package com.github.krishchik.whowithme.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "places")
public class Place extends AbstractEntity{

    @Id
    private Long id;
    //@Column(name = "place_name") //dobavit v basy
    //private String placeName;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "price")
    private Integer price;
    @OneToMany(mappedBy = "place")
    private List<Event> eventList;

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", price=" + price +
                '}';
    }
}
