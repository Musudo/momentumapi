package com.musadzeyt.momentumapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @NotNull
    private String type;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    @NotNull
    private String subject;
    private String internalNote;
    private String externalNote;
    private LocalDateTime emailSentAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private Institution institution;
    @ManyToMany()
//    @JoinTable(
//            name = "activity_tags",
//            joinColumns = @JoinColumn(name = "activity_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id")
//    )
    private Set<Tag> tags = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "activity_contacts",
//            joinColumns = @JoinColumn(name = "activity_id"),
//            inverseJoinColumns = @JoinColumn(name = "contact_id")
//    )
    private Set<Contact> contacts = new HashSet<>();
    @ManyToMany()
//    @JoinTable(
//            name = "activity_external_participants",
//            joinColumns = @JoinColumn(name = "activity_id"),
//            inverseJoinColumns = @JoinColumn(name = "external_participant_id")
//    )
    private Set<ExternalParticipant> externalParticipants = new HashSet<>();
    @OneToMany()
    @JoinColumn(updatable = false)
    private Set<Task> tasks = new HashSet<>();
    @OneToMany()
    @JoinColumn(updatable = false)
    private Set<Review> reviews = new HashSet<>();
}
