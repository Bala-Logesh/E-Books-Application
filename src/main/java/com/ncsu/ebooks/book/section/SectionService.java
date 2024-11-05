package com.ncsu.ebooks.book.section;

import com.ncsu.ebooks.book.chapter.ChapterModel;
import com.ncsu.ebooks.book.contentblock.ContentBlockService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final ContentBlockService contentBlockService;

    public SectionService(SectionRepository sectionRepository, ContentBlockService contentBlockService) {
        this.sectionRepository = sectionRepository;
        this.contentBlockService = contentBlockService;
    }

    public List<SectionModel> getAllSections() {
        try {
            List<SectionModel> sections = sectionRepository.findAll();

            for (SectionModel section : sections) {
                try {
                    section.setContentBlocks(contentBlockService.getContentBlockBySectionID(section.getSectionID()));
                } catch (DataAccessException e) {
                    System.err.println("Error retrieving content blocks for section ID " + section.getSectionID() + ": " + e.getMessage());
                    section.setContentBlocks(Collections.emptyList());
                }
            }

            return sections;

        } catch (DataAccessException e) {
            System.err.println("Error retrieving sections: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve sections", e);
        }
    }

    public SectionModel getSectionById(int id) {
        return sectionRepository.findById(id);
    }

    public List<SectionModel> getSectionByChapterID(int chapterID) {
        return sectionRepository.findByChapterID(chapterID);
    }

    public SectionModel createSection(SectionModel section) {
        try {
            return sectionRepository.save(section);
        } catch (DataAccessException e) {
            System.err.println("Error creating section: " + e.getMessage());
            throw new RuntimeException("Failed to create section: " + e.getMessage(), e);
        }
    }

    public void updateSection(int id, SectionModel section) {
        section.setSectionID(id);
        sectionRepository.update(id, section);
    }

    public void deleteSection(int id) {
        sectionRepository.delete(id);
    }
}
