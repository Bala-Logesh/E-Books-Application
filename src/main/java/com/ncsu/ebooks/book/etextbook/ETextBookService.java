package com.ncsu.ebooks.book.etextbook;
import com.ncsu.ebooks.book.chapter.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ETextBookService {

    @Autowired
    private ETextBookRepository eTextBookRepository;
    @Autowired
    private ChapterService chapterService;

    public List<ETextBookModel> getAllETextBooks() {
        try {
            List<ETextBookModel> ebooks = eTextBookRepository.findAll();

            for (ETextBookModel ebook : ebooks) {
                try {
                    ebook.setChapters(chapterService.getChapterByETextBookID(ebook.getETextBookID()));
                } catch (DataAccessException e) {
                    System.err.println("Error retrieving chapters for e-textbook ID " + ebook.getETextBookID() + ": " + e.getMessage());
                    ebook.setChapters(Collections.emptyList());
                }
            }

            return ebooks;

        } catch (DataAccessException e) {
            System.err.println("Error retrieving e-textbooks: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve e-textbooks", e);
        }
    }

    public ETextBookModel getETextBookById(int id) {
        return eTextBookRepository.findById(id);
    }

    public ETextBookModel createETextBook(ETextBookModel eTextBook) {
        try {
            return eTextBookRepository.save(eTextBook);
        } catch (DataAccessException e) {
            System.err.println("Error creating e-textbook: " + e.getMessage());
            throw new RuntimeException("Failed to create e-textbook: " + e.getMessage(), e);
        }
    }

    public void updateETextBook(int id, ETextBookModel eTextBook) {
        eTextBook.setETextBookID(id);
        eTextBookRepository.update(id, eTextBook);
    }

    public void deleteETextBook(int id) {
        eTextBookRepository.delete(id);
    }
}
