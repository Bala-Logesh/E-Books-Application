package com.ncsu.ebooks.contentblock;

import com.ncsu.ebooks.activity.ActivityModel;
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
public class ContentBlockModel {
    private int contentBlockId;
    private int sectionId;
    private String image;
    private String textBlock;
    private boolean hidden = false;
    private List<ActivityModel> activities = new ArrayList<>();
}