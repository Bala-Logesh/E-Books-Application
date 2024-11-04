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

    public List<NotificationModel> getNotificationByUserId(String userID) {
        return notificationRepository.findByUserId(userID);
    }

    public void createNotification(NotificationModel notification) {
        notificationRepository.save(notification.getUserID(), notification.getMessage());
    }

    public void updateNotification(int id, NotificationModel notification) {
        notification.setNotificationID(id);
        notificationRepository.update(id, notification.getUserID(), notification.getMessage());
    }

    public void deleteNotification(int id) {
        notificationRepository.delete(id);
    }

    public void markNotificationAsRead(int id) {
        notificationRepository.markNotificationAsRead(id);
    }

    public void markNotificationAsUnread(int id) {
        notificationRepository.markNotificationAsUnread(id);
    }
}
