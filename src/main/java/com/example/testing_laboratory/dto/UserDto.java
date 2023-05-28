package com.example.testing_laboratory.dto;

import com.example.testing_laboratory.entity.Department;
import com.example.testing_laboratory.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String secondName;

    @NotNull
    private Department department;

    @Size(min = 1)
    private Set<Role> roles;

}
