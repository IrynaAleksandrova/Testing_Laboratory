package com.example.proving_laboratory.service.impl;

import com.example.proving_laboratory.dto.UserDto;
import com.example.proving_laboratory.entity.Department;
import com.example.proving_laboratory.entity.Role;
import com.example.proving_laboratory.entity.User;
import com.example.proving_laboratory.exception.SaveException;
import com.example.proving_laboratory.mapper.UserMapper;
import com.example.proving_laboratory.repository.UserRepository;
import com.example.proving_laboratory.service.DepartmentService;
import com.example.proving_laboratory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    private final UserRepository userRepository;
    private final DepartmentService departmentService;
    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public void saveAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User("admin", encoder.encode("admin"), "Iryna",
                    "Aleksandrova", Set.of(Role.ADMIN));
            userRepository.save(admin);
        }
    }

    @Override
    public void saveAuditor() {
        if (!userRepository.existsByUsername("auditor")) {
            User auditor = new User("auditor", encoder.encode("auditor"), Set.of(Role.AUDITOR));
            userRepository.save(auditor);
        }
    }

    @Override
    public Optional<User> saveUser(UserDto userDto) {
        User user = userMapper.convertUserDtoToUser(userDto);
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(encoder.encode(user.getPassword()));
            User saveUser = userRepository.save(user);
            log.info(saveUser.getFirstName() + " " + saveUser.getSecondName() + " was saved");
            departmentService.updateDepartmentWithEmployee(saveUser.getDepartment().getId(), saveUser);
            return Optional.of(saveUser);
        } else {
            throw new SaveException();
        }
    }


    @Override
    public Optional<User> findUserByUsername(String username) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return byUsername;
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    @Override
    public List<User> findAllEmployees() {
        return userRepository.findAllEmployees();
    }

    @Override
    public List<User> findEmployeesOfDepartment(UUID departmentId) {
        return userRepository.findByDepartment(departmentId);
    }

    @Override
    public void deleteEmployee(UUID id) {
        Optional<User> userById = userRepository.findById(id);
        userRepository.deleteById(id);
        Department department = userById.get().getDepartment();
        department.getEmployees().remove(userById.get());
        departmentService.updateDepartment(department);
    }

    @Override
    public List<User> findEmployeeList(String currentUserUsername) {
        User user = findUserByUsername(currentUserUsername).get();
        List<User> employeeList;
        if (user.getAuthorities().contains(Role.ADMIN)) {
            employeeList = findAllEmployees();
        } else {
            employeeList = findEmployeesOfDepartment(user.getDepartment().getId());
        }
        return employeeList;

    }
}
