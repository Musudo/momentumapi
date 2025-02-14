package com.musadzeyt.momentumapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email1;
    private String email2;
    @NotNull
    private String phone1;
    private String phone2;
    private String jobTitle;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private User user;
    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "contact_institutions",
//            joinColumns = @JoinColumn(name = "contact_id"),
//            inverseJoinColumns = @JoinColumn(name = "institution_id")
//    )
    private Set<Institution> institutions= new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "contact_activities",
//            joinColumns = @JoinColumn(name = "contact_id"),
//            inverseJoinColumns = @JoinColumn(name = "activity_id")
//    )
    private Set<Activity> activities= new HashSet<>();
}
