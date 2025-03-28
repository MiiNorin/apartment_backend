package com.example.apartmentmanagement.entities;

/***
 * Entity notification: thong bao den cho user
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Nationalized
    private String notificationType;

    @Nationalized
    private String notificationContent;

    private LocalDateTime notificationDate;

    /**
     * @param notificationCheck: flag kiem tra notification da duoc ben phia user nhan hay chua
     */
    private boolean notificationCheck;

    @ManyToOne
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;

}
