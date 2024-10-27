package com.ncsu.ebooks.book.etextbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ETextBookService {

    @Autowired
    private ETextBookRepository eTextBookRepository;

    public List<ETextBookModel> getAllETextBooks() {
        return eTextBookRepository.findAll();
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
