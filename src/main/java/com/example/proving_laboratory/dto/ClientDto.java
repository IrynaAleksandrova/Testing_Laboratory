package com.example.proving_laboratory.dto;

import com.example.proving_laboratory.entity.Department;
import com.example.proving_laboratory.entity.ObjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{3,16}")
    private String nameClient;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{3,16}")
    private String production;

    @NotBlank
    @Pattern(regexp = ".{3,10}")
    private String internalCode;

    @NotBlank
    @Pattern(regexp = "[0-9]{3,10}")
    private String inventoryNumber;

    @NotBlank
    @Pattern(regexp = ".{3,16}")
    private String qrCode;

    @NotNull
    private Department department;

    @NotNull
    private String deliveryDate;

    @NotNull
    private String completionDate;

    @NotNull
    private ObjectStatus objectStatus;
}
