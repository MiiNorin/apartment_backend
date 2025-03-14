package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.CreateNewAccountDTO;
import com.example.apartmentmanagement.dto.UserDTO;
import com.example.apartmentmanagement.dto.VerifyUserRequestDTO;
import com.example.apartmentmanagement.dto.VerifyUserResponseDTO;
import com.example.apartmentmanagement.entities.User;
import com.example.apartmentmanagement.entities.VerificationForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<UserDTO> showAllUser();

    CreateNewAccountDTO addUser(CreateNewAccountDTO newAccountDTO);

    boolean checkUserExisted(User user);

    User getUserById (Long id);

    UserDTO getUserDTOById (Long id);

    boolean updateImage(User user, MultipartFile imageFile);

    String updateUser(UserDTO userDTO, User user);

    User getUserByName(String name);

    List<UserDTO> getUserByFullName(String fullName);

    String deleteUserById(Long id);

    User getUserByEmailOrUserName(String email);

    VerifyUserResponseDTO verifyUser(VerifyUserRequestDTO verifyUserDTO, List<MultipartFile> imageFile);

}
