package com.b2bAr.arzoo.mapper;

import com.b2bAr.arzoo.entity.ProductAttribute;
import com.b2bAr.arzoo.request.ProductAttributeRequest;
import com.b2bAr.arzoo.request.ProductAttributeUpdate;
import com.b2bAr.arzoo.response.ProductAttributeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {


    @Mappings(
            {
                    @Mapping(target = "attributeName", source = "productAttributeRequest.attributeName"),
                    @Mapping(target = "attributeType", source = "productAttributeRequest.attributeType"),
            }
    )
    ProductAttribute requestToEntity(ProductAttributeRequest productAttributeRequest);

    @Mappings(
            {
                    @Mapping(target = "attributeId", source = "attributes.attributeId"),
                    @Mapping(target = "attributeName", source = "attributes.attributeName"),
                    @Mapping(target = "categoryName", source = "attributes.attributeType"),
                    @Mapping(target = "productVariations", source = "attributes.productVariations"),

            }
    )
    List<ProductAttributeResponse> entityToResponse(List<ProductAttribute> attributes);

    @Mappings(
            {
                    @Mapping(target = "attributeId", source = "productAttribute.attributeId"),
                    @Mapping(target = "attributeName", source = "productAttribute.attributeName"),
                    @Mapping(target = "attributeType", source = "productAttribute.attributeType"),
                    @Mapping(target = "productVariations", source = "productAttribute.productVariations"),

            }
    )
    ProductAttributeResponse entityToResponse2(ProductAttribute productAttribute);
    @Mappings(
            {
                    @Mapping(target = "attributeName", source = "attributeRequest.attributeName"),
                    @Mapping(target = "attributeType", source = "attributeRequest.attributeType"),
            }
    )

    ProductAttribute updateRequest(ProductAttribute existingDetails, ProductAttributeUpdate attributeRequest);
}
