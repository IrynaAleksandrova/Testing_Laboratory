package com.example.proving_laboratory.mapper;

import com.example.proving_laboratory.dto.CleaningProcessDto;
import com.example.proving_laboratory.dto.ExtraditionDto;
import com.example.proving_laboratory.entity.CleaningProcess;
import com.example.proving_laboratory.entity.ExtraditionProcess;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(config = MapperConfig.class)
public interface ProcessMapper {

    CleaningProcess convertCleaningProcessDtoToCleaningProcess(CleaningProcessDto cleaningProcessDto);

    ExtraditionProcess convertMaintenanceServiceDtoToMaintenanceService(ExtraditionDto maintenanceServiceDto);

}
