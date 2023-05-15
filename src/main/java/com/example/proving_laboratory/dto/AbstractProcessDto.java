package com.example.proving_laboratory.dto;

import com.example.proving_laboratory.entity.User;
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
    private String extraditionInstruction;
    private String productName;

    public AbstractProcessDto(LocalDateTime processStart, LocalDateTime processEnd, User employee) {
        this.processStart = processStart;
        this.processEnd = processEnd;
        this.employee = employee;
    }

}
