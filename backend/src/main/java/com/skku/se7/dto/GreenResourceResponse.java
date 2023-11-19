package com.skku.se7.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GreenResourceResponse {
    private Double totalCarbonFootprint;
    private Double totalEnergyNeeded;

    private HwFootprint hwFootprint;

    private InterpretedFootprint interpretedFootprint;
}
