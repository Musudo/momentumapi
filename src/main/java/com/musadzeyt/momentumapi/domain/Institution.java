package com.musadzeyt.momentumapi.domain;

import jakarta.persistence.*;
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
public class Institution {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @NotNull
    @Size(min = 2, max = 100, message = "Name should be 2 to 100 characters long")
    @Column(unique = true, nullable = false)
    private String name;
    @NotNull
    @Size(min = 2, max = 100, message = "Street should be 2 to 100 characters long")
    private String street;
    @NotNull
    @Size(min = 1, max = 10, message = "Building number should be 1 to 10 characters long")
    private String buildingNumber;
    @Size(min = 1, max = 10, message = "Postbox should be 1 to 10 characters long")
    private String postbox;
    @NotNull
    @Size(min = 2, max = 100, message = "City should be 2 to 100 characters long")
    private String city;
    @NotNull
    @Size(min = 2, max = 10, message = "Postal code should be 2 to 10 characters long")
    private String postalCode;
    @NotNull
    @Size(min = 2, max = 10, message = "Country code should be 2 to 10 characters long")
    private String countryCode;
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
