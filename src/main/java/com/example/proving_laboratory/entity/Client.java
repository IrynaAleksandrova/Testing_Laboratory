package com.example.proving_laboratory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@DiscriminatorValue("EQ")
public class Client extends AbstractControl {

    private String nameApplicant;

    private LocalDate completionDate;

}
