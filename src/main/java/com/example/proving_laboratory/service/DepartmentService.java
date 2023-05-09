package com.example.proving_laboratory.service;

import com.example.proving_laboratory.entity.Client;
import com.example.proving_laboratory.entity.Department;
import com.example.proving_laboratory.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepartmentService {

    Optional<Department> saveDepartment(Department department);

    List<Department> findAllDepartments();

    Optional<Department> findDepartmentById(UUID id);

    void updateDepartmentWithEmployee(UUID departmentId, User employee);

    void updateDepartmentWithClient(UUID departmentId, Client client);

    void updateDepartment(Department department);

    List<Department> findDepartmentList(User user);

}
