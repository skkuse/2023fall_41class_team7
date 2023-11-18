package com.skku.se7.controller;

import com.skku.se7.dto.ViewResponse;
import com.skku.se7.dto.enums.Continent;
import com.skku.se7.service.HwConverter;
import com.skku.se7.service.LocationHandler;
import com.skku.se7.service.ProcessorTdpHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ViewController {
    private final ProcessorTdpHandler processorTdpHandler;
    private final LocationHandler locationHandler;

    @GetMapping("/model/cpu")
    public ViewResponse<String> getCpuModels() {
        return new ViewResponse<>(processorTdpHandler.getCpuModelNames());
    }

    @GetMapping("/model/gpu")
    public ViewResponse<String> getGpuModels() {
        return new ViewResponse<>(processorTdpHandler.getGpuModelNames());
    }

    @GetMapping("/location")
    public ViewResponse<String> getContinents() {
        return new ViewResponse<>(locationHandler.getContinents());
    }

    @GetMapping("/location/{continent}")
    public ViewResponse<String> getCountries(@PathVariable Continent continent) {
        return new ViewResponse<>(locationHandler.getCountries(continent));
    }

    @GetMapping("/location/{continent}/{country}")
    public ViewResponse<String> getRegions(@PathVariable Continent continent, @PathVariable String country) {
        return new ViewResponse<>(locationHandler.getRegions(continent, country));
    }
}
