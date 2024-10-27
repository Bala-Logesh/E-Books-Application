package com.ncsu.ebooks.user.admin;

import com.ncsu.ebooks.book.contentblock.ContentBlockModel;
import com.ncsu.ebooks.user.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminModel {
    private int adminId;
    private int userId;
    private UserModel user;
}