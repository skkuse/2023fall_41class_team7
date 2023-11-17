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
public class HwFootprint {
    private Optional<Double> cpuCarbonFootprint;
    private Optional<Double> gpuCarbonFootprint;
    private Double memoryCarbonFootprint;
}
