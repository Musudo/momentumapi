package com.musadzeyt.momentumapi.domain;

import com.musadzeyt.momentumapi.util.RolesConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
import java.util.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Email
    private String email;
    @Column(name = "roles", columnDefinition = "TEXT")
    @Convert(converter = RolesConverter.class)
    private Set<String> roles = new HashSet<>(Set.of("ROLE_USER"));
    private String password;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
//    @OneToMany(cascade = CascadeType.REMOVE)
//    private List<Activity> activities;
//    @OneToMany(cascade = CascadeType.REMOVE)
//    private List<Review> reviews;
//    @OneToMany(cascade = CascadeType.REMOVE)
//    private List<Institution> institutions;
//    @OneToMany(cascade = CascadeType.REMOVE)
//    private List<Contact> contacts;
//    @OneToMany(cascade = CascadeType.REMOVE)
//    private List<VoiceMemo> voiceMemos;
//    @OneToMany(cascade = CascadeType.REMOVE)
//    private List<Task> tasks;
}
