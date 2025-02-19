package com.musadzeyt.momentumapi.repository;

import com.musadzeyt.momentumapi.domain.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IErrorLogRepository extends JpaRepository<ErrorLog, UUID> {
}
