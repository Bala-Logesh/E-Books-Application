package com.ncsu.ebooks.book.chapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapters")
public class ChapterController {

    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping
    public List<ChapterModel> getAllChapters() {
        return chapterService.getAllChapters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChapterModel> getChapterById(@PathVariable int id) {
        ChapterModel chapter = chapterService.getChapterById(id);
        if (chapter != null) {
            return new ResponseEntity<>(chapter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/eTextBook/{eTextBookID}")
    public ResponseEntity<List<ChapterModel>> getChapterByETextBookID(@PathVariable int eTextBookID) {
        List<ChapterModel> chapters = chapterService.getChapterByETextBookID(eTextBookID);
        if (!chapters.isEmpty()) {
            return new ResponseEntity<>(chapters, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createChapter(@RequestBody ChapterModel chapter) {
        chapterService.createChapter(chapter);
        return new ResponseEntity<>("Chapter created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateChapter(@PathVariable int id, @RequestBody ChapterModel chapter) {
        chapterService.updateChapter(id, chapter);
        return new ResponseEntity<>("Chapter updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChapter(@PathVariable int id) {
        chapterService.deleteChapter(id);
        return new ResponseEntity<>("Chapter deleted successfully", HttpStatus.OK);
    }
}
