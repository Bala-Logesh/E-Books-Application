package com.ncsu.ebooks.user.teachingassistant;

import com.ncsu.ebooks.user.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TAModel {
    private int teachingAsstID;
    private String userID;
    private int activeCourseID;
    private boolean resetPassword = true;
    private UserModel user;
}