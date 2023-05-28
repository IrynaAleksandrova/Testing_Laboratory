package com.example.testing_laboratory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
public class Department extends AbstractBaseId {

    @NotBlank
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @CollectionTable(name = "department_employees")
    private List<User> employees;

    @OneToMany
    @CollectionTable(name = "department_client_list")
    private List<Client> clientList;

    public Department(String name) {
        this.name = name;
    }
}
