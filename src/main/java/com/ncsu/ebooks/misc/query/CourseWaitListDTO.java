package com.ncsu.ebooks.misc.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseWaitListDTO {
    private String courseID;
    private int totalWaitList;

}
