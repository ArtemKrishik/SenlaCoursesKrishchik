package com.github.krishchik.whowithme.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@NamedEntityGraph(name = "user-profile-entity-graph",
        attributeNodes = @NamedAttributeNode("profile")
)
@NamedEntityGraph(name = "user-roles-entity-graph",
        attributeNodes = @NamedAttributeNode("role")
)
@NamedEntityGraph(name = "user-events-entity-graph",
        attributeNodes = @NamedAttributeNode("events")
)
public class Credential extends AbstractEntity{


    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
    @OneToMany(mappedBy = "creator")
    private List<Event> organizedEvents;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToMany
    @JoinTable(
            name = "users_events",
            joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "event_id")
    )
    private List<Event> events;

    public void addEvent(Event event) {
        events.add(event);
    }

    @Override
    public String toString() {
        return "Credential{" +
                "id=" + getId() +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Builder
    public Credential(Long id, String login, String password, Role role) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
