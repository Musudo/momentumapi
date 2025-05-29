package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID>, JpaSpecificationExecutor<Review> {
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
              DATE_FORMAT(dates.dt, '%b %d') AS date,
              COUNT(r.id) AS amount
            FROM dates
            LEFT JOIN (
              SELECT r.id, r.created_at
              FROM review r
              INNER JOIN app_user u ON r.user_id = u.id
              WHERE u.email = :email
            ) r ON DATE(r.created_at) = dates.dt
            GROUP BY dates.dt
            ORDER BY dates.dt;
            """, nativeQuery = true)
    List<Map<String, Integer>> findAmountsPerDayForIntervalOfDays(@Param("days") int days, @Param("email") String email);
}
