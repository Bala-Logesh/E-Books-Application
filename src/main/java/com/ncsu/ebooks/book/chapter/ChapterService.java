package com.ncsu.ebooks.book.chapter;

import com.ncsu.ebooks.book.etextbook.ETextBookModel;
import com.ncsu.ebooks.book.section.SectionService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final SectionService sectionService;

    public ChapterService(ChapterRepository chapterRepository, SectionService sectionService) {
        this.chapterRepository = chapterRepository;
        this.sectionService = sectionService;
    }

    public List<ChapterModel> getAllChapters() {
        try {
            List<ChapterModel> chapters = chapterRepository.findAll();

            for (ChapterModel chapter : chapters) {
                try {
                    chapter.setSections(sectionService.getSectionByChapterID(chapter.getChapterID()));
                } catch (DataAccessException e) {
                    System.err.println("Error retrieving sections for chapter ID " + chapter.getChapterID() + ": " + e.getMessage());
                    chapter.setSections(Collections.emptyList());
                }
            }

            return chapters;

        } catch (DataAccessException e) {
            System.err.println("Error retrieving chapters: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve chapters", e);
        }
    }

    public ChapterModel getChapterById(int id) {
        return chapterRepository.findById(id);
    }

    public List<ChapterModel> getChapterByETextBookID(int eTextBookId) {
        return chapterRepository.findByETextBookID(eTextBookId);
    }

    public ChapterModel createChapter(ChapterModel chapter) {
        try {
            return chapterRepository.save(chapter);
        } catch (DataAccessException e) {
            System.err.println("Error creating chapter: " + e.getMessage());
            throw new RuntimeException("Failed to create chapter: " + e.getMessage(), e);
        }
    }

    public void updateChapter(int id, ChapterModel chapter) {
        chapter.setChapterID(id);
        chapterRepository.update(id, chapter);
    }

    public void deleteChapter(int id) {
        chapterRepository.delete(id);
    }
}
