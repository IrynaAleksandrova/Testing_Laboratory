package com.example.testing_laboratory.service.impl;

import com.example.testing_laboratory.entity.Client;
import com.example.testing_laboratory.entity.Department;
import com.example.testing_laboratory.entity.Role;
import com.example.testing_laboratory.entity.User;
import com.example.testing_laboratory.exception.SaveException;
import com.example.testing_laboratory.repository.DepartmentRepository;
import com.example.testing_laboratory.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Optional<Department> saveDepartment(Department department) {
        if (!departmentRepository.existsByName(department.getName())) {
            log.info("add department " + department.getName());
            return Optional.of(departmentRepository.save(department));
        } else {
            throw new SaveException();
        }
    }

    @Override
    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> findDepartmentById(UUID id) {
        return departmentRepository.findById(id);
    }

    @Override
    public void updateDepartmentWithEmployee(UUID departmentId, User employee) {
        Optional<Department> departmentById = departmentRepository.findById(departmentId);
        Department department = departmentById.get();
        department.getEmployees().add(employee);
        log.info("update department " + department.getName());
        departmentRepository.save(department);
    }

    @Override
    public void updateDepartmentWithClient(UUID departmentId, Client client) {
        Optional<Department> departmentById = departmentRepository.findById(departmentId);
        Department department = departmentById.get();
        department.getClientList().add(client);
        log.info("update department " + department.getName());
        departmentRepository.save(department);
    }

    @Override
    public void updateDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public List<Department> findDepartmentList(User user) {
        List<Department> departments = new ArrayList<>();
        if (!user.getAuthorities().contains(Role.ADMIN)) {
            departments.add(user.getDepartment());
        } else {
            departments = findAllDepartments();
        }
        return departments;
    }

}
