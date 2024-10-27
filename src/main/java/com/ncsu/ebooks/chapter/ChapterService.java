package com.ncsu.ebooks.chapter;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {

    private final ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public List<ChapterModel> getAllChapters() {
        return chapterRepository.findAll();
    }

    public ChapterModel getChapterById(int id) {
        return chapterRepository.findById(id);
    }

    public List<ChapterModel> getChapterByETextBookId(int eTextBookId) {
        return chapterRepository.findByETextBookId(eTextBookId);
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
