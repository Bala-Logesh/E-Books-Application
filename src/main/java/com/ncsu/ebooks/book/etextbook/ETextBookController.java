package com.ncsu.ebooks.book.etextbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eTextBooks")
public class ETextBookController {

    @Autowired
    private ETextBookService eTextBookService;

    @GetMapping
    public List<ETextBookModel> getAllETextBooks() {
        return eTextBookService.getAllETextBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ETextBookModel> getETextBookById(@PathVariable int id) {
        ETextBookModel eTextBook = eTextBookService.getETextBookById(id);
        if (eTextBook != null) {
            return new ResponseEntity<>(eTextBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createETextBook(@RequestBody ETextBookModel eTextBook) {
        System.out.println(eTextBook.toString());

        eTextBookService.createETextBook(eTextBook);
        return new ResponseEntity<>("ETextBook created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateETextBook(@PathVariable int id, @RequestBody ETextBookModel eTextBook) {
        eTextBookService.updateETextBook(id, eTextBook);
        return new ResponseEntity<>("ETextBook updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteETextBook(@PathVariable int id) {
        eTextBookService.deleteETextBook(id);
        return new ResponseEntity<>("ETextBook deleted successfully", HttpStatus.OK);
    }
}
