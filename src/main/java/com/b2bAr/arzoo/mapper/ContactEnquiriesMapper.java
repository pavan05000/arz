package com.b2bAr.arzoo.mapper;


import com.b2bAr.arzoo.entity.ContactEnquiries;

import com.b2bAr.arzoo.request.ContactEnquiriesRequest;
import com.b2bAr.arzoo.request.ContactEnquiriesRequest2;
import com.b2bAr.arzoo.response.ContactEnquiriesResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactEnquiriesMapper {

    @Mappings({
//            @Mapping(target = "enquiryId", source = "contestEnquiriesMapper.enquiryId"),
//            @Mapping(target = "userEntity", source = "contestEnquiriesMapper.userEntity"),
            @Mapping(target = "enquiryDate", source = "contactEnquiriesRequest.enquiryDate"),
            @Mapping(target = "enquiryMessage", source = "contactEnquiriesRequest.enquiryMessage"),
            // @Mapping(target = "responseMessage", source = "contactEnquiriesRequest.responseMessage"),

    })
    ContactEnquiries requestToEntity(ContactEnquiriesRequest contactEnquiriesRequest);


    @Mappings({
            @Mapping(target = "enquiryId", source = "contactEnquiries.enquiryId"),
            @Mapping(target = "userEntity", source = "contactEnquiries.userEntity"),
            @Mapping(target = "enquiryDate", source = "contactEnquiries.enquiryDate"),
            @Mapping(target = "enquiryMessage", source = "contactEnquiries.enquiryMessage"),
            @Mapping(target = "responseMessage", source = "contactEnquiries.responseMessage"),

    })
    ContactEnquiriesResponse entityToResponse(ContactEnquiries contactEnquiries);

    @Mappings({
            @Mapping(target = "enquiryId", source = "entity.enquiryId"),
            @Mapping(target = "userEntity", source = "entity.userEntity"),
            @Mapping(target = "enquiryDate", source = "entity.enquiryDate"),
            @Mapping(target = "enquiryMessage", source = "entity.enquiryMessage"),
            @Mapping(target = "responseMessage", source = "entity.responseMessage"),

    })
    List<ContactEnquiriesResponse> entityToResponse2(List<ContactEnquiries> entity);

    @Mappings({
            @Mapping(target = "enquiryDate", source = "contactEnquiriesRequest2.enquiryDate"),
            @Mapping(target = "enquiryMessage", source = "contactEnquiriesRequest2.enquiryMessage"),
            @Mapping(target = "responseMessage", source = "contactEnquiriesRequest2.responseMessage"),

    })
    ContactEnquiries updateRequest(ContactEnquiries existingDetails, ContactEnquiriesRequest contactEnquiriesRequest2);

    @Mappings({
            @Mapping(target = "responseMessage", source = "contactEnquiriesRequest2.responseMessage"),

    })
    ContactEnquiries requestToEntity2(ContactEnquiries enquiries, ContactEnquiriesRequest2 contactEnquiriesRequest2);
}
