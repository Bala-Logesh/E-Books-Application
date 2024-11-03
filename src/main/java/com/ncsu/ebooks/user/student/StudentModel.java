package com.ncsu.ebooks.user.student;

import com.ncsu.ebooks.user.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentModel {
    private int studentID;
    private String userID;
    private UserModel user;
}