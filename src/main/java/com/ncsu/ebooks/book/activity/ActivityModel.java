package com.ncsu.ebooks.book.activity;

import com.ncsu.ebooks.book.answerset.AnswerSetModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityModel {
    private int activityID;
    private int sectionID;

    public void setcontentBlockID(int contentBlockID) {
        this.contentBlockID = contentBlockID;
    }

    private int contentBlockID;
    private String question;
    private boolean hidden = false;
    private List<AnswerSetModel> answerSets = new ArrayList<>();
}