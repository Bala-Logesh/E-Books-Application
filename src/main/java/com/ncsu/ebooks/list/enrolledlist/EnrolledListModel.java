package com.ncsu.ebooks.list.enrolledlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrolledListModel {
    private int enrolledID;
    private int activeCourseID;
    private int studentID;
    private int courseScore = 0;
}