package com.ncsu.ebooks.course.activecourse;

import com.ncsu.ebooks.course.course.CourseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ACourseReqModel {
        private CourseModel course;
        private int capacity;
        private String token;
        private boolean openToEnroll;
}