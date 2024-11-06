package com.ncsu.ebooks.misc.score;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreSummaryModel {
    private int ssummaryID;
    private int studentID;
    private int activeCourseID;
    private int totalPoints;
    private int totalActivities;
    private Timestamp timeStamp;
    private String courseName;
}