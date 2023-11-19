package com.skku.se7.service;

import com.skku.se7.dto.GreenRequest;
import com.skku.se7.dto.GreenResourceResponse;
import com.skku.se7.dto.HwFootprint;
import com.skku.se7.dto.InterpretedFootprint;
import com.skku.se7.service.converter.ResourceInterpreter;
import com.skku.se7.service.converter.code.CodeConverter;
import com.skku.se7.service.converter.hardware.HwConverter;
import com.skku.se7.service.converter.code.JavaCodeCompiler;
import com.skku.se7.service.converter.code.JavaRunner;
import com.skku.se7.service.converter.code.JavaValidator;
import com.skku.se7.service.dataHandler.LocationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GreenService {
    private final CodeConverter codeConverter;
    private final HwConverter hwConverter;
    private final ResourceInterpreter resourceInterpreter;
    private final LocationHandler locationHandler;

    public GreenResourceResponse calculateGreen(GreenRequest greenRequest) throws Exception {
        Long codeRunTime = codeConverter.executeSynchronously(greenRequest.getJavaCode());

        Double cpuPowerNeeded = hwConverter.calculateCpuPowerNeeded(greenRequest.getHwSpecRequest().getCpuSpecRequest());
        Double gpuPowerNeeded = hwConverter.calculateCpuPowerNeeded(greenRequest.getHwSpecRequest().getGpuSpecRequest());
        Double memoryPowerNeeded = hwConverter.calculateMemoryPowerNeeded(greenRequest.getHwSpecRequest().getMemoryGigaByte());

        Double psf = greenRequest.getHwSpecRequest().getPsf() / 1000;

        Double cpuEnergyNeeded = codeRunTime * cpuPowerNeeded * psf;
        Double gpuEnergyNeeded = codeRunTime * gpuPowerNeeded * psf;
        Double memoryEnergyNeeded = codeRunTime * memoryPowerNeeded * psf;

        Double carbonIntensity = locationHandler.getCarbonIntensity(greenRequest.getLocationRequest());

        Double cpuCarbonEmission = cpuEnergyNeeded * carbonIntensity;
        Double gpuCarbonEmission = gpuEnergyNeeded * carbonIntensity;
        Double memoryCarbonEmission = memoryEnergyNeeded * carbonIntensity;
        HwFootprint hwFootprint = new HwFootprint(cpuCarbonEmission, gpuCarbonEmission, memoryCarbonEmission);

        Double totalCarbonEmission = cpuCarbonEmission + gpuCarbonEmission + memoryCarbonEmission;

        InterpretedFootprint interpretedFootprint = resourceInterpreter.interpretCarbonEmission(totalCarbonEmission);

        return new GreenResourceResponse(totalCarbonEmission, hwFootprint, interpretedFootprint);
    }
}
