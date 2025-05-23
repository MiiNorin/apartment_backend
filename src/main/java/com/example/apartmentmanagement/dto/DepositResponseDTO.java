package com.example.apartmentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponseDTO {
    private Long depositUserId;
    private Long postId;
    private Float depositPrice;
    private Long depositId;
    private String status;
}
