package com.b2bAr.arzoo.mapper;


import com.b2bAr.arzoo.entity.SubCategoryEntity;
import com.b2bAr.arzoo.request.SubCategoryUpdateRequest;
import com.b2bAr.arzoo.response.SubCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {

    @Mappings(
            {
                    @Mapping(target = "subCategoryName", source = "subCategoryRequest.subCategoryName"),

            }
    )
    SubCategoryEntity requestToEntity(SubCategoryUpdateRequest subCategoryRequest);


    @Mappings(
            {
                    @Mapping(target = "subCategoryId", source = "categories.subCategoryId"),
                    @Mapping(target = "subCategoryName", source = "categories.subCategoryName"),
                    @Mapping(target = "productCategories", source = "categories.productCategories"),
                    @Mapping(target = "productsEntity", source = "categories.productsEntity"),
            }
    )
    List<SubCategoryResponse> entityToResponse(List<SubCategoryEntity> categories);

    @Mappings(
            {
                    @Mapping(target = "subCategoryId", source = "subCategories.subCategoryId"),
                    @Mapping(target = "subCategoryName", source = "subCategories.subCategoryName"),
                    @Mapping(target = "productCategories", source = "subCategories.productCategories"),
                    @Mapping(target = "productsEntity", source = "subCategories.productsEntity"),
            }
    )
     SubCategoryResponse entityToResponse2(SubCategoryEntity subCategories);

    @Mappings(
            {
                    @Mapping(target = "subCategoryName", source = "categoryRequest.subCategoryName"),


            }
    )
    SubCategoryEntity updateRequest(SubCategoryEntity existingDetails, SubCategoryUpdateRequest categoryRequest);


}
