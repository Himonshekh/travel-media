package com.example.travelmedia.mapper;

import com.example.travelmedia.dto.RegistrationDto;
import com.example.travelmedia.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "role", constant = "ADMIN")
    User registationToUser(RegistrationDto registrationDto);
}
