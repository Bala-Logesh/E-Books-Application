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
public class ACourseModel {
    private int activeCourseID;
    private String courseID;
    private int capacity;
    private String token;
    private boolean openToEnroll;
    private CourseModel course;
}