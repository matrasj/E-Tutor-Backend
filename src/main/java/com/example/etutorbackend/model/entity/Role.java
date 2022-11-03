package com.example.etutorbackend.model.entity;

import lombok.*;

import javax.persistence.*;

import java.security.Permission;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = {
            DETACH, MERGE, PERSIST, REFRESH
    })
    private Set<User> users = new HashSet<>();

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "role_authority",
        joinColumns = {
                @JoinColumn(name = "role_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
                @JoinColumn(name = "authority_id", referencedColumnName = "id")
        }
    )
    private Set<Authority> authorities = new HashSet<>();
}
