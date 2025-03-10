package com.example.apartmentmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "verification_form")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_form_id")
    private Long verificationFormId;

    private String verificationFormName;

    private String fullName;

    private String email;

    private String phoneNumber;

    private LocalDateTime contractStartDate;

    private LocalDateTime contractEndDate;

    @OneToOne(mappedBy = "verificationForm", cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "verificationForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractImages> contractImages;
}
