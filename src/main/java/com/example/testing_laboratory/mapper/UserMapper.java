package com.example.testing_laboratory.mapper;

import com.example.testing_laboratory.dto.UserDto;
import com.example.testing_laboratory.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User convertUserDtoToUser(UserDto userDto);

}
