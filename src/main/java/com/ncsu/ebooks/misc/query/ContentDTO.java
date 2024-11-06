package com.ncsu.ebooks.misc.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentDTO {
    private String sectionNumber;
    private String sectionTitle;
    private int contentBlockID;
    private String textBlock;
    private String image;
    private int activityID;
    private String question;
    private int answerSetID;
    private String answerOption;
    private boolean correct;
    private String explanation;
}
