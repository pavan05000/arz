package com.b2bAr.arzoo.mapper;

import com.b2bAr.arzoo.entity.ProductVariations;
import com.b2bAr.arzoo.request.ProductVariationRequest;
import com.b2bAr.arzoo.request.ProductVariationUpdate;
import com.b2bAr.arzoo.response.ProductVariationResponse2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductVariationMapper {

    @Mappings(
            {
                    @Mapping(target = "value", source = "productVariationRequest.value"),
            }
    )

    ProductVariations requestToEntity(ProductVariationRequest productVariationRequest);

    @Mappings(
            {
                    @Mapping(target = "value", source = "variations.value"),
                    @Mapping(target = "variationId", source = "variations.variationId"),
                    @Mapping(target = "productsEntity", source = "variations.productsEntity"),
                    @Mapping(target = "productAttribute", source = "variations.productAttribute"),
            }
    )
    List<ProductVariationResponse2> entityToResponse(List<ProductVariations> variations);

    @Mappings({
            @Mapping(target = "value", source = "productVariations.value"),
            @Mapping(target = "variationId", source = "productVariations.variationId"),
            @Mapping(target = "productsEntity", source = "productVariations.productsEntity"),
//                    @Mapping(target = "productAttribute", source = "productVariations.productAttribute"),
    })
    ProductVariationResponse2 entityToResponse2(ProductVariations productVariations);

    @Mappings(
            {
                    @Mapping(target = "value", source = "productVariationUpdate.value"),
                    // @Mapping(target = "attributeType", source = "productAttributeRequest.attributeType"),
            }
    )
    ProductVariations updateRequest(ProductVariations existingDetails, ProductVariationUpdate productVariationUpdate);
}
