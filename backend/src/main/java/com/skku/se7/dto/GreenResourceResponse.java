package com.skku.se7.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GreenResourceResponse {
    private Double totalCarbonFootprint;

    private HwFootprint hwFootprint;

    private ConvertedFootprint convertedFootprint;
    private DifferentRegionFootprint differentRegionFootprint;
}
