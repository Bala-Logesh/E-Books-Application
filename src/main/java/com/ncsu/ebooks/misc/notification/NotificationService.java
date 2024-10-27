package com.ncsu.ebooks.misc.notification;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationModel> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public NotificationModel getNotificationById(int id) {
        return notificationRepository.findById(id);
    }

    public List<NotificationModel> getNotificationByUserId(int userId) {
        return notificationRepository.findByUserId(userId);
    }

    public void createNotification(NotificationModel notification) {
        notificationRepository.save(notification);
    }

    public void updateNotification(int id, NotificationModel notification) {
        notification.setNotificationId(id);
        notificationRepository.update(id, notification);
    }

    public void deleteNotification(int id) {
        notificationRepository.delete(id);
    }
}
