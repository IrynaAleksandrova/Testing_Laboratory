package com.example.testing_laboratory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("CQ")
public class Client extends ObjectControl {

    private String inventoryNumber;
    private LocalDate lastDeliveryDate;

}
