package com.b2bAr.arzoo.mapper;

import com.b2bAr.arzoo.entity.CartEntity;
import com.b2bAr.arzoo.request.CartRequest;
import com.b2bAr.arzoo.response.CartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mappings({
            @Mapping(target = "cartCount", source = "cartRequest.cartCount")
    })
    CartEntity requestToEntity(CartRequest cartRequest);

    @Mappings({
            @Mapping(target = "cartId", source = "cartEntity.cartId"),
            @Mapping(target = "productsEntity", source = "cartEntity.productsEntity"),
            @Mapping(target = "cartCount", source = "cartEntity.cartCount")

    } )

    CartResponse entityToResponse(CartEntity cartEntity);

    @Mappings({
            @Mapping(target = "cartCount", source = "cartRequest.cartCount")
    })
    CartEntity updateRequest(CartRequest cartRequest, CartEntity existingDetails);
}
