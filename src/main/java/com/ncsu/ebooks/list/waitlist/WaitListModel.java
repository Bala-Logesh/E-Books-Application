package com.ncsu.ebooks.list.waitlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaitListModel {
    private int waitListID;
    private int activeCourseID;
    private int studentID;
}