package com.skku.se7.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.skku.se7.dto.*;
import com.skku.se7.service.GreenService;
import com.skku.se7.service.ProcessorTdpHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static com.skku.se7.controller.ControllerTestUtils.example;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(controllers = GreenController.class)
public class GreenControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GreenService greenService;
    @MockBean
    private ProcessorTdpHandler processorTdpHandler;

    @BeforeEach
    public void beforeEach() {
        given(processorTdpHandler.validateCpuModel(any())).willReturn(true);
        given(processorTdpHandler.validateGpuModel(any())).willReturn(true);
    }

    /**
     * input :모든 정보 입력
     * expect result :
     */
    @Test
    public void getGreen_Default_Success() throws Exception {
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
        Double expectedTotalCarbonFootprint = 126.78;
        Double expectedCpuCarbonFootprint = 32.01;
        Double expectedGpuCarbonFootprint = 42.51;
        Double memoryCarbonFootprint = 52.26;
        Double energyNeeded = 11.111;
        Double treeMonths = 22.222;
        Double passengerCar = 33.333;
        Double flightFromIncheonToLondon = 44.44;

        HwFootprint hwFootprint = new HwFootprint(Optional.of(expectedCpuCarbonFootprint), Optional.of(expectedGpuCarbonFootprint), memoryCarbonFootprint);
        ConvertedFootprint convertedFootprint = new ConvertedFootprint(energyNeeded, treeMonths, passengerCar, flightFromIncheonToLondon);
        GreenResourceResponse greenResourceResponse = new GreenResourceResponse(expectedTotalCarbonFootprint, hwFootprint, convertedFootprint);

        given(greenService.calculateGreen(greenRequest)).willReturn(greenResourceResponse);

        //when
        ResultActions actions = mockMvc.perform(
                post("/green")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCarbonFootprint").value(expectedTotalCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.hwFootprint.cpuCarbonFootprint").value(expectedCpuCarbonFootprint.toString()))
                .andExpect(jsonPath("$.hwFootprint.gpuCarbonFootprint").value(expectedGpuCarbonFootprint.toString()))
                .andExpect(jsonPath("$.hwFootprint.memoryCarbonFootprint").value(memoryCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.convertedFootprint.energyNeeded").value(energyNeeded.toString()))
                .andExpect(jsonPath("$.convertedFootprint.treeMonths").value(treeMonths.toString()))
                .andExpect(jsonPath("$.convertedFootprint.passengerCar").value(passengerCar.toString()))
                .andExpect(jsonPath("$.convertedFootprint.flightFromIncheonToLondon").value(flightFromIncheonToLondon.toString()));

        actions.andDo(
                document(
                        "/green/normal/full",
                        requestFields(
                                fieldWithPath("javaCode").type(JsonFieldType.STRING).description("실행할 자바 코드").attributes(example(javaCode)),

                                fieldWithPath("hwSpecRequest.cpuSpecRequest.modelName").type(JsonFieldType.STRING).description("Processor(CPU or GPU) model 이름").optional().attributes(example("Intel Core i7-14700K")),
                                fieldWithPath("hwSpecRequest.cpuSpecRequest.usageFactor").type(JsonFieldType.NUMBER).description("Processor 코어 한개 가용률(기본값 1, 최소값 0, 최대값 1)").optional().attributes(example("0.8")),
                                fieldWithPath("hwSpecRequest.cpuSpecRequest.coreNumber").type(JsonFieldType.NUMBER).description("활용 가능한 코어 개수(최소값 1)").attributes(example("8")),
                                fieldWithPath("hwSpecRequest.cpuSpecRequest.tdp").type(JsonFieldType.NULL).description("").attributes(example("")).optional(),

                                fieldWithPath("hwSpecRequest.gpuSpecRequest.modelName").type(JsonFieldType.NULL).description("").optional().attributes(example("")),
                                fieldWithPath("hwSpecRequest.gpuSpecRequest.usageFactor").type(JsonFieldType.NUMBER).description("Processor 코어 한개 가용률(기본값 1, 최소값 0, 최대값 1)").optional().attributes(example("0.4")),
                                fieldWithPath("hwSpecRequest.gpuSpecRequest.coreNumber").type(JsonFieldType.NUMBER).description("활용 가능한 코어 개수(최소값 1)").attributes(example("4")),
                                fieldWithPath("hwSpecRequest.gpuSpecRequest.tdp").type(JsonFieldType.NUMBER).description("Processor 코어 한개의 설계 전력(최소값 1").optional().attributes(example("25")),

                                fieldWithPath("hwSpecRequest.memoryGigaByte").type(JsonFieldType.NUMBER).description("사용 가능 메모리 양(최소값 1)").attributes(example("4")),
                                fieldWithPath("hwSpecRequest.psf").type(JsonFieldType.NUMBER).description("프라그마 스케일링 팩터(기본값 1, 최소값 1)").optional().attributes(example("1.5"))
                        ),
                        responseFields(
                                fieldWithPath("totalCarbonFootprint").type(JsonFieldType.NUMBER).description("총 탄소 배출량(세부 탄소 배출량 총합)").attributes(example(expectedTotalCarbonFootprint.toString())),

                                fieldWithPath("hwFootprint.cpuCarbonFootprint").type(JsonFieldType.NUMBER).description("탄소 배출량 - Cpu(입력시)").attributes(example(expectedCpuCarbonFootprint.toString())),
                                fieldWithPath("hwFootprint.gpuCarbonFootprint").type(JsonFieldType.NUMBER).description("탄소 배출량 - Gpu(입력시)").attributes(example(expectedGpuCarbonFootprint.toString())),
                                fieldWithPath("hwFootprint.memoryCarbonFootprint").type(JsonFieldType.NUMBER).description("탄소 배출량 - memory").attributes(example(memoryCarbonFootprint.toString())),

                                fieldWithPath("convertedFootprint.energyNeeded").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 에너지").attributes(example(energyNeeded.toString())),
                                fieldWithPath("convertedFootprint.treeMonths").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 나무").attributes(example(treeMonths.toString())),
                                fieldWithPath("convertedFootprint.passengerCar").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 자동차").attributes(example(passengerCar.toString())),
                                fieldWithPath("convertedFootprint.flightFromIncheonToLondon").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 비행").attributes(example(flightFromIncheonToLondon.toString()))
                        )
                )
        );
    }

    /**
     * input : full hw spec(cpu(model + usage + corenum) + gpu(model + usage + corenum) + memory + psf)
     * expect result :
     */
    @Test
    public void getGreen_FullHwSpec_Success() throws Exception {
        //given
        String cpuModelName = "testCpuModel";
        Double cpuUsageFactor = 0.8;
        Integer cpuCoreNumber = 8;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .modelName(cpuModelName)
                .usageFactor(cpuUsageFactor)
                .coreNumber(cpuCoreNumber)
                .build();

        String gpuModelName = "testGpuModel";
        Double gpuUsageFactor = 0.7;
        Integer gpuCoreNumber = 7;
        ProcessorSpecRequest gpuSpecRequest = ProcessorSpecRequest.builder()
                .modelName(gpuModelName)
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
        Double expectedTotalCarbonFootprint = 126.78;
        Double expectedCpuCarbonFootprint = 32.01;
        Double expectedGpuCarbonFootprint = 42.51;
        Double memoryCarbonFootprint = 52.26;
        Double energyNeeded = 11.111;
        Double treeMonths = 22.222;
        Double passengerCar = 33.333;
        Double flightFromIncheonToLondon = 44.44;

        HwFootprint hwFootprint = new HwFootprint(Optional.of(expectedCpuCarbonFootprint), Optional.of(expectedGpuCarbonFootprint), memoryCarbonFootprint);
        ConvertedFootprint convertedFootprint = new ConvertedFootprint(energyNeeded, treeMonths, passengerCar, flightFromIncheonToLondon);
        GreenResourceResponse greenResourceResponse = new GreenResourceResponse(expectedTotalCarbonFootprint, hwFootprint, convertedFootprint);

        given(greenService.calculateGreen(greenRequest)).willReturn(greenResourceResponse);

        //when
        ResultActions actions = mockMvc.perform(
                post("/green")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCarbonFootprint").value(expectedTotalCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.hwFootprint.cpuCarbonFootprint").value(expectedCpuCarbonFootprint.toString()))
                .andExpect(jsonPath("$.hwFootprint.gpuCarbonFootprint").value(expectedGpuCarbonFootprint.toString()))
                .andExpect(jsonPath("$.hwFootprint.memoryCarbonFootprint").value(memoryCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.convertedFootprint.energyNeeded").value(energyNeeded.toString()))
                .andExpect(jsonPath("$.convertedFootprint.treeMonths").value(treeMonths.toString()))
                .andExpect(jsonPath("$.convertedFootprint.passengerCar").value(passengerCar.toString()))
                .andExpect(jsonPath("$.convertedFootprint.flightFromIncheonToLondon").value(flightFromIncheonToLondon.toString()));

    }

    /**
     * input : cpu(gpu)만 - model명
     * expect result :
     */
    @Test
    public void getGreen_OnlyCpuModelName_ReturnOnlyCpuAndMemory() throws Exception {
        //given
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
        Double expectedTotalCarbonFootprint = 126.78;
        Double expectedCpuCarbonFootprint = 32.01;
        Double memoryCarbonFootprint = 52.26;
        Double energyNeeded = 11.111;
        Double treeMonths = 22.222;
        Double passengerCar = 33.333;
        Double flightFromIncheonToLondon = 44.44;

        HwFootprint hwFootprint = new HwFootprint(Optional.of(expectedCpuCarbonFootprint), Optional.empty(), memoryCarbonFootprint);
        ConvertedFootprint convertedFootprint = new ConvertedFootprint(energyNeeded, treeMonths, passengerCar, flightFromIncheonToLondon);
        GreenResourceResponse greenResourceResponse = new GreenResourceResponse(expectedTotalCarbonFootprint, hwFootprint, convertedFootprint);

        given(greenService.calculateGreen(greenRequest)).willReturn(greenResourceResponse);

        //when
        ResultActions actions = mockMvc.perform(
                post("/green")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCarbonFootprint").value(expectedTotalCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.hwFootprint.cpuCarbonFootprint").value(expectedCpuCarbonFootprint.toString()))
                .andExpect(jsonPath("$.hwFootprint.memoryCarbonFootprint").value(memoryCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.convertedFootprint.energyNeeded").value(energyNeeded.toString()))
                .andExpect(jsonPath("$.convertedFootprint.treeMonths").value(treeMonths.toString()))
                .andExpect(jsonPath("$.convertedFootprint.passengerCar").value(passengerCar.toString()))
                .andExpect(jsonPath("$.convertedFootprint.flightFromIncheonToLondon").value(flightFromIncheonToLondon.toString()));

        actions.andDo(
                document(
                        "/green/normal/no-gpu/model",
                        requestFields(
                                fieldWithPath("javaCode").type(JsonFieldType.STRING).description("실행할 자바 코드").attributes(example(javaCode)),

                                fieldWithPath("hwSpecRequest.cpuSpecRequest.modelName").type(JsonFieldType.STRING).description("Processor(CPU or GPU) model 이름").optional().attributes(example("Intel Core i7-14700K")),
                                fieldWithPath("hwSpecRequest.cpuSpecRequest.usageFactor").type(JsonFieldType.NUMBER).description("Processor 코어 한개 가용률(기본값 1, 최소값 0, 최대값 1)").optional().attributes(example("0.8")),
                                fieldWithPath("hwSpecRequest.cpuSpecRequest.coreNumber").type(JsonFieldType.NUMBER).description("활용 가능한 코어 개수(최소값 1)").attributes(example("8")),
                                fieldWithPath("hwSpecRequest.cpuSpecRequest.tdp").type(JsonFieldType.NULL).description("").attributes(example("")).optional(),

                                fieldWithPath("hwSpecRequest.gpuSpecRequest").type(JsonFieldType.NULL).description("").attributes(example("")).optional(),

                                fieldWithPath("hwSpecRequest.memoryGigaByte").type(JsonFieldType.NUMBER).description("사용 가능 메모리 양(최소값 1)").attributes(example("4")),
                                fieldWithPath("hwSpecRequest.psf").type(JsonFieldType.NUMBER).description("프라그마 스케일링 팩터(기본값 1, 최소값 1)").optional().attributes(example("1.5"))
                        ),
                        responseFields(
                                fieldWithPath("totalCarbonFootprint").type(JsonFieldType.NUMBER).description("총 탄소 배출량(세부 탄소 배출량 총합)").attributes(example(expectedTotalCarbonFootprint.toString())),

                                fieldWithPath("hwFootprint.cpuCarbonFootprint").type(JsonFieldType.NUMBER).description("탄소 배출량 - Cpu(입력시)").attributes(example(expectedCpuCarbonFootprint.toString())),
                                fieldWithPath("hwFootprint.gpuCarbonFootprint").type(JsonFieldType.NULL).description("").attributes(example("")).optional(),
                                fieldWithPath("hwFootprint.memoryCarbonFootprint").type(JsonFieldType.NUMBER).description("탄소 배출량 - memory").attributes(example(memoryCarbonFootprint.toString())),

                                fieldWithPath("convertedFootprint.energyNeeded").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 에너지").attributes(example(energyNeeded.toString())),
                                fieldWithPath("convertedFootprint.treeMonths").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 나무").attributes(example(treeMonths.toString())),
                                fieldWithPath("convertedFootprint.passengerCar").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 자동차").attributes(example(passengerCar.toString())),
                                fieldWithPath("convertedFootprint.flightFromIncheonToLondon").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 비행").attributes(example(flightFromIncheonToLondon.toString()))
                        )
                )
        );
    }

    /**
     * input : cpu(gpu)만 - Tdp
     * expect result :
     */
    @Test
    public void getGreen_OnlyCpuTdp_ReturnOnlyCpuAndMemory() throws Exception {
        //given
        Double tdp = 25.0;
        Double cpuUsageFactor = 0.8;
        Integer cpuCoreNumber = 8;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .tdp(tdp)
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
        Double expectedTotalCarbonFootprint = 126.78;
        Double expectedCpuCarbonFootprint = 32.01;
        Double memoryCarbonFootprint = 52.26;
        Double energyNeeded = 11.111;
        Double treeMonths = 22.222;
        Double passengerCar = 33.333;
        Double flightFromIncheonToLondon = 44.44;

        HwFootprint hwFootprint = new HwFootprint(Optional.of(expectedCpuCarbonFootprint), Optional.empty(), memoryCarbonFootprint);
        ConvertedFootprint convertedFootprint = new ConvertedFootprint(energyNeeded, treeMonths, passengerCar, flightFromIncheonToLondon);
        GreenResourceResponse greenResourceResponse = new GreenResourceResponse(expectedTotalCarbonFootprint, hwFootprint, convertedFootprint);

        given(greenService.calculateGreen(greenRequest)).willReturn(greenResourceResponse);

        //when
        ResultActions actions = mockMvc.perform(
                post("/green")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCarbonFootprint").value(expectedTotalCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.hwFootprint.cpuCarbonFootprint").value(expectedCpuCarbonFootprint.toString()))
                .andExpect(jsonPath("$.hwFootprint.memoryCarbonFootprint").value(memoryCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.convertedFootprint.energyNeeded").value(energyNeeded.toString()))
                .andExpect(jsonPath("$.convertedFootprint.treeMonths").value(treeMonths.toString()))
                .andExpect(jsonPath("$.convertedFootprint.passengerCar").value(passengerCar.toString()))
                .andExpect(jsonPath("$.convertedFootprint.flightFromIncheonToLondon").value(flightFromIncheonToLondon.toString()));

        actions.andDo(
                document(
                        "/green/normal/no-gpu/tdp",
                        requestFields(
                                fieldWithPath("javaCode").type(JsonFieldType.STRING).description("실행할 자바 코드").attributes(example(javaCode)),

                                fieldWithPath("hwSpecRequest.cpuSpecRequest.modelName").type(JsonFieldType.NULL).description("").attributes(example("")).optional(),
                                fieldWithPath("hwSpecRequest.cpuSpecRequest.tdp").type(JsonFieldType.NUMBER).description("Processor 코어 한개의 설계 전력(최소값 1").optional().attributes(example(tdp.toString())),
                                fieldWithPath("hwSpecRequest.cpuSpecRequest.usageFactor").type(JsonFieldType.NUMBER).description("Processor 코어 한개 가용률(기본값 1, 최소값 0, 최대값 1)").optional().attributes(example(cpuUsageFactor.toString())),
                                fieldWithPath("hwSpecRequest.cpuSpecRequest.coreNumber").type(JsonFieldType.NUMBER).description("활용 가능한 코어 개수(최소값 1)").attributes(example(cpuCoreNumber.toString())),

                                fieldWithPath("hwSpecRequest.gpuSpecRequest").type(JsonFieldType.NULL).description("").attributes(example("")).optional(),

                                fieldWithPath("hwSpecRequest.memoryGigaByte").type(JsonFieldType.NUMBER).description("사용 가능 메모리 양(최소값 1)").attributes(example("4")),
                                fieldWithPath("hwSpecRequest.psf").type(JsonFieldType.NUMBER).description("프라그마 스케일링 팩터(기본값 1, 최소값 1)").optional().attributes(example("1.5"))
                        ),
                        responseFields(
                                fieldWithPath("totalCarbonFootprint").type(JsonFieldType.NUMBER).description("총 탄소 배출량(세부 탄소 배출량 총합)").attributes(example(expectedTotalCarbonFootprint.toString())),

                                fieldWithPath("hwFootprint.cpuCarbonFootprint").type(JsonFieldType.NUMBER).description("탄소 배출량 - Cpu(입력시)").attributes(example(expectedCpuCarbonFootprint.toString())),
                                fieldWithPath("hwFootprint.gpuCarbonFootprint").type(JsonFieldType.NULL).description("").attributes(example("")).optional(),
                                fieldWithPath("hwFootprint.memoryCarbonFootprint").type(JsonFieldType.NUMBER).description("탄소 배출량 - memory").attributes(example(memoryCarbonFootprint.toString())),

                                fieldWithPath("convertedFootprint.energyNeeded").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 에너지").attributes(example(energyNeeded.toString())),
                                fieldWithPath("convertedFootprint.treeMonths").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 나무").attributes(example(treeMonths.toString())),
                                fieldWithPath("convertedFootprint.passengerCar").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 자동차").attributes(example(passengerCar.toString())),
                                fieldWithPath("convertedFootprint.flightFromIncheonToLondon").type(JsonFieldType.NUMBER).description("탄소 배출량 변환 - 비행").attributes(example(flightFromIncheonToLondon.toString()))
                        )
                )
        );
    }

    /**
     * input : cpu(gpu)만 - Tdp + No UsageFactor
     * expect result :
     */
    @Test
    public void getGreen_OnlyCpuTdpWithoutUsageFactor_ReturnOnlyCpuAndMemory() throws Exception {
        //given
        Double tdp = 25.0;
        Integer cpuCoreNumber = 8;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .tdp(tdp)
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
        Double expectedTotalCarbonFootprint = 126.78;
        Double expectedCpuCarbonFootprint = 32.01;
        Double memoryCarbonFootprint = 52.26;
        Double energyNeeded = 11.111;
        Double treeMonths = 22.222;
        Double passengerCar = 33.333;
        Double flightFromIncheonToLondon = 44.44;

        HwFootprint hwFootprint = new HwFootprint(Optional.of(expectedCpuCarbonFootprint), Optional.empty(), memoryCarbonFootprint);
        ConvertedFootprint convertedFootprint = new ConvertedFootprint(energyNeeded, treeMonths, passengerCar, flightFromIncheonToLondon);
        GreenResourceResponse greenResourceResponse = new GreenResourceResponse(expectedTotalCarbonFootprint, hwFootprint, convertedFootprint);

        given(greenService.calculateGreen(greenRequest)).willReturn(greenResourceResponse);

        //when
        ResultActions actions = mockMvc.perform(
                post("/green")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCarbonFootprint").value(expectedTotalCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.hwFootprint.cpuCarbonFootprint").value(expectedCpuCarbonFootprint.toString()))
                .andExpect(jsonPath("$.hwFootprint.memoryCarbonFootprint").value(memoryCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.convertedFootprint.energyNeeded").value(energyNeeded.toString()))
                .andExpect(jsonPath("$.convertedFootprint.treeMonths").value(treeMonths.toString()))
                .andExpect(jsonPath("$.convertedFootprint.passengerCar").value(passengerCar.toString()))
                .andExpect(jsonPath("$.convertedFootprint.flightFromIncheonToLondon").value(flightFromIncheonToLondon.toString()));
    }

    /**
     * input : cpu(gpu)만 - Tdp + No Psf
     * expect result :
     */
    @Test
    public void getGreen_WithoutPsf_SuccessWithDefaultPsf() throws Exception {
        //given
        Double tdp = 25.0;
        Double cpuUsageFactor = 0.8;
        Integer cpuCoreNumber = 8;
        ProcessorSpecRequest cpuSpecRequest = ProcessorSpecRequest.builder()
                .tdp(tdp)
                .usageFactor(cpuUsageFactor)
                .coreNumber(cpuCoreNumber)
                .build();

        Integer memory = 8;
        HwSpecRequest hwSpecRequest = new HwSpecRequest(cpuSpecRequest, null, memory, null);

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
        Double expectedTotalCarbonFootprint = 126.78;
        Double expectedCpuCarbonFootprint = 32.01;
        Double memoryCarbonFootprint = 52.26;
        Double energyNeeded = 11.111;
        Double treeMonths = 22.222;
        Double passengerCar = 33.333;
        Double flightFromIncheonToLondon = 44.44;

        HwFootprint hwFootprint = new HwFootprint(Optional.of(expectedCpuCarbonFootprint), Optional.empty(), memoryCarbonFootprint);
        ConvertedFootprint convertedFootprint = new ConvertedFootprint(energyNeeded, treeMonths, passengerCar, flightFromIncheonToLondon);
        GreenResourceResponse greenResourceResponse = new GreenResourceResponse(expectedTotalCarbonFootprint, hwFootprint, convertedFootprint);

        given(greenService.calculateGreen(greenRequest)).willReturn(greenResourceResponse);

        //when
        ResultActions actions = mockMvc.perform(
                post("/green")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCarbonFootprint").value(expectedTotalCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.hwFootprint.cpuCarbonFootprint").value(expectedCpuCarbonFootprint.toString()))
                .andExpect(jsonPath("$.hwFootprint.memoryCarbonFootprint").value(memoryCarbonFootprint.toString()));

        actions.andExpect(jsonPath("$.convertedFootprint.energyNeeded").value(energyNeeded.toString()))
                .andExpect(jsonPath("$.convertedFootprint.treeMonths").value(treeMonths.toString()))
                .andExpect(jsonPath("$.convertedFootprint.passengerCar").value(passengerCar.toString()))
                .andExpect(jsonPath("$.convertedFootprint.flightFromIncheonToLondon").value(flightFromIncheonToLondon.toString()));
    }


}
