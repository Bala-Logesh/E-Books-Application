package com.ncsu.ebooks.book.section;

import com.ncsu.ebooks.book.contentblock.ContentBlockModel;
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
    private int sectionID;
    private int chapterID;
    private String sectionNumber;
    private String title;
    private boolean hidden = false;
    private List<ContentBlockModel> contentBlocks = new ArrayList<>();
}