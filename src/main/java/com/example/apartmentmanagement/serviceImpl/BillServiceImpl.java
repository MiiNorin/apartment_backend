package com.example.apartmentmanagement.serviceImpl;

import com.example.apartmentmanagement.dto.BillDTO;
import com.example.apartmentmanagement.dto.BillRequestDTO;
import com.example.apartmentmanagement.entities.Apartment;
import com.example.apartmentmanagement.entities.Bill;
import com.example.apartmentmanagement.entities.User;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.BillRepository;
import com.example.apartmentmanagement.repository.UserRepository;
import com.example.apartmentmanagement.service.BillService;
import com.example.apartmentmanagement.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public List<BillDTO> getAllBillsWithinSpecTime(Long userId, int month, int year) {
        User user = userRepository.findById(userId).get();
        List<Bill> bills = user.getBills();

        return bills.stream()
                .filter(bill -> bill.getBillDate().getMonthValue() == month && bill.getBillDate().getYear() == year)
                .map(bill -> new BillDTO(
                        bill.getBillId(),
                        bill.getBillContent(),
                        bill.getElectricBill(),
                        bill.getWaterBill(),
                        bill.getOthers(),
                        bill.getTotal(),
                        bill.getBillDate(),
                        bill.getStatus(),
                        bill.getUser().getFullName(),
                        bill.getApartment().getApartmentName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public BillDTO getBillById(Long id) {
        Bill bill = billRepository.findById(id).get();
        BillDTO billDTO = new BillDTO(
                bill.getBillId(),
                bill.getBillContent(),
                bill.getElectricBill(),
                bill.getWaterBill(),
                bill.getOthers(),
                bill.getTotal(),
                bill.getBillDate(),
                bill.getStatus(),
                bill.getUser().getUserName(),
                bill.getApartment().getApartmentName()
        );
        return billDTO;
    }

    @Override
    public BillRequestDTO updateBill(Long id, BillRequestDTO billRequestDTO) {
        Bill bill = billRepository.findById(id).get();
        bill.setBillContent(billRequestDTO.getBillContent());
        return null;
    }

    @Override
    public List<BillDTO> viewBillList(int month, int year, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getBills() == null) return List.of();

        return user.getBills().stream()
                .filter(bill -> bill.getBillDate().getMonthValue() == month && bill.getBillDate().getYear() == year)
                .map(bill -> new BillDTO(
                        bill.getBillId(),
                        bill.getBillContent(),
                        bill.getElectricBill(),
                        bill.getWaterBill(),
                        bill.getOthers(),
                        bill.getTotal(),
                        bill.getBillDate(),
                        bill.getStatus(),
                        user.getFullName(),
                        bill.getApartment().getApartmentName()
                ))
                .collect(Collectors.toList());
    }


//    @Override
//    public BillDTO updateBill(Bill bill) {
//        BillDTO billDTO =  new BillDTO();
//        return billDTO;
//    }

    @Override
    public void deleteBill(Long id) {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy bill này"));
        if (bill.getStatus().equals("unpayed")) {
            throw new RuntimeException("Phải thanh toán hoá đơn trước");
        } else {
            billRepository.delete(bill);
        }
    }

    @Override
    public String addBill(String billContent, String name, int electricCons, int waterCons, float otherCost) {
        System.out.println("bill content" + billContent);
        User user = userRepository.findByUserName(name);

        Apartment apartment = user.getApartment();
        Bill newBill = new Bill();

        float electricCost = calculateElectricBill(electricCons);
        newBill.setElectricBill(electricCost);
        newBill.setBillContent(billContent);

        float waterCost = calculateWaterBill(waterCons);
        newBill.setWaterBill(waterCost);
        newBill.setOthers(otherCost);
        newBill.setTotal(electricCost + waterCost + otherCost);

        newBill.setBillDate(LocalDateTime.now());
        newBill.setStatus("unpayed");

        newBill.setUser(user);
        newBill.setApartment(apartment);

        String notificationContent = "Thông báo hóa đơn mới";

        notificationService.createNotification(notificationContent, "1", user.getUserId());

        billRepository.save(newBill);
        return "success";
    }

    private float calculateElectricBill(int consumption) {
        float total = 0;
        int remaining = consumption;

        int[] thresholds = {50, 50, 100, 100, 100};
        float[] rates = {1.678F, 1.734F, 2.014F, 2.536F, 2.834F, 2.927F};

        for (int i = 0; i < thresholds.length; i++) {
            if (remaining > thresholds[i]) {
                total += thresholds[i] * rates[i];
                remaining -= thresholds[i];
            } else {
                total += remaining * rates[i];
                return total;
            }
        }
        total += remaining * rates[rates.length - 1];
        return total;
    }

    private float calculateWaterBill(int consumption) {
        float total = 0;
        int remaining = consumption;

        int[] thresholds = {10, 10, 10}; // Bậc 1: 10m³, Bậc 2: 10m³, Bậc 3: 10m³
        float[] rates = {5.973F, 7.052F, 8.669F, 15.929F}; // Giá từng bậc

        for (int i = 0; i < thresholds.length; i++) {
            if (remaining > thresholds[i]) {
                total += thresholds[i] * rates[i];
                remaining -= thresholds[i];
            } else {
                total += remaining * rates[i];
                return total;
            }
        }
        total += remaining * rates[rates.length - 1];
        return total;
    }

}
