package com.b2bAr.arzoo.mapper;

import com.b2bAr.arzoo.entity.BannersEntity;
import com.b2bAr.arzoo.request.BannerRequest;
import com.b2bAr.arzoo.response.BannerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BannerMapper {

    @Mappings(
            {
                    @Mapping(target = "bannerName", source = "bannerRequest.bannerName"),
                    @Mapping(target = "image_url", source = "bannerRequest.image_url"),
                    @Mapping(target = "displayStatus", source = "bannerRequest.displayStatus"),
            }
    )
    BannersEntity requestToEntity(BannerRequest bannerRequest);
    @Mappings(
            {
                    @Mapping(target = "bannerId", source = "bannerEntity.bannerId"),
                    @Mapping(target = "bannerName", source = "bannerEntity.bannerName"),
                    @Mapping(target = "image_url", source = "bannerEntity.image_url"),
                    @Mapping(target = "displayStatus", source = "bannerEntity.displayStatus"),
            }
    )
    BannerResponse entityToResponse(BannersEntity bannerEntity);

    @Mappings(
            {
                    @Mapping(target = "bannerId", source = "bannerEntity.bannerId"),
                    @Mapping(target = "bannerName", source = "bannerEntity.bannerName"),
                    @Mapping(target = "image_url", source = "bannerEntity.image_url"),
                    @Mapping(target = "displayStatus", source = "bannerEntity.displayStatus"),
            }
    )
    List<BannerResponse> entityToResponse2(List<BannersEntity> bannersEntity);

    @Mappings(
            {
                    @Mapping(target = "bannerName", source = "bannerRequest1.bannerName"),
                    @Mapping(target = "image_url", source = "bannerRequest1.image_url"),
                    @Mapping(target = "displayStatus", source = "bannerRequest1.displayStatus"),
            }
    )
    BannersEntity updateRequest(BannersEntity entity1, BannerRequest bannerRequest1);
}
