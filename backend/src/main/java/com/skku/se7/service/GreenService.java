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
        Long codeRuntime = codeConverter.executeSynchronously(greenRequest.getJavaCode());
        Double runtime = convertMillisecToHour(codeRuntime);

        Double cpuPowerNeeded = hwConverter.calculateCpuPowerNeeded(greenRequest.getHwSpecRequest().getCpuSpecRequest());
        Double gpuPowerNeeded = hwConverter.calculateGpuPowerNeeded(greenRequest.getHwSpecRequest().getGpuSpecRequest());
        Double memoryPowerNeeded = hwConverter.calculateMemoryPowerNeeded(greenRequest.getHwSpecRequest().getMemoryGigaByte());

        Double psf = greenRequest.getHwSpecRequest().getPsf() / 1000;

        Double cpuEnergyNeeded = runtime * cpuPowerNeeded * psf;
        Double gpuEnergyNeeded = runtime * gpuPowerNeeded * psf;
        Double memoryEnergyNeeded = runtime * memoryPowerNeeded * psf;

        Double totalEnergyNeeded = cpuEnergyNeeded + gpuEnergyNeeded + memoryEnergyNeeded;

        Double carbonIntensity = locationHandler.getCarbonIntensity(greenRequest.getLocationRequest());

        Double cpuCarbonEmission = cpuEnergyNeeded * carbonIntensity;
        Double gpuCarbonEmission = gpuEnergyNeeded * carbonIntensity;
        Double memoryCarbonEmission = memoryEnergyNeeded * carbonIntensity;
        HwFootprint hwFootprint = new HwFootprint(cpuCarbonEmission, gpuCarbonEmission, memoryCarbonEmission);

        Double totalCarbonEmission = cpuCarbonEmission + gpuCarbonEmission + memoryCarbonEmission;

        InterpretedFootprint interpretedFootprint = resourceInterpreter.interpretCarbonEmission(totalCarbonEmission);

        return new GreenResourceResponse(totalCarbonEmission, totalEnergyNeeded, hwFootprint, interpretedFootprint);
    }

    private Double convertMillisecToHour(Long codeRuntime) {
        return Double.valueOf(codeRuntime) / 3_600_000;
    }

}
