package com.example.testing_laboratory.repository;

import com.example.testing_laboratory.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProcessRepository extends JpaRepository<Process, UUID> {

    Process findByClient (UUID clientId);

    @Query(value = "select * from process where process_end is null", nativeQuery = true)
    List<Process> findUnfinishedProcesses();

}
