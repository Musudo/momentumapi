package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID>, JpaSpecificationExecutor<Activity> {
    // TODO: replace this with specification and criteria in the future (in other repo's also)
    @Query(value = """
            SELECT a.*
            FROM activity a
            LEFT JOIN app_user u ON a.user_id = u.id
            WHERE a.start_time >= DATE_SUB(CURDATE(), INTERVAL :days DAY)
                AND u.email = :email
            """, nativeQuery = true)
    List<Activity> findAllForIntervalOfDays(@Param("days") int days, @Param("email") String email);

    @Query(value = """
            SELECT a.*
            FROM activity a
            LEFT JOIN app_user u ON a.user_id = u.id
            WHERE a.type = :type
                AND u.email = :email
                AND (:interval = false
                        OR a.start_time >= DATE_SUB(CURDATE(), INTERVAL :days DAY))
            """, nativeQuery = true)
    List<Activity> findByTypeForIntervalOfDays(@Param("type") String type,
                                               @Param("interval") boolean interval,
                                               @Param("days") int days,
                                               @Param("email") String email);

    /**
     * Returns a list of daily activity counts for the specified interval of days.
     * <p>
     * This native query uses a recursive common table expression (CTE) named "dates" to generate a series of dates,
     * starting from <code>CURDATE() - INTERVAL :days DAY</code> up to <code>CURDATE()</code>. It then left joins the
     * <code>activity</code> table on matching dates (by comparing the date portion of <code>created_at</code> to the generated date)
     * and groups the results by each day. The query returns a count of activity records per day.
     * </p>
     *
     * @param days the number of days in the interval (including today).
     * @return a {@code List<Integer>} where each element is the count of activities for each day in the interval.
     */
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
              COUNT(a.id) AS amount
            FROM dates
            LEFT JOIN activity a ON DATE(a.created_at) = dates.dt
            LEFT JOIN app_user u ON a.user_id = u.id
            WHERE u.email = :email OR u.email IS NULL
            GROUP BY dates.dt
            ORDER BY dates.dt;
            """, nativeQuery = true)
    List<Map<String, Integer>> findAmountsPerDayForIntervalOfDays(@Param("days") int days, @Param("email") String email);

    /**
     * Returns a list of monthly activity counts filtered by type for the specified interval of months.
     * <p>
     * This native query uses a recursive CTE named "months" to generate a sequence of month start dates,
     * starting from the first day of the month for <code>DATE_SUB(CURDATE(), INTERVAL :months MONTH)</code>
     * up to the current month. It then left joins a subquery that groups activities by month (formatted as <code>%Y-%m</code>)
     * and counts the number of activities for the specified type. The final result contains the abbreviated month name
     * (using <code>%b</code>) and the corresponding activity count (defaulting to 0 if no activity exists).
     * </p>
     *
     * @param type   the type of activity to filter by.
     * @param months the number of months in the interval (including the current month).
     * @return a {@code List<Map<String, Integer>>} where each map contains:
     * <ul>
     *     <li><code>"month"</code> - the abbreviated month name (e.g., "Jan", "Feb", etc.)</li>
     *     <li><code>"amount"</code> - the count of activities for that month</li>
     * </ul>
     */
    @Query(value = """
            WITH RECURSIVE months AS (
                -- e.g. if you go for 7 months start at 6 months ago (so including current one it gives 7 months)
                SELECT DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL :months MONTH), '%Y-%m-01') AS month_date
                UNION ALL
                SELECT DATE_ADD(month_date, INTERVAL 1 MONTH)
                FROM months
                WHERE month_date < DATE_FORMAT(CURDATE(), '%Y-%m-01')
            )
            SELECT
                DATE_FORMAT(m.month_date, '%b') AS month,
                COALESCE(a.activity_count, 0) AS amount
            FROM months m
            LEFT JOIN (
                SELECT
                    DATE_FORMAT(a.created_at, '%Y-%m') AS month,
                    COUNT(a.id) AS activity_count
                FROM activity a
                LEFT JOIN app_user u ON a.user_id = u.id
                WHERE a.type = :type
                    AND a.created_at >= DATE_SUB(CURDATE(), INTERVAL :months MONTH)
                    AND u.email = :email OR u.email IS NULL
                GROUP BY DATE_FORMAT(a.created_at, '%Y-%m')
            ) a
                ON DATE_FORMAT(m.month_date, '%Y-%m') = a.month
            ORDER BY m.month_date;
            """, nativeQuery = true)
    List<Map<String, Integer>> findAmountsByTypePerMonthForIntervalOfMonths(@Param("type") String type,
                                                                            @Param("months") int months,
                                                                            @Param("email") String email);

    @Query(value = """
            WITH RECURSIVE dates AS (
                SELECT CURDATE() - INTERVAL :days DAY AS dt
                UNION ALL
                SELECT dt + INTERVAL 1 DAY
                FROM dates
                WHERE dt + INTERVAL 1 DAY <= CURDATE()
            )
            SELECT
                DATE_FORMAT(dates.dt, '%Y-%m-%d') AS date,
                COALESCE(COUNT(a.id), 0) AS amount
            FROM dates
            LEFT JOIN activity a ON DATE(a.start_time) = dates.dt
            LEFT JOIN app_user u ON a.user_id = u.id
            WHERE (u.email = :email OR u.email IS NULL)
              AND (a.type = :type OR a.type IS NULL)
            GROUP BY dates.dt
            ORDER BY dates.dt;
            """, nativeQuery = true)
    List<Integer> findAmountsByTypePerDayForIntervalOfDays(@Param("type") String type,
                                                           @Param("days") int days,
                                                           @Param("email") String email);
}
