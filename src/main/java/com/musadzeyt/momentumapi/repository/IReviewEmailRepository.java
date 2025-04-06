package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.ReviewEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface IReviewEmailRepository extends JpaRepository<ReviewEmail, UUID> {
    @Query(value = "SELECT * FROM email WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL :days DAY)", nativeQuery = true)
    List<ReviewEmail> findAllForIntervalOfDays(@Param("days") int days);

    @Query(value = """
            -- e.g. if you go for 30 days start at 29 days ago (so including today it gives 30 days)
            WITH RECURSIVE dates AS (
              SELECT CURDATE() - INTERVAL :days DAY AS dt
              UNION ALL
              SELECT dt + INTERVAL 1 DAY
              FROM dates
              WHERE dt + INTERVAL 1 DAY <= CURDATE()
            )
            SELECT
              DATE_FORMAT(dates.dt, '%b %e') AS month,
              COUNT(e.id) AS amount
            FROM dates
            LEFT JOIN (
              SELECT e.id, e.created_at
              FROM review_email e
              INNER JOIN review r ON e.review_id = r.id
              INNER JOIN user u ON r.user_id = u.id
              WHERE u.email = :email
            ) e ON DATE(e.created_at) = dates.dt
            GROUP BY dates.dt
            ORDER BY dates.dt;
            """, nativeQuery = true)
    List<Map<String, Integer>> findAmountsPerDayForIntervalOfDays(@Param("days") int days, @Param("email") String email);
}
