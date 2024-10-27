package com.ncsu.ebooks.book.answerset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerSetModel {
    private int answerSetID;
    private int activityId;
    private String answerOption;
    private boolean correct = false;
    private String explanation;
}