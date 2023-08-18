package com.user.service.api.mappers;

import com.common.security.api.mapper.BaseMapper;
import com.user.service.api.dtos.UserDTO;
import com.user.service.api.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends BaseMapper<UserDTO, UserEntity> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
