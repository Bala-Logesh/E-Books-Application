package com.ncsu.ebooks.book.section;

import com.ncsu.ebooks.book.contentblock.ContentBlockService;
import org.springframework.stereotype.Service;

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
        List<SectionModel> sections = sectionRepository.findAll();
        for (SectionModel section : sections) {
            section.setContentBlocks(contentBlockService.getContentBlockBySectionID(section.getSectionID()));
        }

        return sections;
    }

    public SectionModel getSectionById(int id) {
        return sectionRepository.findById(id);
    }

    public List<SectionModel> getSectionByChapterID(int chapterID) {
        return sectionRepository.findByChapterID(chapterID);
    }

    public void createSection(SectionModel section) {
        sectionRepository.save(section);
    }

    public void updateSection(int id, SectionModel section) {
        section.setSectionID(id);
        sectionRepository.update(id, section);
    }

    public void deleteSection(int id) {
        sectionRepository.delete(id);
    }
}
