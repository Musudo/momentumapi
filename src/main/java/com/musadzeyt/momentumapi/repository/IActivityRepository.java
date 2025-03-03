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
    @Query(value = "SELECT * FROM activity WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)", nativeQuery = true)
    List<Activity> findAllForLast30Days();

    @Query(value = "SELECT * FROM activity WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)", nativeQuery = true)
    List<Activity> findAllForLast6Months();

    @Query("SELECT a FROM Activity a WHERE a.type = :type")
    List<Activity> findAllByType(@Param("type") String type);
}
