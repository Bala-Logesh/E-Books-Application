package com.ncsu.ebooks.answerset;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answerSets")
public class AnswerSetController {

    private final AnswerSetService answerSetService;

    public AnswerSetController(AnswerSetService answerSetService) {
        this.answerSetService = answerSetService;
    }

    @GetMapping
    public List<AnswerSetModel> getAllAnswerSets() {
        return answerSetService.getAllAnswerSets();
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

    @GetMapping("/{activityId}")
    public ResponseEntity<List<AnswerSetModel>> getAnswerSetByActivityId(@PathVariable int activityId) {
        List<AnswerSetModel> answerSets = answerSetService.getAnswerSetByActivityId(activityId);
        if (!answerSets.isEmpty()) {
            return new ResponseEntity<>(answerSets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> creatAnswerSet(@RequestBody AnswerSetModel answerSet) {
        answerSetService.creatAnswerSet(answerSet);
        return new ResponseEntity<>("AnswerSet created successfully", HttpStatus.CREATED);
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
