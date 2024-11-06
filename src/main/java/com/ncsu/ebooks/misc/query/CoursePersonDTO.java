package com.ncsu.ebooks.misc.query;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePersonDTO {
    private String firstName;
    private String lastName;
    private String role;
    private String courseID;
}
