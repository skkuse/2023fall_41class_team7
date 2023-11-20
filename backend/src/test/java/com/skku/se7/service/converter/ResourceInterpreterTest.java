package com.skku.se7.service.converter;

import com.skku.se7.dto.InterpretedFootprint;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ResourceInterpreterTest {
    @InjectMocks
    private ResourceInterpreter resourceInterpreter;

    /**
     * input : 복잡한 소수
     * expect result : 소수 세자리수로 반올림
     */
    @Test
    public void interpretCarbonEmission_Default_PrecisionScaled() throws Exception {
        //given
        Double carbonEmission = 8.0 / 3;
        int scailing = 1000;

        //when
        InterpretedFootprint interpretedFootprint = resourceInterpreter.interpretCarbonEmission(carbonEmission);

        //then
        assertThat(interpretedFootprint).isNotNull();

        Double passengerCar = interpretedFootprint.getPassengerCar() * scailing;
        assertThat(passengerCar).isEqualTo(passengerCar.intValue());

        Double treeMonths = interpretedFootprint.getTreeMonths() * scailing;
        assertThat(treeMonths).isEqualTo(treeMonths.intValue());

        Double flight = interpretedFootprint.getFlightFromIncheonToTokyo() * scailing;
        assertThat(flight).isEqualTo(flight.intValue());
    }
}