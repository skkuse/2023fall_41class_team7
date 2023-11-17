package com.skku.se7.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvertedFootprint {
    private Double energyNeeded;
    private Double treeMonths;
    private Double passengerCar;
    private Double flightFromIncheonToLondon;
}
