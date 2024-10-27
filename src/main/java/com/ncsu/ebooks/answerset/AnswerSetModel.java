package com.ncsu.ebooks.answerset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerSetModel {
    private int answerSetID;
    @Setter
    private int activityId;
    @Setter
    private String title;
    @Setter
    private String answerOption;
    @Setter
    private boolean correct = false;
    @Setter
    private String explanation;
}