package com.skku.se7.dto;

import com.skku.se7.dto.enums.Continent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {
    @NotNull
    private Continent continent;
    @NotBlank
    private String country;
    @NotBlank
    private String region;
}
