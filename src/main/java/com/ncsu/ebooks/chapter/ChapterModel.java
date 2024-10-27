package com.ncsu.ebooks.chapter;

import com.ncsu.ebooks.answerset.AnswerSetModel;
import com.ncsu.ebooks.section.SectionModel;
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
public class ChapterModel {
    private int chapterId;
    private int eTextBookID;
    private int chapterNumber;
    private String title;
    private boolean hidden = false;
    private List<SectionModel> sections = new ArrayList<>();
}