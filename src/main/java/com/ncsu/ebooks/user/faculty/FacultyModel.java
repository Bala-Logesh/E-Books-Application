package com.ncsu.ebooks.user.faculty;

import com.ncsu.ebooks.user.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyModel {
    private int facultyID;
    private String userID;
    private UserModel user;
}