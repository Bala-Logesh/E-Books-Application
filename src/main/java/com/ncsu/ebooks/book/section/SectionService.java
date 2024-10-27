package com.ncsu.ebooks.book.section;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<SectionModel> getAllActivities() {
        return sectionRepository.findAll();
    }

    public SectionModel getSectionById(int id) {
        return sectionRepository.findById(id);
    }

    public List<SectionModel> getSectionByChapterId(int chapterId) {
        return sectionRepository.findByChapterId(chapterId);
    }

    public void createSection(SectionModel section) {
        sectionRepository.save(section);
    }

    public void updateSection(int id, SectionModel section) {
        section.setSectionId(id);
        sectionRepository.update(id, section);
    }

    public void deleteSection(int id) {
        sectionRepository.delete(id);
    }
}
