package com.skku.se7.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.skku.se7.Se7Configuration;
import com.skku.se7.dto.GreenRequest;
import com.skku.se7.dto.HwSpecRequest;
import com.skku.se7.dto.LocationRequest;
import com.skku.se7.dto.ProcessorSpecRequest;
import com.skku.se7.dto.enums.Continent;
import com.skku.se7.error.exceptions.*;
import com.skku.se7.error.handler.ControllerExceptionHandler;
import com.skku.se7.service.GreenService;
import com.skku.se7.service.LocationHandler;
import com.skku.se7.service.ProcessorTdpHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(Se7Configuration.class)
@WebMvcTest(controllers = GreenController.class)
class GreenControllerExceptionTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GreenService greenService;
    @MockBean
    private ProcessorTdpHandler processorTdpHandler;
    @InjectMocks
    private LocationHandler locationHandler;
    @MockBean
    private ControllerExceptionHandler controllerExceptionHandler;

    /**
     * input : No java Code, No memory, No Gpu CoreNum(Only Gpu Given)
     * expect result : BindException
     */
    @Test
    public void getGreen_WithoutRequiredFields_BindException() throws Exception {
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

        Continent continent = Continent.NORTH_AMERICA;
        String country = "Canada";
        String region = "Nunavut";
        LocationRequest locationRequest = new LocationRequest(continent, country, region);

        String javaCode = null;
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, locationRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(controllerExceptionHandler.bindException(any(), any()))
                .willReturn(ResponseEntity.badRequest().build());

        //when
        MvcResult missingRequestFieldsResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andDo(
                        document("green/exception/required")
                )
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
    public void getGreen_OutOfRangeFields_BindException() throws Exception {
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

        Continent continent = Continent.NORTH_AMERICA;
        String country = "Canada";
        String region = "Nunavut";
        LocationRequest locationRequest = new LocationRequest(continent, country, region);

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
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, locationRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(controllerExceptionHandler.bindException(any(), any()))
                .willReturn(ResponseEntity.badRequest().build());

        //when
        MvcResult missingRequestFieldsResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andDo(
                        document("green/exception/range")
                )
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
    public void getGreen_NoCpuNoGpu_WithoutProcessorException() throws Exception {
        //given
        Integer memory = 2;
        Double psf = 1.6;
        HwSpecRequest hwSpecRequest = new HwSpecRequest(null, null, memory, psf);

        Continent continent = Continent.NORTH_AMERICA;
        String country = "Canada";
        String region = "Nunavut";
        LocationRequest locationRequest = new LocationRequest(continent, country, region);

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
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, locationRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(controllerExceptionHandler.withoutProcessorException(any(), any()))
                .willReturn(ResponseEntity.badRequest().build());

        //when
        MvcResult missingRequestFieldsResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andDo(
                        document("green/exception/noProcessor")
                )
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
    public void getGreen_InputCpuTdpWithModelName_CpuTdpWithModelNameException() throws Exception {
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

        Continent continent = Continent.NORTH_AMERICA;
        String country = "Canada";
        String region = "Nunavut";
        LocationRequest locationRequest = new LocationRequest(continent, country, region);

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
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, locationRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(controllerExceptionHandler.cpuTdpWithModelNameException(any(), any()))
                .willReturn(ResponseEntity.badRequest().build());

        //when
        MvcResult tdpWithModelNameResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andDo(
                        document("green/exception/cpuTdpWithName")
                )
                .andReturn();

        //then
        assertThat(tdpWithModelNameResult.getResolvedException()).isExactlyInstanceOf(CpuTdpWithModelNameException.class);
    }

    /**
     * input : gpu model + gpu tdp
     * expect result :
     */
    @Test
    public void getGreen_InputGpuTdpWithModelName_GpuTdpWithModelNameException() throws Exception {
        //given
        Double cpuUsageFactor = 0.8;
        Integer cpuCoreNumber = 8;
        Double cpuTdp = 3.0;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .tdp(cpuTdp)
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

        Continent continent = Continent.NORTH_AMERICA;
        String country = "Canada";
        String region = "Nunavut";
        LocationRequest locationRequest = new LocationRequest(continent, country, region);

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
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, locationRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(controllerExceptionHandler.gpuTdpWithModelNameException(any(), any()))
                .willReturn(ResponseEntity.badRequest().build());

        //when
        MvcResult tdpWithModelNameResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andDo(
                        document("green/exception/gpuTdpWithName")
                )
                .andReturn();

        //then
        assertThat(tdpWithModelNameResult.getResolvedException()).isExactlyInstanceOf(GpuTdpWithModelNameException.class);
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_UnmatchedCpuModelName_NoMatchCpuModelNameException() throws Exception {
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

        Continent continent = Continent.NORTH_AMERICA;
        String country = "Canada";
        String region = "Nunavut";
        LocationRequest locationRequest = new LocationRequest(continent, country, region);

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
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, locationRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(processorTdpHandler.validateCpuModel(cpuModelName)).willReturn(false);
        given(controllerExceptionHandler.noMatchCpuModelNameException(any(), any()))
                .willReturn(ResponseEntity.badRequest().build());

        //when
        MvcResult badCpuModelNameResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andDo(
                        document("green/exception/badCpuName")
                )
                .andReturn();

        //then
        assertThat(badCpuModelNameResult.getResolvedException()).isExactlyInstanceOf(NoMatchCpuModelNameException.class);

    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_UnmatchedGpuModelName_NoMatchGpuModelNameException() throws Exception {
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

        Continent continent = Continent.NORTH_AMERICA;
        String country = "Canada";
        String region = "Nunavut";
        LocationRequest locationRequest = new LocationRequest(continent, country, region);

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
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, locationRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(processorTdpHandler.validateGpuModel(modelName)).willReturn(false);
        given(controllerExceptionHandler.noMatchGpuModelNameException(any(), any()))
                .willReturn(ResponseEntity.badRequest().build());

        //when
        MvcResult badGpuModelNameResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andDo(
                        document("green/exception/badGpuName")
                )
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
    public void getGreen_NoTdpNoModelName_CannotInferTdpException() throws Exception {
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

        Continent continent = Continent.NORTH_AMERICA;
        String country = "Canada";
        String region = "Nunavut";
        LocationRequest locationRequest = new LocationRequest(continent, country, region);

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
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, locationRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(controllerExceptionHandler.cannotInferTdpException(any(), any()))
                .willReturn(ResponseEntity.badRequest().build());

        //when
        MvcResult cannotInferTdpResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andDo(
                        document("green/exception/infer")
                )
                .andReturn();

        //then
        assertThat(cannotInferTdpResult.getResolvedException()).isExactlyInstanceOf(CannotInferTdpException.class);
    }

    /**
     * input : bad country (EUROPE, China)
     * expect result : NoMatchCountryException
     */
    @Test
    public void getGreen_GivenContinentBadCountry_NoMatchCountryException() throws Exception {
        //given
        String cpuModelName = "testCpuModel";
        Double cpuUsageFactor = 0.8;
        Integer cpuCoreNumber = 8;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .modelName(cpuModelName)
                .usageFactor(cpuUsageFactor)
                .coreNumber(cpuCoreNumber)
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

        Continent continent = Continent.EUROPE;
        String country = "China";
        String region = "Taiwan";
        LocationRequest locationRequest = new LocationRequest(continent, country, region);


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
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, locationRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(processorTdpHandler.validateCpuModel(any())).willReturn(true);
        given(processorTdpHandler.validateGpuModel(any())).willReturn(true);
        given(controllerExceptionHandler.noMatchCountryException(any(), any()))
                .willReturn(ResponseEntity.badRequest().build());

        //when
        MvcResult badCountryResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andDo(
                        document("/green/exception/badCountry")
                ).andReturn();

        //then
        assertThat(badCountryResult.getResolvedException()).isExactlyInstanceOf(NoMatchCountryException.class);
    }

    /**
     * input : bad region (ASIA, China, Quebec)
     * expect result : NoMatchCountryException
     */
    @Test
    public void getGreen_GivenContinentAndCountryBadRegion_NoMatchCountryException() throws Exception {
        //given
        String cpuModelName = "testCpuModel";
        Double cpuUsageFactor = 0.8;
        Integer cpuCoreNumber = 8;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .modelName(cpuModelName)
                .usageFactor(cpuUsageFactor)
                .coreNumber(cpuCoreNumber)
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

        Continent continent = Continent.ASIA;
        String country = "China";
        String region = "Quebec";
        LocationRequest locationRequest = new LocationRequest(continent, country, region);


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
        GreenRequest greenRequest = new GreenRequest(hwSpecRequest, locationRequest, javaCode);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content = ow.writeValueAsString(greenRequest);

        //mocking
        given(processorTdpHandler.validateCpuModel(any())).willReturn(true);
        given(processorTdpHandler.validateGpuModel(any())).willReturn(true);
        given(controllerExceptionHandler.noMatchRegionException(any(), any()))
                .willReturn(ResponseEntity.badRequest().build());

        //when
        MvcResult badCountryResult = mockMvc.perform(
                        post("/green")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andDo(
                        document("/green/exception/badRegion")
                ).andReturn();

        //then
        assertThat(badCountryResult.getResolvedException()).isExactlyInstanceOf(NoMatchRegionException.class);
    }

}