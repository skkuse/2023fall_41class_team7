package com.skku.se7.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.skku.se7.Se7Configuration;
import com.skku.se7.dto.*;
import com.skku.se7.error.exceptions.*;
import com.skku.se7.error.handler.ControllerExceptionHandler;
import com.skku.se7.service.GreenService;
import com.skku.se7.service.ProcessorTdpHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(controllers = GreenController.class)
class GreenControllerExceptionTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GreenService greenService;
    @MockBean
    private ProcessorTdpHandler processorTdpHandler;
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;

    @BeforeEach
    public void beforeEach() throws NoSuchMethodException {
    }


    /**
     * input : No java Code, No memory, No Gpu CoreNum(Only Gpu Given)
     * expect result : BindException
     */
    @Test
    public void getGreen_WithoutRequiredFields_BindException() throws Exception{
        //given
        Double gpuUsageFactor = 0.7;
        Integer gpuCoreNumber = null;
        Double gpuTdp = 25.0;
        ProcessorSpecRequest gpuSpecRequest = ProcessorSpecRequest.builder()
                .tdp(gpuTdp)
                .usageFactor(gpuUsageFactor)
                .coreNumber(gpuCoreNumber)
                .build();

        Integer memory = null;
        Double psf = 2.7;
        HwSpecRequest hwSpecRequest = new HwSpecRequest(null, gpuSpecRequest, memory, psf);

        String javaCode = null;
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //when
        MvcResult missingRequestFieldsResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(missingRequestFieldsResult.getResolvedException())
                .isInstanceOf(BindException.class);
    }

    /**
     * input : Out of range List - cpu Usage, gpu coreNum, memory, psf
     * expect result : BindException
     */
    @Test
    public void getGreen_OutOfRangeFields_BindException() throws Exception{
        //given
        String cpuModelName = "testCpuModel";
        Double cpuUsageFactor = 1.8;
        Integer cpuCoreNumber = 1;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .modelName(cpuModelName)
                .usageFactor(cpuUsageFactor)
                .coreNumber(cpuCoreNumber)
                .build();

        Double gpuUsageFactor = 0.7;
        Integer gpuCoreNumber = -2;
        Double gpuTdp = 25.0;
        ProcessorSpecRequest gpuSpecRequest = ProcessorSpecRequest.builder()
                .tdp(gpuTdp)
                .usageFactor(gpuUsageFactor)
                .coreNumber(gpuCoreNumber)
                .build();

        Integer memory = 0;
        Double psf = 0.8;
        HwSpecRequest hwSpecRequest = new HwSpecRequest(cpuSpecRequest, gpuSpecRequest, memory, psf);

        String javaCode = """
                public class Main {
                    public static void main(String[] args) {
                        int i = 1;
                        float f = 1.1f;
                        double d = 1.2;
                        boolean b = true;
                        char c = 'a';
                        System. out.println(i);
                        System. out.println(f);
                        System. out.println(d);
                        System. out.println(b);
                        System. out.println(c);
                    }
                }
                """;
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);


        //when
        MvcResult missingRequestFieldsResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(missingRequestFieldsResult.getResolvedException())
                .isInstanceOf(BindException.class);
    }

    /**
     * input : NO Cpu, No Gpu, Only memory
     * expect result : WithoutProcessorException
     */
    @Test
    public void getGreen_NoCpuNoGpu_WithoutProcessorException() throws Exception{
        //given
        Integer memory = 2;
        Double psf = 1.6;
        HwSpecRequest hwSpecRequest = new HwSpecRequest(null, null, memory, psf);

        String javaCode = """
                public class Main {
                    public static void main(String[] args) {
                        int i = 1;
                        float f = 1.1f;
                        double d = 1.2;
                        boolean b = true;
                        char c = 'a';
                        System. out.println(i);
                        System. out.println(f);
                        System. out.println(d);
                        System. out.println(b);
                        System. out.println(c);
                    }
                }
                """;
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking

        //when
        MvcResult missingRequestFieldsResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(missingRequestFieldsResult.getResolvedException())
                .isExactlyInstanceOf(WithoutProcessorException.class);
    }

    /**
     * input : cpu model + cpu tdp
     * expect result :
     */
    @Test
    public void getGreen_InputCpuTdpWithModelName_CpuTdpWithModelNameException() throws Exception{
        //given
        String cpuModelName = "testCpuModel";
        Double cpuUsageFactor = 0.8;
        Integer cpuCoreNumber = 8;
        Double cpuTdp = 25.0;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .modelName(cpuModelName)
                .usageFactor(cpuUsageFactor)
                .coreNumber(cpuCoreNumber)
                .tdp(cpuTdp)
                .build();

        Double gpuUsageFactor = 0.7;
        Integer gpuCoreNumber = 7;
        Double gpuTdp = 25.0;
        ProcessorSpecRequest gpuSpecRequest = ProcessorSpecRequest.builder()
                .tdp(gpuTdp)
                .usageFactor(gpuUsageFactor)
                .coreNumber(gpuCoreNumber)
                .build();

        Integer memory = 8;
        Double psf = 2.7;
        HwSpecRequest hwSpecRequest = new HwSpecRequest(cpuSpecRequest, gpuSpecRequest, memory, psf);

        String javaCode = """
                public class Main {
                    public static void main(String[] args) {
                        int i = 1;
                        float f = 1.1f;
                        double d = 1.2;
                        boolean b = true;
                        char c = 'a';
                        System. out.println(i);
                        System. out.println(f);
                        System. out.println(d);
                        System. out.println(b);
                        System. out.println(c);
                    }
                }
                """;
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking

        //when
        MvcResult tdpWithModelNameResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(tdpWithModelNameResult.getResolvedException()).isExactlyInstanceOf(CpuTdpWithModelNameException.class);
    }

    /**
     * input : gpu model + gpu tdp
     * expect result :
     */
    @Test
    public void getGreen_InputGpuTdpWithModelName_GpuTdpWithModelNameException() throws Exception{
        //given
        String cpuModelName = "testCpuModel";
        Double cpuUsageFactor = 0.8;
        Integer cpuCoreNumber = 8;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .modelName(cpuModelName)
                .usageFactor(cpuUsageFactor)
                .coreNumber(cpuCoreNumber)
                .build();

        String gpuModelName = "testCpuModel";
        Double gpuUsageFactor = 0.7;
        Integer gpuCoreNumber = 7;
        Double gpuTdp = 25.0;
        ProcessorSpecRequest gpuSpecRequest = ProcessorSpecRequest.builder()
                .modelName(gpuModelName)
                .tdp(gpuTdp)
                .usageFactor(gpuUsageFactor)
                .coreNumber(gpuCoreNumber)
                .build();

        Integer memory = 8;
        Double psf = 2.7;
        HwSpecRequest hwSpecRequest = new HwSpecRequest(cpuSpecRequest, gpuSpecRequest, memory, psf);

        String javaCode = """
                public class Main {
                    public static void main(String[] args) {
                        int i = 1;
                        float f = 1.1f;
                        double d = 1.2;
                        boolean b = true;
                        char c = 'a';
                        System. out.println(i);
                        System. out.println(f);
                        System. out.println(d);
                        System. out.println(b);
                        System. out.println(c);
                    }
                }
                """;
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking

        //when
        MvcResult tdpWithModelNameResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(tdpWithModelNameResult.getResolvedException()).isExactlyInstanceOf(GpuTdpWithModelNameException.class);
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_UnmatchedCpuModelName_NoMatchCpuModelNameException() throws Exception{
        String cpuModelName = "testCpuModel";
        Double cpuUsageFactor = 0.8;
        Integer cpuCoreNumber = 8;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .modelName(cpuModelName)
                .usageFactor(cpuUsageFactor)
                .coreNumber(cpuCoreNumber)
                .build();

        Integer memory = 8;
        Double psf = 2.7;
        HwSpecRequest hwSpecRequest = new HwSpecRequest(cpuSpecRequest, null, memory, psf);

        String javaCode = """
                public class Main {
                    public static void main(String[] args) {
                        int i = 1;
                        float f = 1.1f;
                        double d = 1.2;
                        boolean b = true;
                        char c = 'a';
                        System. out.println(i);
                        System. out.println(f);
                        System. out.println(d);
                        System. out.println(b);
                        System. out.println(c);
                    }
                }
                """;
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(processorTdpHandler.validateCpuModel(cpuModelName)).willReturn(false);

        //when
        MvcResult badCpuModelNameResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(badCpuModelNameResult.getResolvedException()).isExactlyInstanceOf(NoMatchCpuModelNameException.class);

    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_UnmatchedGpuModelName_NoMatchGpuModelNameException() throws Exception{
        //given
        String modelName = "testModelName";
        Double gpuUsageFactor = 0.7;
        Integer gpuCoreNumber = 7;
        ProcessorSpecRequest gpuSpecRequest = ProcessorSpecRequest.builder()
                .modelName(modelName)
                .usageFactor(gpuUsageFactor)
                .coreNumber(gpuCoreNumber)
                .build();

        Integer memory = 8;
        Double psf = 2.7;
        HwSpecRequest hwSpecRequest = new HwSpecRequest(null, gpuSpecRequest, memory, psf);

        String javaCode = """
                public class Main {
                    public static void main(String[] args) {
                        int i = 1;
                        float f = 1.1f;
                        double d = 1.2;
                        boolean b = true;
                        char c = 'a';
                        System. out.println(i);
                        System. out.println(f);
                        System. out.println(d);
                        System. out.println(b);
                        System. out.println(c);
                    }
                }
                """;
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(processorTdpHandler.validateGpuModel(modelName)).willReturn(false);

        //when
        MvcResult badGpuModelNameResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(badGpuModelNameResult.getResolvedException())
                .isExactlyInstanceOf(NoMatchGpuModelNameException.class);

    }

    /**
     * input : Cpu(No Model Name, No Tdp)
     * expect result :
     */
    @Test
    public void getGreen_NoTdpNoModelName_CannotInferTdpException() throws Exception{
        //given
        String cpuModelName = null;
        Double cpuTdp = null;
        Double cpuUsageFactor = 0.8;
        Integer cpuCoreNumber = 8;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .modelName(cpuModelName)
                .tdp(cpuTdp)
                .usageFactor(cpuUsageFactor)
                .coreNumber(cpuCoreNumber)
                .build();

        Integer memory = 8;
        Double psf = 2.7;
        HwSpecRequest hwSpecRequest = new HwSpecRequest(cpuSpecRequest, null, memory, psf);

        String javaCode = """
                public class Main {
                    public static void main(String[] args) {
                        int i = 1;
                        float f = 1.1f;
                        double d = 1.2;
                        boolean b = true;
                        char c = 'a';
                        System. out.println(i);
                        System. out.println(f);
                        System. out.println(d);
                        System. out.println(b);
                        System. out.println(c);
                    }
                }
                """;
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking

        //when
        MvcResult cannotInferTdpResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn();

        //then
        assertThat(cannotInferTdpResult.getResolvedException()).isExactlyInstanceOf(CannotInferTdpException.class);

    }

}