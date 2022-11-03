package com.example.etutorbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "profile_image_path")
    private String profileImagePath;

    @Column(name = "enabled")
    private boolean enabled = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private Date lastUpdated;



    @OneToMany(cascade = ALL, mappedBy = "user")
    
    private Set<ConfirmationToken> confirmationTokens = new HashSet<>();

    @OneToMany(cascade = ALL, mappedBy = "user")
    
    private List<Advertisement> advertisements = new ArrayList<>();

    @OneToMany(cascade = ALL, mappedBy = "user")
    
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    
    private List<Message> messagesReceived = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    
    private List<Message> messagesSent = new ArrayList<>();

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "user_role",
        joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")
        }
    )
    private Set<Role> roles = new HashSet<>();

    public Set<Authority> getAuthorities() {
        return roles
                .stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
