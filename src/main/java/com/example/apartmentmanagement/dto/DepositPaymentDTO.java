package com.example.apartmentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositPaymentDTO {
    private Long depositUserId;
    private Long postId;
    private float depositPrice;
    private Long depositId;
}
