package com.ncsu.ebooks.course.course;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRespModel {
    private String courseID;
    private String title;
    private Timestamp startDate;
    private Timestamp endDate;
    private int eTextBookID;
    private String eTextBookTitle;
    private String courseType;
    private int courseTypeID;
    private String token;
}