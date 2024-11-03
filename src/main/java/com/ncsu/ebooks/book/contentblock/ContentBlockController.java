package com.ncsu.ebooks.book.contentblock;
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
    public ResponseEntity<String> createContentBlock(@RequestBody ContentBlockModel contentBlock) {
        System.out.println(contentBlock);
        contentBlockService.createContentBlock(contentBlock);
        return new ResponseEntity<>("Content Block created successfully", HttpStatus.CREATED);
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
