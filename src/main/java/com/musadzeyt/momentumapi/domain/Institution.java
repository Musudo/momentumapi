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
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Institution {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.VARCHAR)
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
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(updatable = false)
//    private User user;
//    @ManyToMany(mappedBy = "institutions", fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "institution_contacts",
//            joinColumns = @JoinColumn(name = "institution_id"),
//            inverseJoinColumns = @JoinColumn(name = "contact_id")
//    )
//    private Set<Contact> contacts = new HashSet<>();
//    @OneToMany()
//    @JoinColumn(updatable = false)
//    private List<Activity> activities;

    // Helper method to maintain both sides of the relationship
//    public void addContact(Contact contact) {
//        this.contacts.add(contact);
//        contact.getInstitutions().add(this);
//    }
}
