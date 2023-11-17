package com.skku.se7.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GpuSpecRequest {
    private String modelName;
    private Double usageFactor;
    private Integer coreNumber;
}
