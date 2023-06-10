package com.example.testing_laboratory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("CP")
public class CleaningProcess extends Process {

    private String cleaningEquipment;
    private String procedure;

}
