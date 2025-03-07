package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IContactRepository extends JpaRepository<Contact, UUID> {
    @Query(value = "SELECT * FROM contact c JOIN institution i ON c.institution_id = i.id", nativeQuery = true)
    List<Contact> findAllWithInstitution();
}
