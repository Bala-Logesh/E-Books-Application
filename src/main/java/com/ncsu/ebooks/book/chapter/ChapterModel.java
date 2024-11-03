package com.ncsu.ebooks.book.chapter;

import com.ncsu.ebooks.book.section.SectionModel;
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
    private String chapterNumber;
    private String title;
    private int eTextBookID;

    public void seteTextBookID(int eTextBookID) {
        this.eTextBookID = eTextBookID;
    }

    private boolean hidden = false;
    private List<SectionModel> sections = new ArrayList<>();
}


