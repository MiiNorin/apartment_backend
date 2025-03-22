package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.PaymentHistoryResponseDTO;
import com.example.apartmentmanagement.service.BillService;
import com.example.apartmentmanagement.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

import java.util.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
  private final PayOS payOS;

  public PaymentController(PayOS payOS) {
    super();
    this.payOS = payOS;
  }

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private BillService billService;

  @PostMapping(path = "/payos_transfer_handler")
  public ObjectNode payosTransferHandler(@RequestBody ObjectNode body)
          throws JsonProcessingException, IllegalArgumentException {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode response = objectMapper.createObjectNode();
    Webhook webhookBody = objectMapper.treeToValue(body, Webhook.class);

    try {
      // Xác minh dữ liệu từ PayOS
      WebhookData data = payOS.verifyPaymentWebhookData(webhookBody);

      // Sau khi xử lý thành công, trả về phản hồi OK
      response.put("error", 0);
      response.put("message", "Webhook delivered");
      response.set("data", null);

      return response;
    } catch (Exception e) {
      e.printStackTrace();
      response.put("error", -1);
      response.put("message", e.getMessage());
      response.set("data", null);
      return response;
    }
  }

  @PostMapping("/success")
  public ResponseEntity<Object> paymentSuccess(@RequestBody Map<String, String> payload) {
    try {
      Long billId = Long.parseLong(payload.get("billId"));
      String description = payload.get("description");
      billService.processPaymentSuccess(billId, description);
      return ResponseEntity.ok("Thanh toán thành công");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @GetMapping("/history/{userId}")
  public ResponseEntity<Object> paymentHistory(@RequestParam int month, @RequestParam int year,
                                                   @PathVariable Long userId) {
    Map<String, Object> response = new HashMap<>();
    List<PaymentHistoryResponseDTO> paymentHistoryResponseDTOS = paymentService.getPaymentHistory(userId, month, year);
    if (!paymentHistoryResponseDTOS.isEmpty()) {
      response.put("data", paymentHistoryResponseDTOS);
      response.put("status", HttpStatus.OK.value());
      return ResponseEntity.ok(response);
    } else {
      response.put("status", HttpStatus.NOT_FOUND.value());
      response.put("message", "Không có dữ liệu");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }
}
