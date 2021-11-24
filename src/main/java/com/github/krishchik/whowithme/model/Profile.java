package com.github.krishchik.whowithme.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class Profile {

    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "phone_number")
    private Long phoneNumber;
    @OneToOne(mappedBy = "profile")
    private User user;

}
