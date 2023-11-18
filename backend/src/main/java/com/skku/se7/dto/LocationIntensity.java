package com.skku.se7.dto;

import com.skku.se7.dto.enums.Continent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationIntensity {
    private Continent continent;
    private String country;
    private String region;
    private Double carbonIntensity;
}
