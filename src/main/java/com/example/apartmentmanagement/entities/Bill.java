package com.example.apartmentmanagement.entities;

/***
 * Entity bill: hoa don, chi phi
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @Nationalized
    private String billContent;

    private String billType;

    private float amount;

    private String period;

    private LocalDateTime billDate;

    private Long createBillUserId;

    private Float surcharge;

    @Nationalized
    private String status;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "paymentId")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "consumption_id")
    private Consumption consumption;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;
}
