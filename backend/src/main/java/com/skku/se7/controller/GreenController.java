package com.skku.se7.controller;

import com.skku.se7.dto.GreenRequest;
import com.skku.se7.dto.GreenResourceResponse;
import com.skku.se7.dto.HwSpecRequest;
import com.skku.se7.dto.ProcessorSpecRequest;
import com.skku.se7.error.exceptions.*;
import com.skku.se7.service.GreenService;
import com.skku.se7.service.ProcessorTdpHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GreenController {
    private final GreenService greenService;
    private final ProcessorTdpHandler processorTdpHandler;

    @PostMapping("/green")
    public GreenResourceResponse getGreen(@Valid @RequestBody GreenRequest greenRequest) {
        validateHw(greenRequest.getHwSpecRequest());
        return greenService.calculateGreen(greenRequest);
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
            throw new CpuTdpWithModelNameException();

        if(modelName == null && tdp == null)
            throw new CannotInferTdpException();

        if(modelName != null && !processorTdpHandler.validateCpuModel(modelName))
            throw new NoMatchCpuModelNameException();
    }

    private void validateGpu(ProcessorSpecRequest processor) throws CannotInferTdpException, GpuTdpWithModelNameException, NoMatchGpuModelNameException {
        String modelName = processor.getModelName();
        Double tdp = processor.getTdp();

        if(tdp != null && modelName != null)
            throw new GpuTdpWithModelNameException();

        if(modelName == null && tdp == null)
            throw new CannotInferTdpException();

        if(modelName != null && !processorTdpHandler.validateGpuModel(modelName))
            throw new NoMatchGpuModelNameException();
    }

}
