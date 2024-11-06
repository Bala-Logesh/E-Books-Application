package com.ncsu.ebooks.book.etextbook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ETextBookRespModel2 {
    private int userTypeID;
    private String courseID;
    private String courseType;
    private int courseTypeID;
    private int etextBookID;
    private String title;
    private String courseName;
}
