package com.skku.se7.dto;

import com.skku.se7.dto.enums.PlatformType;
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
public class RunningEnvironmentRequest {
    private PlatformType platformType;
    private LocationInfo locationInfo;
    @Min(1)
    private Optional<Double> pue = Optional.of(1.0);
}
