package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IActivityRepository extends JpaRepository<Activity, UUID> {
    @Query(value = "SELECT * FROM activity WHERE start_time >= DATE_SUB(CURDATE(), INTERVAL :days DAY)", nativeQuery = true)
    List<Activity> findAllForInterval(@Param("days") int days);

    @Query(value = "SELECT * FROM activity WHERE type = :type AND (:lastSixMonths = false OR start_time >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH))", nativeQuery = true)
    List<Activity> findByType(@Param("type") String type, @Param("lastSixMonths") boolean lastSixMonths);

    @Query(value = "SELECT COUNT(*) AS amount FROM activity WHERE type = :type AND start_time >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) GROUP BY DATE_FORMAT(start_time, '%Y-%m') ORDER BY DATE_FORMAT(start_time, '%Y-%m')", nativeQuery = true)
    List<Integer> findAmountPerMonthForLastSixMonths(@Param("type") String type);
}
