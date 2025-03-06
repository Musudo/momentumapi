package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface IAttachmentRepository extends JpaRepository<Attachment, UUID> {
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
              COUNT(a.created_at) AS amount
            FROM dates
            LEFT JOIN attachment a
              ON DATE(a.created_at) = dates.dt
            GROUP BY dates.dt
            ORDER BY dates.dt;
            """, nativeQuery = true)
    List<Map<String, Integer>> findAmountsPerDayForIntervalOfDays(@Param("days") int days);
}
