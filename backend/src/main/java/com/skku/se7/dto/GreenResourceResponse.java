package com.skku.se7.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Builder
@NoArgsConstructor
public class GreenResourceResponse {
    private Double totalCarbonFootprint;
    private Double totalEnergyNeeded;

    private HwFootprint hwFootprint;

    private InterpretedFootprint interpretedFootprint;

    public GreenResourceResponse(Double totalCarbonFootprint, Double totalEnergyNeeded, HwFootprint hwFootprint, InterpretedFootprint interpretedFootprint) {
        this.totalCarbonFootprint = totalCarbonFootprint);
        this.totalEnergyNeeded = totalEnergyNeeded);
        this.hwFootprint = hwFootprint;
        this.interpretedFootprint = interpretedFootprint;
    }
}
