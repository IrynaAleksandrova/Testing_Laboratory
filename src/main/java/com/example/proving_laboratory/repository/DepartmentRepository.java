package com.example.proving_laboratory.repository;

import com.example.proving_laboratory.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    Optional<Department> findByName(String name);

    boolean existsByName(String name);

}
