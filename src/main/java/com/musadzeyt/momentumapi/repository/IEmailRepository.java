package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IEmailRepository extends JpaRepository<Email, UUID> {
    @Query(value = "SELECT * FROM email WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL :days DAY)", nativeQuery = true)
    List<Email> findAllForInterval(@Param("days") int days);

    @Query(value = """
            WITH RECURSIVE dates AS (
                SELECT DATE_SUB(CURDATE(), INTERVAL 29 DAY) AS dt
                UNION ALL
                SELECT DATE_ADD(dt, INTERVAL 1 DAY)
                FROM dates
                WHERE dt < CURDATE()
            )
            SELECT COALESCE(e.amount, 0)
            FROM dates
            LEFT JOIN (
                SELECT DATE(created_at) AS dt, COUNT(*) AS amount
                FROM email
                WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
                GROUP BY DATE(created_at)
            ) e ON dates.dt = e.dt
            ORDER BY dates.dt
            """, nativeQuery = true)
    List<Integer> findAmountPerDayForLastMonth();
}
