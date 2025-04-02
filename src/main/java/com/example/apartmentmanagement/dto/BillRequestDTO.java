package com.example.apartmentmanagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillRequestDTO {
    private String userName;
    private String billContent;
    private float monthlyPaid;
    private float managementFee;
    private float lastMonthWaterCons;
    private float waterCons;
    private float others;
    private Long consumptionId;
    private Long createdUserId;

    public BillRequestDTO(String userName, String billContent, float managementFee, float lastMonthWaterCons, float waterCons, float others, Long consumptionId, Long createdUserId) {
        this.userName = userName;
        this.billContent = billContent;
        this.managementFee = managementFee;
        this.lastMonthWaterCons = lastMonthWaterCons;
        this.waterCons = waterCons;
        this.others = others;
        this.consumptionId = consumptionId;
        this.createdUserId = createdUserId;
    }
}
