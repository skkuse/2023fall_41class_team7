package com.skku.se7.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HwSpecRequest {
    private ProcessorSpecRequest cpuSpecRequest;
    private ProcessorSpecRequest gpuSpecRequest;
    @Min(1)
    private Integer memoryGigaByte;
    @Min(1)
    private Double psf = 1.0;
}
