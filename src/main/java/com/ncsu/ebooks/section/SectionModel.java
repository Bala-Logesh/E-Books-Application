package com.ncsu.ebooks.section;

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
public class SectionModel {
    private int sectionId;
    private int chapterId;
    private int sectionNumber;
    private String title;
    private boolean hidden = false;
    private List<AnswerSetModel> contentBlocks = new ArrayList<>();
}