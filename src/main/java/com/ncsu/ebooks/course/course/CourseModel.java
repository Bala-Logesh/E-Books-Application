package com.ncsu.ebooks.course.course;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseModel {
    private String courseID;
    private String title;
    private int facultyID;
    private Timestamp startDate;
    private Timestamp endDate;

    public void seteTextBookID(int eTextBookID) {
        this.eTextBookID = eTextBookID;
    }

    private int eTextBookID;
}