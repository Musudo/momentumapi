package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IContactRepository extends JpaRepository<Contact, UUID>, JpaSpecificationExecutor<Contact> {
}
