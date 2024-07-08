package com.b2bAr.arzoo.mapper;

import com.b2bAr.arzoo.entity.DealsEntity;
import com.b2bAr.arzoo.request.DealsRequest;
import com.b2bAr.arzoo.response.DealsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DealsMapper {


    @Mappings({
            @Mapping(target = "dealName", source = "dealsRequest.dealName"),
            @Mapping(target = "description", source = "dealsRequest.description"),
            @Mapping(target = "startDate", source = "dealsRequest.startDate"),
            @Mapping(target = "endDate", source = "dealsRequest.endDate"),
            @Mapping(target = "discountPercentage", source = "dealsRequest.discountPercentage"),
    })

     DealsEntity requestToEntity(DealsRequest dealsRequest);

    @Mappings({
            @Mapping(target = "dealId", source = "entities.dealId"),
            @Mapping(target = "description", source = "entities.description"),
            @Mapping(target = "dealName", source = "entities.dealName"),
            @Mapping(target = "startDate", source = "entities.startDate"),
            @Mapping(target = "endDate", source = "entities.endDate"),
            @Mapping(target = "discountPercentage", source = "entities.discountPercentage"),
    })

    List<DealsResponse> entityToResponse(List<DealsEntity> entities);

    @Mappings({
            @Mapping(target = "dealId", source = "dealsEntity.dealId"),
            @Mapping(target = "description", source = "dealsEntity.description"),
            @Mapping(target = "dealName", source = "dealsEntity.dealName"),
            @Mapping(target = "startDate", source = "dealsEntity.startDate"),
            @Mapping(target = "endDate", source = "dealsEntity.endDate"),
            @Mapping(target = "discountPercentage", source = "dealsEntity.discountPercentage"),
    })
    DealsResponse entityToResponse2(DealsEntity dealsEntity);

    @Mappings({
            @Mapping(target = "dealName", source = "dealsRequest.dealName"),
            @Mapping(target = "description", source = "dealsRequest.description"),
            @Mapping(target = "startDate", source = "dealsRequest.startDate"),
            @Mapping(target = "endDate", source = "dealsRequest.endDate"),
            @Mapping(target = "discountPercentage", source = "dealsRequest.discountPercentage"),
    })
    DealsEntity updateRequest(DealsEntity existingDetails, DealsRequest dealsRequest);
}
