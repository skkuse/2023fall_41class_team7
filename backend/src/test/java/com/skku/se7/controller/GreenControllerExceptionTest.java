package com.skku.se7.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.skku.se7.dto.*;
import com.skku.se7.error.exceptions.CpuTdpWithModelNameException;
import com.skku.se7.error.exceptions.GpuTdpWithModelNameException;
import com.skku.se7.error.exceptions.WithoutProcessorException;
import com.skku.se7.service.GreenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindException;

import static org.assertj.core.api.Assertions.assertThat;
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
                .isExactlyInstanceOf(BindException.class);
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
                .isExactlyInstanceOf(BindException.class);
    }

    /**
     * input : NO Cpu, No Gpu, Only memory
     * expect result : WithoutProcessorException
     */
    @Test
    public void getGreen_NoCpuNoGpu_WithoutProcessorException() throws Exception{
        //given
        Integer memory = -2;
        Double psf = 0.5;
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
}