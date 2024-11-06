package com.ncsu.ebooks.misc.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextbookCoursesDTO {
    private int eTextBookID;
    private String textbookTitle;
    private List<String> courseIDs;
}
