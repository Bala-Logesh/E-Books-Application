package com.ncsu.ebooks.book.section;
import com.ncsu.ebooks.book.chapter.ChapterModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<Map<String, Object>> getSectionByContentID(@PathVariable int chapterID) {
        List<SectionModel> sections = sectionService.getSectionByChapterID(chapterID);
        Map<String, Object> response = new HashMap<>();
        if (!sections.isEmpty()) {
            response.put("message", "Sections retrieved successfully");
            response.put("sections", sections);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving sections: ");
            response.put("message", "Failed to retrieve sections");
            response.put("sections", new ArrayList<SectionModel>());
            return new ResponseEntity<>(response, HttpStatus.OK);
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

    @PutMapping("/{id}/hide")
    public ResponseEntity<Map<String, String>> hideSection(@PathVariable int id) {
        boolean success = sectionService.hideSection(id);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Section hidden/unhidden successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else {
            System.err.println("Error hiding/unhiding section");
            response.put("message", "Failed to hide/unhide section");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSection(@PathVariable int id) {
        boolean success = sectionService.deleteSection(id);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Section deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else {
            System.err.println("Error deleting section");
            response.put("message", "Failed to delete section");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
