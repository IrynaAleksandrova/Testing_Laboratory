package com.example.proving_laboratory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Department extends AbstractEntity{

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{3,16}")
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @CollectionTable(name = "department_employees")
    private List<User> employees;

    @OneToMany
    @CollectionTable(name = "department_client_list")
    private List<Client> clientList;

}
