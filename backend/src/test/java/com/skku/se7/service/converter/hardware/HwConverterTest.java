package com.skku.se7.service.converter.hardware;

import com.skku.se7.Se7Configuration;
import com.skku.se7.dto.ProcessorSpecRequest;
import com.skku.se7.service.dataHandler.ProcessorTdpHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;

@Slf4j
@ExtendWith(MockitoExtension.class)
@Import(Se7Configuration.class)
class HwConverterTest {
    @InjectMocks
    private HwConverter hwConverter;
    @Mock
    private ProcessorTdpHandler processorTdpHandler;


    /**
     * input : Model Name(=null), Tdp
     * expect result : Handler 호출 없이 리턴
     */
    @Test
    public void calculateCpuPowerNeeded_givenTdp_NotCallHandler() throws Exception {
        //given
        String modelName = null;
        Double tdp = 3.3;
        Double usageFactor = 0.6;
        Integer coreNumber = 8;

        ProcessorSpecRequest processorSpecRequest = new ProcessorSpecRequest(modelName, usageFactor, coreNumber, tdp);

        //mocking
        lenient().doThrow(RuntimeException.class).when(processorTdpHandler).getCpuTdpByModelName(any());

        //when
        Double powerNeeded = hwConverter.calculateCpuPowerNeeded(processorSpecRequest);

        //then
        assertThat(powerNeeded).isEqualTo(tdp * coreNumber * usageFactor);
    }

    /**
     * input : Model Name, Tdp(=null)
     * expect result :
     */
    @Test
    public void calculateCpuPowerNeeded_givenModelNameTdpIsNUll_ConvertNameToHandler() throws Exception {
        //given
        String modelName = "testModelName";
        Double tdp = null;
        Double expectedTdp = 3.3;
        Double usageFactor = 0.6;
        Integer coreNumber = 8;

        ProcessorSpecRequest processorSpecRequest = new ProcessorSpecRequest(modelName, usageFactor, coreNumber, tdp);

        //mocking
        given(processorTdpHandler.getCpuTdpByModelName(modelName)).willReturn(expectedTdp);

        //when
        Double powerNeeded = hwConverter.calculateCpuPowerNeeded(processorSpecRequest);

        //then
        assertThat(powerNeeded).isEqualTo(expectedTdp * coreNumber * usageFactor);
    }

    /**
     * input : Null
     * expect result : 0
     */
    @Test
    public void calculateCpuPowerNeeded_ProcessorIsNull_ReturnZero() throws Exception {
        //given
        ProcessorSpecRequest processorSpecRequest = null;

        //mocking

        //when
        Double powerNeeded = hwConverter.calculateCpuPowerNeeded(processorSpecRequest);

        //then
        assertThat(powerNeeded).isZero();
    }

    /**
     * input : Model Name(=null), Tdp
     * expect result : Handler 호출 없이 리턴
     */
    @Test
    public void calculateGpuPowerNeeded_givenTdp_NotCallHandler() throws Exception {
        //given
        String modelName = null;
        Double tdp = 3.3;
        Double usageFactor = 0.6;
        Integer coreNumber = 8;

        ProcessorSpecRequest processorSpecRequest = new ProcessorSpecRequest(modelName, usageFactor, coreNumber, tdp);

        //mocking
        lenient().doThrow(RuntimeException.class).when(processorTdpHandler).getGpuTdpByModelName(any());

        //when
        Double powerNeeded = hwConverter.calculateGpuPowerNeeded(processorSpecRequest);

        //then
        assertThat(powerNeeded).isEqualTo(tdp * coreNumber * usageFactor);
    }

    /**
     * input : Model Name, Tdp(=null)
     * expect result :
     */
    @Test
    public void calculateGpuPowerNeeded_givenModelNameTdpIsNUll_ConvertNameToHandler() throws Exception {
        //given
        String modelName = "testModelName";
        Double tdp = null;
        Double expectedTdp = 3.3;
        Double usageFactor = 0.6;
        Integer coreNumber = 8;

        ProcessorSpecRequest processorSpecRequest = new ProcessorSpecRequest(modelName, usageFactor, coreNumber, tdp);

        //mocking
        given(processorTdpHandler.getGpuTdpByModelName(modelName)).willReturn(expectedTdp);

        //when
        Double powerNeeded = hwConverter.calculateGpuPowerNeeded(processorSpecRequest);

        //then
        assertThat(powerNeeded).isEqualTo(expectedTdp * coreNumber * usageFactor);
    }

    /**
     * input : Null
     * expect result : 0
     */
    @Test
    public void calculateGpuPowerNeeded_ProcessorIsNull_ReturnZero() throws Exception {
        //given
        ProcessorSpecRequest processorSpecRequest = null;

        //mocking

        //when
        Double powerNeeded = hwConverter.calculateGpuPowerNeeded(processorSpecRequest);

        //then
        assertThat(powerNeeded).isZero();
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void calculateMemoryPowerNeeded_Default_Sucess() throws Exception{
        //given
        Integer memory = 8;

        //mocking

        //when
        Double powerNeeded = hwConverter.calculateMemoryPowerNeeded(memory);

        //then
        assertThat(powerNeeded).isEqualTo(memory * 0.3725);

    }
}