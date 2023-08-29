package org.brain.user_service.mapper;

import org.brain.user_service.model.User;

import org.brain.user_service.payload.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapToUser(UserRequest request);

}
