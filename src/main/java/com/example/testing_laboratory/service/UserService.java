package com.example.testing_laboratory.service;

import com.example.testing_laboratory.dto.UserDto;
import com.example.testing_laboratory.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    void saveAdmin();

    void saveAuditor();

    Optional<User> saveUser(UserDto userDto);

    Optional<User> findUserByUsername(String username);

    List<User> findAllEmployees();

    List<User> findEmployeesOfDepartment(UUID departmentId);

    void deleteEmployee(UUID id);

    List<User> findEmployeeList(String currentUserUsername);

}
