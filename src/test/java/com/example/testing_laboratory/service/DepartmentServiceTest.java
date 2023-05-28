package com.example.testing_laboratory.service;

import com.example.testing_laboratory.entity.Department;
import com.example.testing_laboratory.entity.Role;
import com.example.testing_laboratory.entity.User;
import com.example.testing_laboratory.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DepartmentServiceTest {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserRepository userRepository;

    @Test
    void updateDepartmentWithEmployee() {
        Department department = new Department("Department");
        Optional<Department> department1 = departmentService.saveDepartment(department);
        User user = new User("username", "password", "firstName", "secondName", department1.get(), Set.of(Role.HEAD_OF_DEPARTMENT));
        User saveUser = userRepository.save(user);
        departmentService.updateDepartmentWithEmployee(department1.get().getId(), saveUser);
        Optional<Department> departmentById = departmentService.findDepartmentById(department1.get().getId());
        List<User> employees = departmentById.get().getEmployees();
        boolean result = true;
        for (User employee:employees) {
            if(employee.getId() == saveUser.getId()){
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
}
