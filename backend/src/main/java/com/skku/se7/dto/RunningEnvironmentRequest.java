package com.skku.se7.dto;

import com.skku.se7.dto.enums.PlatformType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunningEnvironmentRequest {
    private PlatformType platformType;
    private LocationInfo locationInfo;
    private Double pue;
}
