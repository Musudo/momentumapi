package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IEmailRepository extends JpaRepository<Email, UUID> {
    @Query(value = "SELECT * FROM email WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)", nativeQuery = true)
    List<Email> findAllForLast30Days();
}
