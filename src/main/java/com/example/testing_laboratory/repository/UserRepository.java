package com.example.testing_laboratory.repository;

import com.example.testing_laboratory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(value = "select * from users where department_id is not null", nativeQuery = true)
    List<User> findAllEmployees();

    @Query(value = "select * from users where department_id =:departmentId", nativeQuery = true)
    List<User> findByDepartment(UUID departmentId);

}
