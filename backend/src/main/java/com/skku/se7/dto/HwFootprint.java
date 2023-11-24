package com.skku.se7.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
public class HwFootprint {
    private Double cpuCarbonFootprint;
    private Double gpuCarbonFootprint;
    private Double memoryCarbonFootprint;

    public HwFootprint(Double cpuCarbonFootprint, Double gpuCarbonFootprint, Double memoryCarbonFootprint) {
        this.cpuCarbonFootprint = BigDecimal.valueOf(cpuCarbonFootprint)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        this.gpuCarbonFootprint = BigDecimal.valueOf(gpuCarbonFootprint)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        this.memoryCarbonFootprint =BigDecimal.valueOf(memoryCarbonFootprint)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
