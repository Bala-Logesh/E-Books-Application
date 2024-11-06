package com.ncsu.ebooks.misc.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    private String answerOption;
    private String explanation;
}
