package com.ncsu.ebooks.activity;

import com.ncsu.ebooks.answerset.AnswerSetModel;
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
    private int activityId;
    private int sectionId;
    private int contentId;
    private String question;
    private boolean hidden = false;
    private List<AnswerSetModel> answerSets = new ArrayList<>();
}