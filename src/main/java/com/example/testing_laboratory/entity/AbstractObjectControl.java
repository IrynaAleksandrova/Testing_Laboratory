package com.example.testing_laboratory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "O_TYPE")
@Table(name = "control_object")
public abstract class AbstractObjectControl extends AbstractEntity{

    private String nameClient;

    private String qrCode;

    private String internalCode;

    private String production;

    @ManyToOne
    private Department department;

    private LocalDate lastPaymentDate;

    @Enumerated(EnumType.STRING)
    private ObjectStatus objectStatus;

    private boolean isProcess;
}
