package com.b2bAr.arzoo.mapper;

import com.b2bAr.arzoo.entity.ProductCategories;
import com.b2bAr.arzoo.request.CategoryRequest;
import com.b2bAr.arzoo.request.CategoryUpdateRequest;
import com.b2bAr.arzoo.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings(
            {
                    @Mapping(target = "categoryName", source = "categoryRequest.categoryName"),
                    @Mapping(target = "description", source = "categoryRequest.description"),
            }
    )
    ProductCategories requestToEntity(CategoryRequest categoryRequest);

    @Mappings(
            {
                    @Mapping(target = "categoryId", source = "categories.categoryId"),
                    @Mapping(target = "categoryName", source = "categories.categoryName"),
                    @Mapping(target = "description", source = "categories.description"),
                    @Mapping(target = "subCategories", source = "category.subCategories"),


            }
    )
    List<CategoryResponse> entityToResponse(List<ProductCategories> categories);

    @Mappings(
            {
                    @Mapping(target = "categoryId", source = "productCategories.categoryId"),
                    @Mapping(target = "categoryName", source = "productCategories.categoryName"),
                    @Mapping(target = "description", source = "productCategories.description"),
                   // @Mapping(target = "subCategories", source = "categories.subCategories"),
            }
    )
    CategoryResponse entityToResponse2(ProductCategories productCategories);
    @Mappings(
            {
                    @Mapping(target = "categoryName", source = "categoryRequest.categoryName"),
                    @Mapping(target = "description", source = "categoryRequest.description"),
            }
    )
    ProductCategories updateRequest(ProductCategories existingDetails, CategoryUpdateRequest categoryRequest);


}
