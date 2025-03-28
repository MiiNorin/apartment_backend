package com.example.apartmentmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "form")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formId;

    private String formType; // Loại đơn từ (ví dụ: "Đơn xin sửa chữa")
    private String fileUrl;  // URL file trên Cloudinary
    private String fileName; // Tên file gốc
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
