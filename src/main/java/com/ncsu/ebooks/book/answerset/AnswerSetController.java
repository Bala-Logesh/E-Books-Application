package com.ncsu.ebooks.book.answerset;
import com.ncsu.ebooks.book.chapter.ChapterModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/answerSets")
public class AnswerSetController {

    private final AnswerSetService answerSetService;

    public AnswerSetController(AnswerSetService answerSetService) {
        this.answerSetService = answerSetService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAnswerSets() {
        Map<String, Object> response = new HashMap<>();
        List<AnswerSetModel> answerSetResponse;

        try {
            answerSetResponse = answerSetService.getAllAnswerSets();
            if (answerSetResponse != null && !answerSetResponse.isEmpty()) {
                response.put("message", "Answer Sets retrieved successfully");
                response.put("answerSets", answerSetResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No answer sets found");
            response.put("message", "No answer sets available");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error retrieving answer sets: " + e.getMessage());
            response.put("message", "Failed to retrieve answer sets");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerSetModel> getAnswerSetById(@PathVariable int id) {
        AnswerSetModel answerSet = answerSetService.getAnswerSetById(id);
        if (answerSet != null) {
            return new ResponseEntity<>(answerSet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/activity/{activityID}")
    public ResponseEntity<List<AnswerSetModel>> getAnswerSetByActivityId(@PathVariable int activityID) {
        List<AnswerSetModel> answerSets = answerSetService.getAnswerSetByActivityID(activityID);
        if (!answerSets.isEmpty()) {
            return new ResponseEntity<>(answerSets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAnswerSet(@RequestBody AnswerSetModel answerSet) {
        System.out.println(answerSet.toString());
        AnswerSetModel answerSetResponse = answerSetService.createAnswerSet(answerSet);

        Map<String, Object> response = new HashMap<>();
        if (answerSetResponse != null) {
            response.put("message", "Answer Set created successfully");
            response.put("answerSet", answerSetResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating answer set");
        response.put("message", "Failed to create answer set");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAnswerSet(@PathVariable int id, @RequestBody AnswerSetModel answerSet) {
        answerSetService.updateAnswerSet(id, answerSet);
        return new ResponseEntity<>("AnswerSet updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnswerSet(@PathVariable int id) {
        answerSetService.deleteAnswerSet(id);
        return new ResponseEntity<>("AnswerSet deleted successfully", HttpStatus.OK);
    }
}
