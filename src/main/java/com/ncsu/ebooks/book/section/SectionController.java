package com.ncsu.ebooks.book.section;
import com.ncsu.ebooks.book.chapter.ChapterModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSections() {
        Map<String, Object> response = new HashMap<>();
        List<SectionModel> sectionResponse;

        try {
            sectionResponse = sectionService.getAllSections();
            if (sectionResponse != null && !sectionResponse.isEmpty()) {
                response.put("message", "Sections retrieved successfully");
                response.put("sections", sectionResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No sections found");
            response.put("message", "No sections available");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error retrieving sections: " + e.getMessage());
            response.put("message", "Failed to retrieve sections");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @GetMapping("/chapter/{chapterID}")
    public ResponseEntity<List<SectionModel>> getSectionByContentID(@PathVariable int chapterID) {
        List<SectionModel> sections = sectionService.getSectionByChapterID(chapterID);
        if (!sections.isEmpty()) {
            return new ResponseEntity<>(sections, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createSection(@RequestBody SectionModel section) {
        System.out.println(section.toString());
        SectionModel sectionResponse = sectionService.createSection(section);

        Map<String, Object> response = new HashMap<>();
        if (sectionResponse != null) {
            response.put("message", "Section created successfully");
            response.put("section", sectionResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating section");
        response.put("message", "Failed to create section");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSection(@PathVariable int id, @RequestBody SectionModel section) {
        sectionService.updateSection(id, section);
        return new ResponseEntity<>("Section updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSection(@PathVariable int id) {
        sectionService.deleteSection(id);
        return new ResponseEntity<>("Section deleted successfully", HttpStatus.OK);
    }
}
