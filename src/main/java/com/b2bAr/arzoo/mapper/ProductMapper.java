package com.b2bAr.arzoo.mapper;

import com.b2bAr.arzoo.entity.ProductCategories;
import com.b2bAr.arzoo.entity.ProductsEntity;
import com.b2bAr.arzoo.request.ProductRequest;
import com.b2bAr.arzoo.request.ProductUpdateRequest;
import com.b2bAr.arzoo.response.ProductResponse;
import com.b2bAr.arzoo.response.ProductResponse2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mappings(
            {
                    @Mapping(target = "productName", source = "productRequest.productName"),
                    @Mapping(target = "description", source = "productRequest.description"),
                    @Mapping(target = "price", source = "productRequest.price"),
                    @Mapping(target = "image_url", source = "productRequest.image_url"),
                    @Mapping(target = "availability_status", source = "productRequest.availability_status"),
                    @Mapping(target = "stock", source = "productRequest.stock"),

            }
    )
    ProductsEntity requestToEntity(ProductRequest productRequest);

    @Mappings(
            {
                    @Mapping(target = "productId", source = "entities.productId"),
                    @Mapping(target = "productName", source = "entities.productName"),
                    @Mapping(target = "description", source = "entities.description"),
                    @Mapping(target = "price", source = "entities.price"),
                    @Mapping(target = "availability_status", source = "entities.availability_status"),
                    @Mapping(target = "stock", source = "entities.stock"),
                    @Mapping(target = "productCategories", source = "entities.productCategories"),
                    //@Mapping(target = "productVariations", source = "entities.productVariations"),

            }
    )
    List<ProductResponse> entityToResponse(List<ProductsEntity> entities);

    @Mappings(
            {
                    @Mapping(target = "productId", source = "productsEntity.productId"),
                    @Mapping(target = "productName", source = "productsEntity.productName"),
                    @Mapping(target = "description", source = "productsEntity.description"),
                    @Mapping(target = "price", source = "productsEntity.price"),
                    @Mapping(target = "availability_status", source = "productsEntity.availability_status"),
                    @Mapping(target = "stock", source = "productsEntity.stock"),
                    @Mapping(target = "productCategories", source = "productsEntity.productCategories"),
                    // @Mapping(target = "productVariations", source = "entities.productVariations")

            }
    )
    ProductResponse entityToResponse2(ProductsEntity productsEntity);
    @Mappings(
            {
                    @Mapping(target = "productName", source = "updateRequest.productName"),
                    @Mapping(target = "description", source = "updateRequest.description"),
                    @Mapping(target = "price", source = "updateRequest.price"),
                    @Mapping(target = "availability_status", source = "updateRequest.availability_status"),
                    @Mapping(target = "image_url", source = "updateRequest.image_url"),
                    @Mapping(target = "stock", source = "updateRequest.stock"),
            }
    )

    ProductsEntity updateRequest(ProductsEntity existingDetails, ProductUpdateRequest updateRequest);

    @Mappings(
            {
                    @Mapping(target = "productId", source = "productsEntity.productId"),
                    @Mapping(target = "productName", source = "productsEntity.productName"),
                    @Mapping(target = "description", source = "productsEntity.description"),
                    @Mapping(target = "price", source = "productsEntity.price"),
                    @Mapping(target = "availability_status", source = "productsEntity.availability_status"),
                    @Mapping(target = "stock", source = "productsEntity.stock"),
                    @Mapping(target = "image_url", source = "productsEntity.image_url")

            }
    )
    ProductResponse2 entityToResponse3(ProductsEntity productsEntity);
}
