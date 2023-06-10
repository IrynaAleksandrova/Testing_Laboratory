package com.example.testing_laboratory.service;

import com.example.testing_laboratory.dto.UserDto;
import com.example.testing_laboratory.entity.Department;
import com.example.testing_laboratory.entity.Role;
import com.example.testing_laboratory.entity.User;
import com.example.testing_laboratory.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmentService departmentService;

    @Test
    void saveUser() {
        Department department = new Department("Department");
        departmentService.saveDepartment(department);
        UserDto userDto = new UserDto("username", "password", "firstName", "secondName", department, Set.of(Role.HEAD_OF_DEPARTMENT));
        Optional<User> user = userService.saveUser(userDto);
        assertTrue(userRepository.existsByUsername(user.get().getUsername()));
    }

    @Test
    void deleteEmployee() {
        Department department = new Department("Department");
        departmentService.saveDepartment(department);
        UserDto userDto1 = new UserDto("username1", "password1", "firstName1", "secondName1", department, Set.of(Role.HEAD_OF_DEPARTMENT));
        Optional<User> user1 = userService.saveUser(userDto1);
        UserDto userDto2 = new UserDto("username2", "password2", "firstName2", "secondName2", department, Set.of(Role.DEPARTMENT_WORKER));
        Optional<User> user2 = userService.saveUser(userDto2);
        userService.deleteEmployee(user1.get().getId());
        assertFalse(userRepository.existsByUsername(user1.get().getUsername()));
    }
}
