package com.example.apartmentmanagement.entities;

/***
 * Entity facility: dich vu, tien ich tu ben thu ba
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name = "facility")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    @Nationalized
    private String facilityType;

    /**
     * @param issuanceDate: ngay ky hop dong
     */
    private LocalDate startDate;

    /**
     * @param expirationDate: ngay het han
     */
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;
}
