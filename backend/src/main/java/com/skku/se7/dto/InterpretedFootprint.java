package com.skku.se7.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Builder
@NoArgsConstructor
public class InterpretedFootprint {
    private Double treeMonths;
    private Double passengerCar;
    private Double flightFromIncheonToTokyo;

    public InterpretedFootprint(Double treeMonths, Double passengerCar, Double flightFromIncheonToTokyo) {
        this.treeMonths = treeMonths;
        this.passengerCar = passengerCar;
        this.flightFromIncheonToTokyo = flightFromIncheonToTokyo;
    }
}
