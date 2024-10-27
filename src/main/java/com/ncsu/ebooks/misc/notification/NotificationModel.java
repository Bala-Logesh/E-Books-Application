package com.ncsu.ebooks.misc.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationModel {
    private int notificationId;
    private int userId;
    private String message;
    private boolean readStatus = false;
}