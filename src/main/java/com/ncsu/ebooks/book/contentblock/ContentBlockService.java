package com.ncsu.ebooks.book.contentblock;

import com.ncsu.ebooks.book.activity.ActivityService;
import com.ncsu.ebooks.book.chapter.ChapterModel;
import com.ncsu.ebooks.book.section.SectionModel;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        try {
            List<ContentBlockModel> contentblocks = contentBlockRepository.findAll();

            for (ContentBlockModel contentblock : contentblocks) {
                try {
                    contentblock.setActivities(activityService.getActivityByContentID(contentblock.getContentBlockID()));
                } catch (DataAccessException e) {
                    System.err.println("Error retrieving activities for content block ID " + contentblock.getContentBlockID() + ": " + e.getMessage());
                    contentblock.setActivities(Collections.emptyList());
                }
            }

            return contentblocks;

        } catch (DataAccessException e) {
            System.err.println("Error retrieving content blocks: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve content blocks", e);
        }
    }

    public ContentBlockModel getContentBlockById(int id) {
        return contentBlockRepository.findById(id);
    }

    public List<ContentBlockModel> getContentBlockBySectionID(int sectionID) {
        return contentBlockRepository.findBySectionID(sectionID);
    }

    public ContentBlockModel createContentBlock(ContentBlockModel contentBlock) {
        try {
            return contentBlockRepository.save(contentBlock);
        } catch (DataAccessException e) {
            System.err.println("Error creating content block: " + e.getMessage());
            throw new RuntimeException("Failed to create content block: " + e.getMessage(), e);
        }
    }

    public void updateContentBlock(int id, ContentBlockModel contentBlock) {
        try {
            contentBlockRepository.update(id, contentBlock);
        } catch (DataAccessException e) {
            System.err.println("Error editing content block: " + e.getMessage());
            throw new RuntimeException("Failed to edit content block: " + e.getMessage(), e);
        }
    }
    public boolean hideContentBlock(int id) {
        try {
            contentBlockRepository.hideContentBlock(id);
            return true;
        } catch (DataAccessException e) {
            System.err.println("Error hiding/unhiding contentblock: " + e.getMessage());
            throw new RuntimeException("Failed to hide/unhide contentblock: " + e.getMessage(), e);
        }
    }

    public boolean deleteContentBlock(int id) {
        try {
            contentBlockRepository.delete(id);
            return true;
        } catch (DataAccessException e) {
            System.err.println("Error deleting contentblock: " + e.getMessage());
            throw new RuntimeException("Failed to delete contentblock: " + e.getMessage(), e);
        }
    }
}
