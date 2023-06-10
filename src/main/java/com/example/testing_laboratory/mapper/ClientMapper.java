package com.example.testing_laboratory.mapper;

import com.example.testing_laboratory.dto.ClientDto;
import com.example.testing_laboratory.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client convertClientDtoToClient(ClientDto clientDto);

}
