package com.example.etutorbackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "seen")
    private boolean seen = false;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne(cascade = {
            DETACH, MERGE, PERSIST, REFRESH
    })
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne(cascade = {
            DETACH, MERGE, PERSIST, REFRESH
    })
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User receiver;

    @ManyToOne(cascade = {
            DETACH, MERGE, PERSIST, REFRESH
    })
    @JoinColumn(name = "advertisement_id", referencedColumnName = "id")
    private Advertisement advertisement;
}
