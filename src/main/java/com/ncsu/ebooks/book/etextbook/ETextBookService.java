package com.ncsu.ebooks.book.etextbook;
import com.ncsu.ebooks.book.chapter.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ETextBookService {

    @Autowired
    private ETextBookRepository eTextBookRepository;
    @Autowired
    private ChapterService chapterService;

    public List<ETextBookModel> getAllETextBooks() {
        List<ETextBookModel> ebooks = eTextBookRepository.findAll();
        for (ETextBookModel ebook : ebooks) {
            ebook.setChapters(chapterService.getChapterByETextBookID(ebook.getETextBookID()));
        }

        return ebooks;
    }

    public ETextBookModel getETextBookById(int id) {
        return eTextBookRepository.findById(id);
    }

    public void createETextBook(ETextBookModel eTextBook) {
        eTextBookRepository.save(eTextBook);
    }

    public void updateETextBook(int id, ETextBookModel eTextBook) {
        eTextBook.setETextBookID(id);
        eTextBookRepository.update(id, eTextBook);
    }

    public void deleteETextBook(int id) {
        eTextBookRepository.delete(id);
    }
}
