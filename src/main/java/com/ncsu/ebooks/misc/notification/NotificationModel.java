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
    private int notificationID;
    private String userID;
    private String message;
    private boolean messageRead = false;
}