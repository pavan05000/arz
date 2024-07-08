package com.b2bAr.arzoo.mapper;

import com.b2bAr.arzoo.entity.UserEntity;
import com.b2bAr.arzoo.request.UserRequest;
import com.b2bAr.arzoo.request.UserUpdateRequest;
import com.b2bAr.arzoo.response.UserResponse;
import com.b2bAr.arzoo.response.UserResponse3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "userName", source = "userRequest.userName"),
            @Mapping(target = "email", source = "userRequest.email"),
            @Mapping(target = "password", source = "userRequest.password"),
            @Mapping(target = "role", source = "userRequest.role"),
            @Mapping(target = "address", source = "userRequest.address"),
            @Mapping(target = "phoneNo", source = "userRequest.phoneNo"),
            @Mapping(target = "activeStatus", source = "userRequest.activeStatus")
    })
    UserEntity requestToEntity(UserRequest userRequest);

    @Mappings({
            @Mapping(target = "userId", source = "userEntity.userId"),
            @Mapping(target = "userName", source = "userEntity.userName"),
            @Mapping(target = "email", source = "userEntity.email"),
            @Mapping(target = "password", source = "userEntity.password"),
            @Mapping(target = "role", source = "userEntity.role"),
            @Mapping(target = "registerDate", source = "userEntity.registerDate"),
            @Mapping(target = "loginDate", source = "userEntity.loginDate"),
            @Mapping(target = "address", source = "userEntity.address"),
            @Mapping(target = "phoneNo", source = "userEntity.phoneNo"),
            @Mapping(target = "activeStatus", source = "userEntity.activeStatus"),
            @Mapping(target = "ordersEntity", source = "userEntity.ordersEntity")

    })
    UserResponse entityToResponse(UserEntity userEntity);


    List<UserResponse> entityToResponse2(List<UserEntity> entity);


    @Mappings({
            @Mapping(target = "userId", source = "userEntity.userId"),
            @Mapping(target = "userName", source = "userEntity.userName"),
            @Mapping(target = "email", source = "userEntity.email"),
            @Mapping(target = "password", source = "userEntity.password"),
            @Mapping(target = "role", source = "userEntity.role"),
            @Mapping(target = "registerDate", source = "userEntity.registerDate"),
            @Mapping(target = "address", source = "userEntity.address"),
            @Mapping(target = "phoneNo", source = "userEntity.phoneNo"),
            @Mapping(target = "cartEntity", source = "userEntity.cartEntity")

    })
    UserResponse3 entityToResponse3(UserEntity userEntity);
    @Mappings({
            @Mapping(target = "userName", source = "userUpdateRequest.userName"),
            @Mapping(target = "email", source = "userUpdateRequest.email"),
            @Mapping(target = "password", source = "userUpdateRequest.password"),
            @Mapping(target = "role", source = "userUpdateRequest.role"),
            @Mapping(target = "address", source = "userUpdateRequest.address"),
            @Mapping(target = "phoneNo", source = "userUpdateRequest.phoneNo"),


    })

    UserEntity updateRequest(UserEntity existingDetails, UserUpdateRequest userUpdateRequest);
}
