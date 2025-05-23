package com.example.apartmentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddNewResidentResponseDTO {
    private String userName;
    private List<ApartmentResponseDTO> apartmentResponseDTOS;
    private String fullName;
    private String role;
    private boolean status;
}
