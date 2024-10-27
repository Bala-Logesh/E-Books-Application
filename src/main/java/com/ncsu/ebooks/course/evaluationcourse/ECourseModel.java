package com.ncsu.ebooks.course.evaluationcourse;

import com.ncsu.ebooks.course.course.CourseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ECourseModel {
    private int eCourseId;
    private int courseId;
    private CourseModel course;
}