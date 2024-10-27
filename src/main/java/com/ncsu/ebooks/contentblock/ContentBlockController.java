package com.ncsu.ebooks.contentblock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contentblocks")
public class ContentBlockController {

    private final ContentBlockService contentBlockService;

    public ContentBlockController(ContentBlockService contentBlockService) {
        this.contentBlockService = contentBlockService;
    }

    @GetMapping
    public List<ContentBlockModel> getAllContentBlocks() {
        return contentBlockService.getAllContentBlocks();
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

    @GetMapping("/{sectionId}")
    public ResponseEntity<List<ContentBlockModel>> getContentBlockBySectionId(@PathVariable int sectionId) {
        List<ContentBlockModel> contentBlocks = contentBlockService.getContentBlockBySectionId(sectionId);
        if (!contentBlocks.isEmpty()) {
            return new ResponseEntity<>(contentBlocks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createContentBlock(@RequestBody ContentBlockModel contentBlock) {
        contentBlockService.createContentBlock(contentBlock);
        return new ResponseEntity<>("Activity created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContentBlock(@PathVariable int id, @RequestBody ContentBlockModel contentBlock) {
        contentBlockService.updateContentBlock(id, contentBlock);
        return new ResponseEntity<>("Activity updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContentBlock(@PathVariable int id) {
        contentBlockService.deleteContentBlock(id);
        return new ResponseEntity<>("Activity deleted successfully", HttpStatus.OK);
    }
}
