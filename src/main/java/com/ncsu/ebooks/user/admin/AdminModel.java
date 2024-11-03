package com.ncsu.ebooks.user.admin;

import com.ncsu.ebooks.user.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminModel {
    private int adminID;
    private String userID;
    private UserModel user;
}