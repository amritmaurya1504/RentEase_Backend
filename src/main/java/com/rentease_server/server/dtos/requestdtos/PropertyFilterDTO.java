package com.rentease_server.server.dtos.requestdtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyFilterDTO {
    private Double minBudget;
    private Double maxBudget;
    private List<String> propertyTypes;
    private List<String> size;
    private List<String> furnishedStatuses;
    private String availabilityStatus;
    private List<String> amenities;
    private String city;
    private String state;
    private String sortBy;
    private List<String> tenantTypes;
}
