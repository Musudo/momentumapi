package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface IEmailRepository extends JpaRepository<Email, UUID> {
    @Query(value = "SELECT * FROM email WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL :days DAY)", nativeQuery = true)
    List<Email> findAllForIntervalOfDays(@Param("days") int days);

    @Query(value = """
            WITH RECURSIVE dates AS (
              -- e.g. if you go for 30 days start at 29 days ago (so including today it gives 30 days)
              SELECT CURDATE() - INTERVAL :days DAY AS dt
              UNION ALL
              SELECT dt + INTERVAL 1 DAY
              FROM dates
              WHERE dt + INTERVAL 1 DAY <= CURDATE()
            )
            SELECT
              DATE_FORMAT(dates.dt, '%b %e') AS month,
              COUNT(e.created_at) AS amount
            FROM dates
            LEFT JOIN email e
              ON DATE(e.created_at) = dates.dt
            GROUP BY dates.dt
            ORDER BY dates.dt;
            """, nativeQuery = true)
    List<Map<String, Integer>> findAmountsPerDayForIntervalOfDays(@Param("days") int days);
}
