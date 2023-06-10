package com.example.testing_laboratory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "control_object")
@DiscriminatorColumn(name = "O_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class ObjectControl extends AbstractBaseId {

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
