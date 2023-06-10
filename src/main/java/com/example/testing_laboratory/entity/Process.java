package com.example.testing_laboratory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "process")
@DiscriminatorColumn(name = "P_TYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Process extends AbstractBaseId {

    private LocalDateTime processStart;

    private LocalDateTime processEnd;

    @ManyToMany
    @CollectionTable(name = "process_client")
    private List<Client> client;

    @ManyToOne
    private User employee;

}
