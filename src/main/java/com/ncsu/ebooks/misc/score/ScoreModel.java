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
public class ScoreModel {
    private int scoreID;
    private int studentID;
    private int activeCourseID;
    private int activityID;
    private int point;
    private Timestamp timeStamp;
}