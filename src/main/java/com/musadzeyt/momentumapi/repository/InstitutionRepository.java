package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, UUID>, JpaSpecificationExecutor<Institution> {
    // TODO: consider replacing this by specification in the future
    @Query("SELECT i FROM Institution i WHERE i.name = :name")
    Optional<Institution> findByName(@Param("name") String name);
}
