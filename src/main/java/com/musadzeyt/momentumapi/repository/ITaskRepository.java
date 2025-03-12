package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<Task, UUID>, JpaSpecificationExecutor<Task> {
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
              DATE_FORMAT(dates.dt, '%b %d') AS date,
              COUNT(t.id) AS amount
            FROM dates
            LEFT JOIN task t ON DATE(t.created_at) = dates.dt
            LEFT JOIN user u ON t.user_id = u.id
            WHERE u.email = :email OR u.email IS NULL
            GROUP BY dates.dt
            ORDER BY dates.dt;
            """, nativeQuery = true)
    List<Map<String, Integer>> findAmountsPerDayForIntervalOfDays(@Param("days") int days, @Param("email") String email);
}
