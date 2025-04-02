package com.example.apartmentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionResponseDTO {
    private Long id;
    private LocalDate consumptionDate;
    private float lastMonthWaterConsumption;
    private float waterConsumption;
    private String userName;
    private String apartmentName;
    private boolean isBillCreated;

    public ConsumptionResponseDTO(Long id, LocalDate consumptionDate, float waterConsumption, float lastMonthWaterConsumption, String apartmentName) {
        this.id = id;
        this.consumptionDate = consumptionDate;
        this.waterConsumption = waterConsumption;
        this.lastMonthWaterConsumption = lastMonthWaterConsumption;
        this.apartmentName = apartmentName;
    }
}
