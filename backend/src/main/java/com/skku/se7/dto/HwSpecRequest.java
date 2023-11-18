package com.skku.se7.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Valid
    private ProcessorSpecRequest cpuSpecRequest;
    @Valid
    private ProcessorSpecRequest gpuSpecRequest;
    @NotNull
    @Min(1)
    private Integer memoryGigaByte;
    @Min(1)
    private Double psf = 1.0;
}
