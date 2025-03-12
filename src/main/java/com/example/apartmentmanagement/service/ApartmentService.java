package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.ApartmentDTO;
import com.example.apartmentmanagement.entities.Apartment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApartmentService {

    String addApartment(Apartment apartment);

    String addResidentIntoApartment(Apartment apartment, Long userId, MultipartFile imageFile);

    String checkApartmentExisted(Apartment apartment);

    List<ApartmentDTO> showApartment();

    Apartment getApartmentById (Long id);

    void updateApartment (Apartment existedApartment, Apartment apartment, MultipartFile imageFile);

    void deleteApartment (Long id);

    List<ApartmentDTO> getApartmentByName (String name);

    List<ApartmentDTO> totalUnrentedApartment ();
}
