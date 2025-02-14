package com.musadzeyt.momentumapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
public class Institution {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String street;
    @NotNull
    private String buildingNumber;
    private String postbox;
    @NotNull
    private String city;
    @NotNull
    private String postalCode;
    @NotNull
    private String countryCode;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private User user;
    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "institution_contacts",
//            joinColumns = @JoinColumn(name = "institution_id"),
//            inverseJoinColumns = @JoinColumn(name = "contact_id")
//    )
    private List<Contact> contacts;
    @OneToMany()
    @JoinColumn(updatable = false)
    private List<Activity> activities;
}
