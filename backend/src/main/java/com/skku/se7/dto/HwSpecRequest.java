package com.skku.se7.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HwSpecRequest {
    private CpuSpecRequest cpuSpecRequest;
    private GpuSpecRequest gpuSpecRequest;
    private Integer memoryGigaByte;
    private Double pragmaScalingFactor;
}
