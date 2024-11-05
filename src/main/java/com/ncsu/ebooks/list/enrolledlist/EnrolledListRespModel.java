package com.ncsu.ebooks.list.enrolledlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrolledListRespModel {
    private int enrolledListID;
    private String courseID;
    private String userID;
    private String firstName;
    private String lastName;
}