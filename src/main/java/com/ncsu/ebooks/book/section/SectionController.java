package com.ncsu.ebooks.book.section;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<SectionModel> getAllSections() {
        return sectionService.getAllSections();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionModel> getSectionById(@PathVariable int id) {
        SectionModel section = sectionService.getSectionById(id);
        if (section != null) {
            return new ResponseEntity<>(section, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<List<SectionModel>> getSectionByContentId(@PathVariable int contentId) {
        List<SectionModel> sections = sectionService.getSectionByChapterId(contentId);
        if (!sections.isEmpty()) {
            return new ResponseEntity<>(sections, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> creatAnswerSet(@RequestBody SectionModel section) {
        sectionService.createSection(section);
        return new ResponseEntity<>("Section created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAnswerSet(@PathVariable int id, @RequestBody SectionModel section) {
        sectionService.updateSection(id, section);
        return new ResponseEntity<>("Section updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnswerSet(@PathVariable int id) {
        sectionService.deleteSection(id);
        return new ResponseEntity<>("Section deleted successfully", HttpStatus.OK);
    }
}
