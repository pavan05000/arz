package com.b2bAr.arzoo.mapper;

import com.b2bAr.arzoo.entity.OrdersEntity;
import com.b2bAr.arzoo.request.OrderRequest;
import com.b2bAr.arzoo.response.OrderResponse;
import com.b2bAr.arzoo.response.OrderResponse2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "quantity", source = "request.quantity")
    })
    OrdersEntity requestToEntity(OrderRequest request);

    @Mappings(
            {
                    @Mapping(target = "orderId", source = "entityToResponse.orderId"),
                    @Mapping(target = "userEntity", source = "entityToResponse.userEntity"),
                    @Mapping(target = "productEntity", source = "entityToResponse.productEntity"),
                    @Mapping(target = "orderDate", source = "entityToResponse.orderDate"),
                    @Mapping(target = "status", source = "entityToResponse.status")
            }
    )
    OrderResponse entityToResponse(OrdersEntity entityToResponse);
    OrderResponse2 entityToResponse3(OrdersEntity entityToResponse);

    @Mappings(
            {
                    @Mapping(target = "orderId", source = "entityList.orderId"),
                    @Mapping(target = "userEntity", source = "entityList.userEntity"),
                    @Mapping(target = "productEntity", source = "entityList.productEntity"),
                    @Mapping(target = "orderDate", source = "entityList.orderDate"),
                    @Mapping(target = "status", source = "entityList.status")
            }
    )
    List<OrderResponse> entityToResponse2(List<OrdersEntity> entityList);

    @Mappings({
            @Mapping(target = "quantity", source = "orderRequest.quantity"),
    })
    OrdersEntity updateRequest(OrderRequest orderRequest, @MappingTarget OrdersEntity existingDetails);

}
