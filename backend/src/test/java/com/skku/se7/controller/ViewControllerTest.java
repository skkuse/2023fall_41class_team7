package com.skku.se7.controller;

import com.skku.se7.Se7Configuration;
import com.skku.se7.dto.enums.Continent;
import com.skku.se7.service.LocationHandler;
import com.skku.se7.service.ProcessorTdpHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.skku.se7.controller.ControllerTestUtils.example;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(Se7Configuration.class)
@WebMvcTest(controllers = ViewController.class)
class ViewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ViewController viewController;
    @InjectMocks
    private LocationHandler locationHandler;
    @InjectMocks
    private ProcessorTdpHandler processorTdpHandler;
    
    @Test
    public void getCpuModels_Default_Success() throws Exception{
        //given
        Integer cpuRowNum = 186;
        
        //mocking
        
        //when
        ResultActions actions = mockMvc.perform(
                get("/model/cpu")
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(cpuRowNum));
        for (int i = 0; i < cpuRowNum; i++) {
            actions.andExpect(jsonPath("$.data[" + i + "]").isString());
        }
        actions.andDo(
                document("/view/model/cpu",
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("cpu 모델명 총 개수").attributes(example(cpuRowNum.toString())),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("cpu 모델명").attributes(example("Intel Core i7-14700K"))
                        )
                )
        );
    }
    @Test
    public void getGpuModels_Default_Success() throws Exception{
        //given
        Integer gpuRowNum = 207;

        //mocking

        //when
        ResultActions actions = mockMvc.perform(
                get("/model/gpu")
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(gpuRowNum));
        for (int i = 0; i < gpuRowNum; i++) {
            actions.andExpect(jsonPath("$.data[" + i + "]").isString());
        }
        actions.andDo(
                document("/view/model/gpu",
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("gpu 모델명 총 개수").attributes(example(gpuRowNum.toString())),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("gpu 모델명").attributes(example("WGeForce GT 1030 (GDDR5)"))
                                )
                        )
        );
    }
    @Test
    public void getContinents_Default_Success() throws Exception{
        //given
        List<Continent> continents = List.of(Continent.values());

        //mocking

        //when
        ResultActions actions = mockMvc.perform(
                get("/location")
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(continents.size()));
        for (int i = 0; i < continents.size(); i++) {
            actions.andExpect(jsonPath("$.data[" + i + "]").isString());
        }
        actions.andDo(
                document("/view/location/continent",
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("대륙명 총 개수").attributes(example(String.valueOf(continents.size()))),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("대륙명").attributes(example(continents.get(0).name()))
                                )
                        )
        );
    }
    @Test
    public void getCountries_Default_Success() throws Exception{
        //given
        Continent continent = Continent.NORTH_AMERICA;
        Integer countryNum = 3;

        //mocking

        //when
        ResultActions actions = mockMvc.perform(
                get("/location/{continent}", continent.name())
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(countryNum));
        for (int i = 0; i < countryNum; i++) {
            actions.andExpect(jsonPath("$.data[" + i + "]").isString());
        }
        actions.andDo(
                document("/view/location/country",
                        pathParameters(
                                parameterWithName("continent").description("대륙").attributes(example(continent.name()))
                        ),
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("대륙에 속하는 국가명 총 개수").attributes(example(countryNum.toString())),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("대륙에 속하는 국가명").attributes(example("Brazil"))
                                )
                        )
        );
    }
    @Test
    public void getRegions_Default_Success() throws Exception{
        //given
        Continent continent = Continent.NORTH_AMERICA;
        String country = "Canada";
        Integer regionNum = 14;

        //mocking

        //when
        ResultActions actions = mockMvc.perform(
                get("/location/{continent}/{country}", continent.name(), country)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(regionNum));
        for (int i = 0; i < regionNum; i++) {
            actions.andExpect(jsonPath("$.data[" + i + "]").isString());
        }
        actions.andDo(
                document("/view/location/region",
                        pathParameters(
                                parameterWithName("continent").description("대륙").attributes(example(continent.name())),
                                parameterWithName("country").description("대륙에 속하는 국가").attributes(example(country))
                        ),
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("대륙명 총 개수").attributes(example(String.valueOf(regionNum))),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("대륙명").attributes(example("Any"))
                                )
                        )
        );
    }
    @Test
    public void getRegions_BadContinent_MethodArgumentTypeMismatchException() throws Exception{
        //given
        String continent = "badContinent";
        String country = "Canada";

        //mocking

        //when
        MvcResult badContinentResult = mockMvc.perform(
                        get("/location/{continent}/{country}", continent, country)
                ).andExpect(status().isBadRequest())
                .andDo(
                        document("/view/exception/badContinent")
                )
                .andReturn();

        //then
        assertThat(badContinentResult.getResolvedException()).isExactlyInstanceOf(MethodArgumentTypeMismatchException.class);
    }
}