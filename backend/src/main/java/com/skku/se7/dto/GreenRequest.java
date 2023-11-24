package com.skku.se7.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GreenRequest {
    @Valid
    private HwSpecRequest hwSpecRequest;
    @Valid
    private LocationRequest locationRequest;
    @NotBlank
    private String javaCode;
}
