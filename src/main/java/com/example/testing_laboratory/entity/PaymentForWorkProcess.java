package com.example.testing_laboratory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@DiscriminatorValue("PWP")
public class PaymentForWorkProcess extends AbstractProcess {

    private String proofOfPayment;
}
