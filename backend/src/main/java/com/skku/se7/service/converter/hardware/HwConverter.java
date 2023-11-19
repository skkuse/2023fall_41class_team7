package com.skku.se7.service.converter.hardware;

import com.skku.se7.dto.ProcessorModelTdp;
import com.skku.se7.dto.ProcessorSpecRequest;
import com.skku.se7.service.dataHandler.ProcessorTdpHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HwConverter {
    private final ProcessorTdpHandler processorTdpHandler;
    //use default pue
    private static final Double PUE = 1.0;

    //in W/GB from http://dl.acm.org/citation.cfm?doid=3076113.3076117 and https://www.tomshardware.com/uk/reviews/intel-core-i7-5960x-haswell-e-cpu,3918-13.html
    private static final Double MEMORY_POWER = 0.3725;

    public Double calculateCpuPowerNeeded(ProcessorSpecRequest cpuSpec) {
        if(cpuSpec == null) return 0.0;
        Double tdp = cpuSpec.getTdp();
        if(tdp == null) tdp = processorTdpHandler.getCpuTdpByModelName(cpuSpec.getModelName());
        return PUE * cpuSpec.getCoreNumber() * tdp * cpuSpec.getUsageFactor();
    }
    public Double calculateGpuPowerNeeded(ProcessorSpecRequest gpuSpec) {
        if(gpuSpec == null) return 0.0;
        Double tdp = gpuSpec.getTdp();
        if(tdp == null) tdp = processorTdpHandler.getGpuTdpByModelName(gpuSpec.getModelName());
        return PUE * gpuSpec.getCoreNumber() * tdp * gpuSpec.getUsageFactor();
    }
    public Double calculateMemoryPowerNeeded(Integer memory) {
        return PUE * memory * MEMORY_POWER;
    }
}
