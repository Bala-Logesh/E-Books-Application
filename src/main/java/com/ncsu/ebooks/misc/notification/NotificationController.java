package com.ncsu.ebooks.misc.notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationModel> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationModel> getNotificationById(@PathVariable int id) {
        NotificationModel notification = notificationService.getNotificationById(id);
        if (notification != null) {
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<List<NotificationModel>> getNotificationByUserId(@PathVariable String userID) {
        List<NotificationModel> notifications = notificationService.getNotificationByUserId(userID);
        if (!notifications.isEmpty()) {
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody NotificationModel notification) {
        notificationService.createNotification(notification);
        return new ResponseEntity<>("Notification created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable int id) {
        notificationService.markNotificationAsRead(id);
        return new ResponseEntity<>("Notification marked as read successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}/unread")
    public ResponseEntity<String> markNotificationAsUnread(@PathVariable int id) {
        notificationService.markNotificationAsUnread(id);
        return new ResponseEntity<>("Notification marked as unread successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateNotification(@PathVariable int id, @RequestBody NotificationModel notification) {
        notificationService.updateNotification(id, notification);
        return new ResponseEntity<>("Notification updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable int id) {
        notificationService.deleteNotification(id);
        return new ResponseEntity<>("Notification deleted successfully", HttpStatus.OK);
    }
}
