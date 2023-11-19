package com.skku.se7.service.converter;

import com.skku.se7.dto.InterpretedFootprint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceInterpreter {
    public InterpretedFootprint interpretCarbonEmission(Double carbonEmission) {
        return null;
    }
}
