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
    private Double cpuCarbonFootprint;
    private Double gpuCarbonFootprint;
    private Double memoryCarbonFootprint;
}
