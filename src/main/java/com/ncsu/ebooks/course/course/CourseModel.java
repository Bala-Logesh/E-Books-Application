package com.ncsu.ebooks.course.course;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseModel {
    private int courseId;
    private String Title;
    private int facultyId;
    private Timestamp startDate;
    private Timestamp endDate;
    private int eTextBookId;
}