package com.skku.se7.controller;

import com.skku.se7.service.GreenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(controllers = GreenController.class)
class GreenControllerTest {
    @MockBean
    private GreenService greenService;
    
    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_WithoutRequiredFields_BindException() throws Exception{
        //given
        
        //mocking
        
        //when
        
        //then
        
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_OutOfRangeFields_BindException() throws Exception{
        //given

        //mocking

        //when

        //then

    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_NoCpuNoGpu_WithoutProcessorException() throws Exception{
        //given

        //mocking

        //when

        //then

    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_InputTdpWithModelName_TdpWithModelNameException() throws Exception{
        //given

        //mocking

        //when

        //then

    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_UnmatchedCpuModelName_NoMatchCpuModelNameException() throws Exception{
        //given

        //mocking

        //when

        //then

    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_UnmatchedGpuModelName_NoMatchGpuModelNameException() throws Exception{
        //given

        //mocking

        //when

        //then

    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_InputPueWithSomeCloudService_PueWithCloudServiceException() throws Exception{
        //given

        //mocking

        //when

        //then

    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_InvalidRegionWithContinent_ContinentNotContainRegionException() throws Exception{
        //given

        //mocking

        //when

        //then

    }

}