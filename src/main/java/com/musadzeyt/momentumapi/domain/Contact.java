package com.musadzeyt.momentumapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @NotNull
    @Size(min = 2, max = 25, message = "First name should be 2 to 25 characters")
    private String firstName;
    @NotNull
    @Size(min = 2, max = 25, message = "Last name should be 2 to 25 characters")
    private String lastName;
    @NotNull
    @Email(message = "Email1 is invalid")
    @Size(min = 10, max = 100, message = "Email1 should be 10 to 100 characters")
    @Column(unique = true, nullable = false)
    private String email1;
    @Email(message = "Email2 is invalid")
    @Size(min = 10, max = 100, message = "Email2 should be 10 to 100 characters")
    @Column(unique = true)
    private String email2;
    @NotNull
    @Size(min = 6, max = 25, message = "Phone1 should be 6 to 25 numbers")
    @Column(unique = true, nullable = false)
    private String phone1;
    @Size(min = 6, max = 25, message = "Phone2 should be 6 to 25 numbers")
    @Column(unique = true)
    private String phone2;
    @NotNull
    @Size(min = 1, max = 50, message = "Job title should be 1 to 50 characters")
    private String jobTitle;
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(updatable = false)
    private AppUser user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(updatable = false)
    private Institution institution;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
