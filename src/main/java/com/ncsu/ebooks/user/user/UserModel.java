package com.ncsu.ebooks.user.user;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Timestamp accountCreation;
}