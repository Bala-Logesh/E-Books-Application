package com.ncsu.ebooks.misc.score;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public List<ScoreModel> getAllScores() {
        return scoreService.getAllScores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreModel> getScoreById(@PathVariable int id) {
        ScoreModel score = scoreService.getScoreById(id);
        if (score != null) {
            return new ResponseEntity<>(score, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createScore(@RequestBody ScoreModel score) {
        scoreService.createScore(score);
        return new ResponseEntity<>("Score created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateScore(@PathVariable int id, @RequestBody ScoreModel score) {
        scoreService.updateScore(id, score);
        return new ResponseEntity<>("Score updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteScore(@PathVariable int id) {
        scoreService.deleteScore(id);
        return new ResponseEntity<>("Score deleted successfully", HttpStatus.OK);
    }
}
