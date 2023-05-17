package com.example.proving_laboratory.entity;

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
@DiscriminatorValue("PP")
public class ProductionProcess extends AbstractProcess{

    private String productName;

    private String seriesNumber;

}
