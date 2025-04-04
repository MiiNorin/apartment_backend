package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.BankAccountQRResponseDTO;
import com.example.apartmentmanagement.dto.BankAccountRequestDTO;
import com.example.apartmentmanagement.service.BankAccountService;
import com.example.apartmentmanagement.serviceImpl.BankAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank-account")
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/add")
    public ResponseEntity<BankAccountQRResponseDTO> addBankAccount(
            @RequestBody BankAccountRequestDTO requestDTO) {
        // Thêm tài khoản và sinh QR
        BankAccountQRResponseDTO response = bankAccountService.addBankAccountWithQR(requestDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/qr_generate")
    public ResponseEntity<BankAccountQRResponseDTO> generateQRCode(@RequestBody BankAccountRequestDTO requestDTO) {
        BankAccountQRResponseDTO response = bankAccountService.generateQR(requestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/webhook")
    public String receiveWebhook(@RequestBody String payload) {
        // In ra dữ liệu nhận được từ webhook
        System.out.println("Webhook đã nhận dữ liệu: " + payload);

        // In ra thông báo "gọi webhook thành công"
        System.out.println("Gọi webhook thành công");

        // Trả về phản hồi cho Make
        return "Webhook nhận thành công";
    }
}