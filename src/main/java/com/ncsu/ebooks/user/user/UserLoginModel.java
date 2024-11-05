package com.ncsu.ebooks.user.user;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginModel {
    private String userID;
    private String password;
}