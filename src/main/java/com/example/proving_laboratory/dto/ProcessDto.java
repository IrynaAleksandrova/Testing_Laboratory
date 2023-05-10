package com.example.proving_laboratory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDto {

    @Size(min = 1)
    private List<String> clientQrCodes;

    @NotNull
    private String processType;

}
