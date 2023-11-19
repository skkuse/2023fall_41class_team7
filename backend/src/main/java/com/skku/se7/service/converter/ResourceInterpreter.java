package com.skku.se7.service.converter;

import com.skku.se7.dto.InterpretedFootprint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceInterpreter {
    //https://www.carbonfootprint.com/calculator.aspx
    public InterpretedFootprint interpretCarbonEmission(Double carbonEmission) {
        Double treeMonths = interpretAsTreeMonths(carbonEmission);
        Double passengerCar = interpretAsPassengerCar(carbonEmission);
        Double flightFromIncheonToTokyo = interpretAsFlightFromIncheonToTokyo(carbonEmission);

        return InterpretedFootprint.builder()
                .treeMonths(treeMonths)
                .passengerCar(passengerCar)
                .flightFromIncheonToTokyo(flightFromIncheonToTokyo)
                .build();
    }

    private Double interpretAsFlightFromIncheonToTokyo(Double carbonEmission) {
        return carbonEmission / 210000 * 100;
    }

    private Double interpretAsPassengerCar(Double carbonEmission) {
        return carbonEmission * 0.00600096015;
    }

    private Double interpretAsTreeMonths(Double carbonEmission) {
        return carbonEmission / 917;
    }
}
