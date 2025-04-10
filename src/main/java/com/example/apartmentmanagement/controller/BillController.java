package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.BillResponseDTO;
import com.example.apartmentmanagement.dto.BillRequestDTO;
import com.example.apartmentmanagement.entities.Bill;
import com.example.apartmentmanagement.entities.User;
import com.example.apartmentmanagement.repository.UserRepository;
import com.example.apartmentmanagement.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/viewAll")
    public ResponseEntity<Object> getAllBills() {
        List<BillResponseDTO> billResponseDTOS = billService.getAllBill();
        Map<String, Object> response = new HashMap<>();
        if (billResponseDTOS.isEmpty()) {
            response.put("message", "Chưa có hoá đơn nào");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response.put("data", billResponseDTOS);
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getRentorBills/{rentorId}")
    public ResponseEntity<Object> getRentorBills(@PathVariable Long rentorId) {
        List<BillResponseDTO> billResponseDTOS = billService.viewRentorBills(rentorId);
        Map<String, Object> response = new HashMap<>();
        if (billResponseDTOS.isEmpty()) {
            response.put("message", "Chưa có hoá đơn nào");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response.put("data", billResponseDTOS);
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    /**
     * (Staff) Xem danh sach bill cua user bat ky trong khoang thoi gian cu the
     *
     * @param month
     * @param year
     * @return
     */
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Object> getBill(@RequestParam int month, @RequestParam int year,
                                          @PathVariable Long userId) {
        List<BillResponseDTO> billResponseDTOS = billService.getAllBillsWithinSpecTime(userId, month, year);
        Map<String, Object> response = new HashMap<>();
        if (billResponseDTOS.isEmpty()) {
            response.put("message", "Chưa thanh toán hoá đơn nào");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response.put("data", billResponseDTOS);
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    /**
     * (User) Xem danh sach bill cua ban than trong khoang thoi gian cu the
     *
     * @param month
     * @param year
     * @param userId
     * @return
     */
    @GetMapping("/view_bill_list/{userId}")
    public ResponseEntity<Object> getOwnBillListWithinSpecTime(@RequestParam int month, @RequestParam int year, @PathVariable Long userId) {
        User user = userRepository.findById(userId).get();
        List<BillResponseDTO> billResponseDTOS = billService.viewBillListWithinSpecTime(month, year, user.getUserId());
        Map<String, Object> response = new HashMap<>();
        if (billResponseDTOS.isEmpty()) {
            response.put("message", "Chưa thanh toán hoá đơn nào");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response.put("data", billResponseDTOS);
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/view_own_bill_list/{userId}")
    public ResponseEntity<Object> getOwnBillList(@PathVariable Long userId) {
        User user = userRepository.findById(userId).get();
        List<BillResponseDTO> billResponseDTOS = billService.viewBillList(user.getUserId());
        Map<String, Object> response = new HashMap<>();
        if (billResponseDTOS.isEmpty()) {
            response.put("message", "Chưa có hóa đơn nào");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response.put("data", billResponseDTOS);
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    /**
     * (Staff) Tao hoa don cho owner or rentor
     *
     * @param request
     * @return
     */
    @PostMapping("/createBillConsumption")
    public ResponseEntity<Object> createBillConsumption(@RequestBody BillRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        try {
            BillResponseDTO result = billService.addBillConsumption(request);
            response.put("status", HttpStatus.CREATED.value());
            response.put("data", result);
            response.put("message", "Tạo hoá đơn thành công");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * (Owner) Tao hoa don cho rentor
     *
     * @param request
     * @return
     */
    @PostMapping("/createBillMonthPaid")
    public ResponseEntity<Object> createBillMonthPaid(@RequestBody BillRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        try {
            BillResponseDTO result = billService.addBillMonthPaid(request);
            response.put("status", HttpStatus.CREATED.value());
            response.put("data", result);
            response.put("message", "Tạo hoá đơn thành công");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * (Owner) Tao hoa don cho rentor
     *
     * @param request
     * @return
     */
    @PostMapping("/create_for_rentor")
    public ResponseEntity<Object> createBillForRentor(@RequestBody BillRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        try {
            BillResponseDTO result = billService.sendBillToRenter(request);
            response.put("status", HttpStatus.CREATED.value());
            response.put("data", result);
            response.put("message", "Tạo hoá đơn thành công");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @GetMapping("/get_bill_info/{billId}")
    public ResponseEntity<Object> getBillInfo(@PathVariable Long billId) {
        BillResponseDTO billResponseDTO = billService.getBillById(billId);
        Map<String, Object> response = new HashMap<>();
        if (billResponseDTO == null) {
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Không tìm thấy hoá đơn này");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("status", HttpStatus.OK.value());
            response.put("data", billResponseDTO);
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/update/{billid}")
    public ResponseEntity<Object> updateBill(@PathVariable Long billid, @RequestBody BillRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        try {
            BillResponseDTO billResponseDTO = billService.updateBill(billid, request);
            response.put("status", HttpStatus.CREATED.value());
            response.put("data", billResponseDTO);
            response.put("message", "Update bill thành công");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    /**
     * (User) xoá hoá đơn đã thanh toán
     *
     * @param billId
     * @return
     */
    @DeleteMapping("/delete/{billId}")
    public ResponseEntity<Object> deleteBill(@PathVariable Long billId) {
        Map<String, Object> response = new HashMap<>();
        try {
            billService.deleteBill(billId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}