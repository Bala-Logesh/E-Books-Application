package com.ncsu.ebooks.book.contentblock;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentBlockService {

    private final ContentBlockRepository contentBlockRepository;

    public ContentBlockService(ContentBlockRepository contentBlockRepository) {
        this.contentBlockRepository = contentBlockRepository;
    }

    public List<ContentBlockModel> getAllContentBlocks() {
        return contentBlockRepository.findAll();
    }

    public ContentBlockModel getContentBlockById(int id) {
        return contentBlockRepository.findById(id);
    }

    public List<ContentBlockModel> getContentBlockBySectionId(int sectionId) {
        return contentBlockRepository.findBySectionId(sectionId);
    }

    public void createContentBlock(ContentBlockModel contentBlock) {
        contentBlockRepository.save(contentBlock);
    }

    public void updateContentBlock(int id, ContentBlockModel contentBlock) {
        contentBlock.setContentBlockId(id);
        contentBlockRepository.update(id, contentBlock);
    }

    public void deleteContentBlock(int id) {
        contentBlockRepository.delete(id);
    }
}
