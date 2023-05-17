package com.example.proving_laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionProcessDto {

    @NotBlank
    private String productName;

    @NotBlank
    private String seriesNumber;

}
