package com.example.apartmentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositListResponseDTO {
    private Long depositId;
    private String status;

    private Long postOwnerId;
    private String postOwnerName;

    private Long depositUserId;
    private String depositUserName;

    private Long paymentId;
    private LocalDateTime paymentDate;
    private String paymentInfo;
    private String depositPrice;

    private String apartmentName;
}