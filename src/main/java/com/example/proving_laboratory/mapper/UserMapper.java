package com.example.proving_laboratory.mapper;

import com.example.proving_laboratory.dto.UserDto;
import com.example.proving_laboratory.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    User convertUserDtoToUser(UserDto userDto);

}
