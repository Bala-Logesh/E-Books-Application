package com.ncsu.ebooks.book.chapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chapters")
public class ChapterController {

    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllChapters() {
        Map<String, Object> response = new HashMap<>();
        List<ChapterModel> chapterResponse;

        try {
            chapterResponse = chapterService.getAllChapters();
            if (chapterResponse != null && !chapterResponse.isEmpty()) {
                response.put("message", "Chapters retrieved successfully");
                response.put("chapters", chapterResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No chapters found");
            response.put("message", "No chapters available");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error retrieving chapters: " + e.getMessage());
            response.put("message", "Failed to retrieve chapters");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    public ResponseEntity<Map<String, Object>> getChapterByETextBookID(@PathVariable int eTextBookID) {
        List<ChapterModel> chapters = chapterService.getChapterByETextBookID(eTextBookID);
        Map<String, Object> response = new HashMap<>();
        if (!chapters.isEmpty()) {
            response.put("message", "Chapters retrieved successfully");
            response.put("chapters", chapters);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving chapters: ");
            response.put("message", "Failed to retrieve chapters");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createChapter(@RequestBody ChapterModel chapter) {
        System.out.println(chapter.toString());
        ChapterModel chapterResponse = chapterService.createChapter(chapter);

        Map<String, Object> response = new HashMap<>();
        if (chapterResponse != null) {
            response.put("message", "Chapter created successfully");
            response.put("chapter", chapterResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating chapter");
        response.put("message", "Failed to create chapter");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
