package com.ncsu.ebooks.book.contentblock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contentblocks")
public class ContentBlockController {

    private final ContentBlockService contentBlockService;

    public ContentBlockController(ContentBlockService contentBlockService) {
        this.contentBlockService = contentBlockService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllContentBlocks() {
        Map<String, Object> response = new HashMap<>();
        List<ContentBlockModel> contentBlockResponse;

        try {
            contentBlockResponse = contentBlockService.getAllContentBlocks();
            if (contentBlockResponse != null && !contentBlockResponse.isEmpty()) {
                response.put("message", "Content Blocks retrieved successfully");
                response.put("contentBlocks", contentBlockResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No content blocks found");
            response.put("message", "No content blocks available");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error retrieving content blocks: " + e.getMessage());
            response.put("message", "Failed to retrieve content blocks");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentBlockModel> getContentBlockById(@PathVariable int id) {
        ContentBlockModel contentBlock = contentBlockService.getContentBlockById(id);
        if (contentBlock != null) {
            return new ResponseEntity<>(contentBlock, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/section/{sectionID}")
    public ResponseEntity<List<ContentBlockModel>> getContentBlockBySectionID(@PathVariable int sectionID) {
        List<ContentBlockModel> contentBlocks = contentBlockService.getContentBlockBySectionID(sectionID);
        if (!contentBlocks.isEmpty()) {
            return new ResponseEntity<>(contentBlocks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createContentBlock(@RequestBody ContentBlockModel contentBlock) {
        System.out.println(contentBlock.toString());
        ContentBlockModel contentBlockResponse = contentBlockService.createContentBlock(contentBlock);

        Map<String, Object> response = new HashMap<>();
        if (contentBlockResponse != null) {
            response.put("message", "Content Block created successfully");
            response.put("contentBlock", contentBlockResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating content block");
        response.put("message", "Failed to create content block");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContentBlock(@PathVariable int id, @RequestBody ContentBlockModel contentBlock) {
        contentBlockService.updateContentBlock(id, contentBlock);
        return new ResponseEntity<>("Content Block updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContentBlock(@PathVariable int id) {
        contentBlockService.deleteContentBlock(id);
        return new ResponseEntity<>("Content Block deleted successfully", HttpStatus.OK);
    }
}
