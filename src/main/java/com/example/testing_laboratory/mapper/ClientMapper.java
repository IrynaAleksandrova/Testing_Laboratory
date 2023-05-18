package com.example.testing_laboratory.mapper;

import com.example.testing_laboratory.dto.ClientDto;
import com.example.testing_laboratory.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(config = MapperConfig.class)
public interface ClientMapper {

    Client convertClientDtoToClient(ClientDto ClientDto);

}
