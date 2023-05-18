package com.example.testing_laboratory.dto;

import com.example.testing_laboratory.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractProcessDto {
    private LocalDateTime processStart;
    private LocalDateTime processEnd;
    private User employee;
    private String cleaningEquipment;
    private String procedure;
    private String productName;
    private String seriesNumber;
    private String deliveryReport;
    private String paymentProcess;

    public AbstractProcessDto(LocalDateTime processStart, LocalDateTime processEnd, User employee) {
        this.processStart = processStart;
        this.processEnd = processEnd;
        this.employee = employee;
    }

}
