package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IErrorLogRepository extends JpaRepository<ErrorLog, UUID> {
    @Query(value = """
            SELECT el.*
            FROM error_log el
            LEFT JOIN app_user u ON el.user_id = u.id
            WHERE u.email = :email
            """, nativeQuery = true)
    List<ErrorLog> findAllByUser(@Param("email") String email);
}
