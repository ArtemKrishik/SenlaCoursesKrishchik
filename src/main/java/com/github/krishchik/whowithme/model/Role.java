package com.github.krishchik.whowithme.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roles")
public class Role extends AbstractEntity {


    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "role")
    private List<User> users;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }

    @Builder
    public Role(Long id, String name) {
        super(id);
        this.name = name;
    }
}
