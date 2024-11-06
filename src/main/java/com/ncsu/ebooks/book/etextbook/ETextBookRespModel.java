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
public class ETextBookRespModel {
    private int studentId;
    private int activeCourseId;
    private int eTextBookId;
    private String courseId;
    private String title;
    private String courseName;
}
