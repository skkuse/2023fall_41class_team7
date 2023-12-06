package com.skku.se7.controller;

import com.skku.se7.dto.*;
import com.skku.se7.error.exceptions.*;
import com.skku.se7.service.GreenService;
import com.skku.se7.service.dataHandler.LocationHandler;
import com.skku.se7.service.dataHandler.ProcessorTdpHandler;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GreenController {
    private final GreenService greenService;
    private final ProcessorTdpHandler processorTdpHandler;
    private final LocationHandler locationHandler;

    @PostMapping("/green")
    public GreenResourceResponse getGreen(@Valid @RequestBody GreenRequest greenRequest) throws Exception {
        validateHw(greenRequest.getHwSpecRequest());
        validateLocation(greenRequest.getLocationRequest());
        return greenService.calculateGreen(greenRequest);
    }

    private void validateLocation(LocationRequest locationRequest) throws NoMatchRegionException, NoMatchCountryException {
        if(!locationHandler.validateCountry(locationRequest.getContinent(), locationRequest.getCountry()))
            throw new NoMatchCountryException();
        if(!locationHandler.validateRegion(locationRequest.getContinent(), locationRequest.getCountry(), locationRequest.getRegion()))
            throw new NoMatchRegionException();
    }

    private void validateHw(HwSpecRequest hwSpecRequest) throws WithoutProcessorException {
        if(hwSpecRequest.getCpuSpecRequest() == null && hwSpecRequest.getGpuSpecRequest() == null)
            throw new WithoutProcessorException();

        if(hwSpecRequest.getCpuSpecRequest() != null) validateCpu(hwSpecRequest.getCpuSpecRequest());
        if(hwSpecRequest.getGpuSpecRequest() != null) validateGpu(hwSpecRequest.getGpuSpecRequest());
    }

    private void validateCpu(ProcessorSpecRequest processor) throws CpuTdpWithModelNameException, CannotInferTdpException, NoMatchCpuModelNameException {
        String modelName = processor.getModelName();
        Double tdp = processor.getTdp();

        if(tdp != null && modelName != null)
            modelName = null;

        if(modelName == null && tdp == null)
            throw new CannotInferTdpException();

        if(modelName != null && !processorTdpHandler.validateCpuModel(modelName))
            throw new NoMatchCpuModelNameException();
    }

    private void validateGpu(ProcessorSpecRequest processor) throws CannotInferTdpException, GpuTdpWithModelNameException, NoMatchGpuModelNameException {
        String modelName = processor.getModelName();
        Double tdp = processor.getTdp();

        if(tdp != null && modelName != null)
            modelName = null;

        if(modelName == null && tdp == null)
            throw new CannotInferTdpException();

        if(modelName != null && !processorTdpHandler.validateGpuModel(modelName))
            throw new NoMatchGpuModelNameException();
    }

}
