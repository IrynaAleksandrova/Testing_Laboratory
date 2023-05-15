package com.example.proving_laboratory.repository;

import com.example.proving_laboratory.entity.AbstractProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProcessRepository extends JpaRepository<AbstractProcess, UUID> {

    AbstractProcess findByClient (UUID clientId);

    @Query(value = "select * from process where process_end is null", nativeQuery = true)
    List<AbstractProcess> findUnfinishedProcesses();

}
