package com.ncsu.ebooks.user.teachingassistant;

import com.ncsu.ebooks.course.course.CourseModel;
import com.ncsu.ebooks.user.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TAReqModel {
        private UserModel user;
        private int activeCourseID;
        private boolean resetPassword = true;
}