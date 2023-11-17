package com.skku.se7.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessorSpecRequest {
    private Optional<String> modelName;
    @Range(max = 1, min = 0)
    private Double usageFactor = 1.0;
    @Min(1)
    private Integer coreNumber;
    private Optional<Double> tdp = Optional.of(1.0);
}
