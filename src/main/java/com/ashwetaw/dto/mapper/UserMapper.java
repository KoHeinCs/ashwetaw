package com.ashwetaw.dto.mapper;

import com.ashwetaw.dto.UserDTO;
import com.ashwetaw.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDTO, User> {
    @Override
    @Mapping(target = "password", ignore = true)
    UserDTO toDTO(User entity);
}
