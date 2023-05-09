package com.example.proving_laboratory.mapper;

import com.example.proving_laboratory.dto.ClientDto;
import com.example.proving_laboratory.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(config = MapperConfig.class)
public interface ClientMapper {

    Client convertClientDtoToClient(ClientDto ClientDto);

}
