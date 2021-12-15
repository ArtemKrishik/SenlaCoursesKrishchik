package com.github.krishchik.whowithme.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@NamedEntityGraph(name = "user-profile-entity-graph",
        attributeNodes = @NamedAttributeNode("profile")
)
public class User extends AbstractEntity{

    @Id
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
    @OneToMany(mappedBy = "users")
    private List<Event> organizedEvents;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToMany
    @JoinTable(
            name = "users_events",
            joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "event_id")
    )
    private List<Event> events;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
