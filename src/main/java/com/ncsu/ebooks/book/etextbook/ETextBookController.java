package com.ncsu.ebooks.book.etextbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eTextBooks")
public class ETextBookController {

    @Autowired
    private ETextBookService eTextBookService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllETextBooks() {
        Map<String, Object> response = new HashMap<>();
        List<ETextBookModel> etbResponse;

        try {
            etbResponse = eTextBookService.getAllETextBooks();
            if (etbResponse != null && !etbResponse.isEmpty()) {
                response.put("message", "ETextBooks retrieved successfully");
                response.put("etextbooks", etbResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No e-textbooks found");
            response.put("message", "No e-textbooks available");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            System.err.println("Error retrieving e-textbooks: " + e.getMessage());
            response.put("message", "Failed to retrieve e-textbooks");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getETextBookById(@PathVariable int id) {
        ETextBookModel eTextBook = eTextBookService.getETextBookById(id);
        Map<String, Object> response = new HashMap<>();
        if (eTextBook != null) {
            response.put("message", "ETextBooks retrieved successfully");
            response.put("etextbook", eTextBook);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving e-textbooks: ");
            response.put("message", "Failed to retrieve e-textbooks");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<Map<String, Object>> getETextBookByUserID(@PathVariable String userID) {
        Map<String, Object> response = new HashMap<>();
        List<ETextBookRespModel> eTextBooks = eTextBookService.getETextBookByUserID(userID);
        if (eTextBooks != null) {
            response.put("message", "ETextBooks retrieved successfully");
            response.put("eTextBooks", eTextBooks);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving e-textbooks: ");
            response.put("message", "Failed to retrieve e-textbooks");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createETextBook(@RequestBody ETextBookModel eTextBook) {
        System.out.println(eTextBook.toString());
        ETextBookModel etbResponse = eTextBookService.createETextBook(eTextBook);

        Map<String, Object> response = new HashMap<>();
        if (etbResponse != null) {
            response.put("message", "ETextBook created successfully");
            response.put("etextbook", etbResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating e-textbook");
        response.put("message", "Failed to create e-textbook");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
