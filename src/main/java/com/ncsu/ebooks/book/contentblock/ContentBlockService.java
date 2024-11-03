package com.ncsu.ebooks.book.contentblock;

import com.ncsu.ebooks.book.activity.ActivityService;
import com.ncsu.ebooks.book.section.SectionModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentBlockService {

    private final ContentBlockRepository contentBlockRepository;
    private final ActivityService activityService;

    public ContentBlockService(ContentBlockRepository contentBlockRepository, ActivityService activityService) {
        this.contentBlockRepository = contentBlockRepository;
        this.activityService = activityService;
    }

    public List<ContentBlockModel> getAllContentBlocks() {

        List<ContentBlockModel> contentblocks = contentBlockRepository.findAll();
        for (ContentBlockModel contentblock : contentblocks) {
            contentblock.setActivities(activityService.getActivityByContentID(contentblock.getContentBlockID()));
        }

        return contentblocks;
    }

    public ContentBlockModel getContentBlockById(int id) {
        return contentBlockRepository.findById(id);
    }

    public List<ContentBlockModel> getContentBlockBySectionID(int sectionID) {
        return contentBlockRepository.findBySectionID(sectionID);
    }

    public void createContentBlock(ContentBlockModel contentBlock) {
        contentBlockRepository.save(contentBlock);
    }

    public void updateContentBlock(int id, ContentBlockModel contentBlock) {
        contentBlock.setContentBlockID(id);
        contentBlockRepository.update(id, contentBlock);
    }

    public void deleteContentBlock(int id) {
        contentBlockRepository.delete(id);
    }
}
