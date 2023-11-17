package com.skku.se7.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DifferentRegionFootprint {
    private Double australiaFootprint;
    private Double indiaFootprint;
    private Double chinaFootprint;
    private Double usaFootprint;
    private Double ukFootprint;
    private Double canadaFootprint;
    private Double franceFootprint;
    private Double swedenFootprint;
    private Double switzerlandFootprint;
}
