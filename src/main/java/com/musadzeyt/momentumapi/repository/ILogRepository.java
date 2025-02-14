package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ILogRepository extends JpaRepository<Log, UUID> {
}
