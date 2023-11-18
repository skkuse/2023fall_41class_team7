package com.skku.se7.controller;

import com.skku.se7.controller.validator.EnvironmentValidator;
import com.skku.se7.controller.validator.HwValidator;
import com.skku.se7.service.GreenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import({HwValidator.class, EnvironmentValidator.class})
@WebMvcTest(controllers = GreenController.class)
class GreenControllerExceptionTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GreenService greenService;
    @Autowired
    private HwValidator hwValidator;
    @Autowired
    private EnvironmentValidator environmentValidator;
    
    /**
     * input :
     * expect result :
     */
    @Test
    public void getGreen_WithoutRequiredFields_MissingServletRequestParameterException() throws Exception{
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

}