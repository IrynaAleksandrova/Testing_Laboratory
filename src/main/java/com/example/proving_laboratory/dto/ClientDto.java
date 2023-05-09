package com.example.proving_laboratory.dto;

import com.example.proving_laboratory.entity.Department;
import com.example.proving_laboratory.entity.ObjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {

    private UUID id;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{3,16}")
    private String nameApplicant;

    @NotBlank
    @Pattern(regexp = ".{3,16}")
    private String qrCode;

    @NotBlank
    @Pattern(regexp = ".{3,10}")
    private String address;
//
//    @NotBlank
//    @Pattern(regexp = "[0-9]{3,10}")
//    private String inventoryNumber;
//
//    @NotBlank
//    @Pattern(regexp = ".{3,60}")
//    private String technicalCharacteristic;

    @NotNull
    private Department department;

//    @NotNull
//    private String lastQualificationDate;

    @NotNull
    private String arrivalDate;

    @NotNull
    private ObjectStatus objectStatus;
}
