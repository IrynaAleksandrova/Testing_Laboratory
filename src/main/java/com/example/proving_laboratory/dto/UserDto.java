package com.example.proving_laboratory.dto;

import com.example.proving_laboratory.entity.Department;
import com.example.proving_laboratory.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{3,16}")
    private String username;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])[0-9a-zA-Z]{3,16}")
    private String password;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{3,16}")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{3,16}")
    private String secondName;

    @NotNull
    private Department department;

    @Size(min = 1)
    private Set<Role> roles;

}
