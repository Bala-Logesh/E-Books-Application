package com.ncsu.ebooks.misc.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveCourseDetailsDTO {
    private String courseID;
    private String facultyFirstName;
    private String facultyLastName;
    private int totalStudents;
}
