package com.example.testing_laboratory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@DiscriminatorValue("CP")
public class CleaningProcess extends AbstractProcess{

    private String cleaningEquipment;

    private String procedure;

}
