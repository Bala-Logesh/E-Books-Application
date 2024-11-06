package com.ncsu.ebooks.book.contentblock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<Map<String, Object>> getContentBlockBySectionID(@PathVariable int sectionID) {
        List<ContentBlockModel> contentBlocks = contentBlockService.getContentBlockBySectionID(sectionID);
        Map<String, Object> response = new HashMap<>();
        if (!contentBlocks.isEmpty()) {
            response.put("message", "Content Blocks retrieved successfully");
            response.put("contentBlocks", contentBlocks);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving content blocks: ");
            response.put("message", "Failed to retrieve content blocks");
            response.put("contentBlocks", new ArrayList<ContentBlockModel>());
            return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<Map<String, String>> updateContentBlock(@PathVariable int id, @RequestBody ContentBlockModel contentBlock) {
        Map<String, String> response = new HashMap<>();
        try{
            contentBlockService.updateContentBlock(id, contentBlock);
            response.put("message", "Content Block edited successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error retrieving content blocks: " + e.getMessage());
            response.put("message", "Failed to edit content blocks");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/{id}/hide")
    public ResponseEntity<Map<String, String>> hideContentBlock(@PathVariable int id) {
        boolean success = contentBlockService.hideContentBlock(id);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "ContentBlock hidden/unhidden successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else {
            System.err.println("Error hiding/unhiding contentblock");
            response.put("message", "Failed to hide/unhide contentblock");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteContentBlock(@PathVariable int id) {
        boolean success = contentBlockService.deleteContentBlock(id);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "ContentBlock deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else {
            System.err.println("Error deleting contentblock");
            response.put("message", "Failed to delete contentblock");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
