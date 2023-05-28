package com.example.testing_laboratory.dto;

import com.example.testing_laboratory.entity.Department;
import com.example.testing_laboratory.entity.ObjectStatus;
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
    private String nameClient;

    @NotBlank
    @Pattern(regexp = ".{3,16}")
    private String qrCode;

    @NotBlank
    @Pattern(regexp = ".{3,10}")
    private String internalCode;

    @NotBlank
    @Pattern(regexp = "[0-9]{3,10}")
    private String inventoryNumber;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{3,16}")
    private String production;

    @NotNull
    private Department department;

    @NotNull
    private String lastPaymentDate;

    @NotNull
    private String lastDeliveryDate;

    @NotNull
    private ObjectStatus objectStatus;
}
