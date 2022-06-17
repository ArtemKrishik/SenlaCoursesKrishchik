package com.github.krishchik.whowithme.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class Profile extends AbstractEntity{


    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "phone_number")
    private Long phoneNumber;
    @OneToOne(mappedBy = "profile")
    private Credential credential;

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
