package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<Task, UUID> {
    @Query(value = "SELECT * FROM task WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL :days DAY)", nativeQuery = true)
    List<Task> findAllForInterval(@Param("days") int days);

    @Query(value = "SELECT COUNT(*) AS amount FROM task WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) GROUP BY DATE_FORMAT(created_at, '%Y-%m-%d') ORDER BY DATE_FORMAT(created_at, '%Y-%m-%d')", nativeQuery = true)
    List<Integer> findAmountPerDayForLastMonth();
}
