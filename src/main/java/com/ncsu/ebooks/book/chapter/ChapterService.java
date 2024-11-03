package com.ncsu.ebooks.book.chapter;

import com.ncsu.ebooks.book.section.SectionService;
import org.springframework.stereotype.Service;

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
        List<ChapterModel> chapters = chapterRepository.findAll();
        for (ChapterModel chapter : chapters) {
            chapter.setSections(sectionService.getSectionByChapterID(chapter.getChapterId()));
        }

        return chapters;
    }

    public ChapterModel getChapterById(int id) {
        return chapterRepository.findById(id);
    }

    public List<ChapterModel> getChapterByETextBookID(int eTextBookId) {
        return chapterRepository.findByETextBookID(eTextBookId);
    }

    public void createChapter(ChapterModel chapter) {
        chapterRepository.save(chapter);
    }

    public void updateChapter(int id, ChapterModel chapter) {
        chapter.setChapterId(id);
        chapterRepository.update(id, chapter);
    }

    public void deleteChapter(int id) {
        chapterRepository.delete(id);
    }
}
