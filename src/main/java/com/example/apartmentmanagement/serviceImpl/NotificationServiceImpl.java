package com.example.apartmentmanagement.serviceImpl;

import com.example.apartmentmanagement.dto.NotificationDTO;
import com.example.apartmentmanagement.entities.Notification;
import com.example.apartmentmanagement.entities.User;
import com.example.apartmentmanagement.repository.NotificationRepository;
import com.example.apartmentmanagement.repository.UserRepository;
import com.example.apartmentmanagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<NotificationDTO> getNotifications(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user.getNotifications().stream().map(notification -> new NotificationDTO(
                notification.getNotificationId(),
                user.getUserName(),
                notification.getNotificationContent(),
                notification.isNotificationCheck(),
                notification.getNotificationDate(),
                notification.getNotificationType()
        )).collect(Collectors.toList());
    }

    @Override
    public String createNotification(String notificationContent, String notiType, Long userId) {
        Notification newNotification = new Notification();
        newNotification.setNotificationContent(notificationContent);
        User user = userRepository.findById(userId).get();
        newNotification.setNotificationType(notiType);
        newNotification.setNotificationDate(LocalDateTime.now());
        newNotification.setNotificationCheck(false);
        newNotification.setUser(user);
        notificationRepository.save(newNotification);
        return "done";
    }

    @Override
    public String deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
        return "done";
    }

    @Override
    public Notification getNotification(Long notificationId) {
        return notificationRepository.findById(notificationId).orElse(null);
    }

}
