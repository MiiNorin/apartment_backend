package com.example.apartmentmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Entity deposit: luu du lieu dat coc
 */

@Entity
@Table(name = "deposit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositId;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

    private String status;

    @OneToOne
    @JoinColumn(name = "apartment_id", referencedColumnName = "apartment_id")
    private Apartment apartment;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "paymentId")
    private Payment payment;
}
