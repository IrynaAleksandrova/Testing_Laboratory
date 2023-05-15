package com.example.proving_laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentForWorkProcessDto {
    @NotBlank
    private String proofOfPayment;
}
