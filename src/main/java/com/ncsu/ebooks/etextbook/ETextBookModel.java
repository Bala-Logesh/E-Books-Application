package com.ncsu.ebooks.etextbook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ETextBookModel {
    private int eTextBookID;
    @Setter
    private String title;
}
