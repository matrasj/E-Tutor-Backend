package com.example.etutorbackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "minutes_duration")
    private int minutesDuration;

    @Column(name = "short_desc", columnDefinition = "TEXT")
    private String shortDesc;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private Date lastUpdated;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    @ManyToOne(cascade = {
            DETACH, MERGE, PERSIST, REFRESH
    })
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(cascade = {
            DETACH, MERGE, REFRESH, PERSIST
    })
    @JoinTable(name = "advertisement_place",
    joinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "place_id", referencedColumnName = "id"))
    private List<Place> places = new ArrayList<>();

    @ManyToOne(cascade = {
            DETACH, MERGE, PERSIST, REFRESH
    })
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @ManyToMany(cascade = {
            DETACH, MERGE, REFRESH, PERSIST
    })
    @JoinTable(name = "advertisement_range",
            joinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "range_id", referencedColumnName = "id")
    )
    private List<LessonRange> lessonRanges = new ArrayList<>();

    @Column(name = "advertisement_type")
    @Enumerated(STRING)
    private AdvertisementType advertisementType;

    @OneToMany(cascade = ALL, mappedBy = "advertisement")
    private List<Availability> availabilities = new ArrayList<>();

    @OneToMany(cascade = ALL, mappedBy = "advertisement")
    private List<Review> reviews = new ArrayList<>();


}
