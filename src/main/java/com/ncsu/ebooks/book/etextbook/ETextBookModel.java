package com.ncsu.ebooks.book.etextbook;

import com.ncsu.ebooks.book.chapter.ChapterModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ETextBookModel {
    private int eTextBookID;
    private String title;
    private List<ChapterModel> chapters = new ArrayList<>();
}
