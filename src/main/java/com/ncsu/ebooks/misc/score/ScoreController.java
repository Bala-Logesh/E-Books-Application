package com.ncsu.ebooks.misc.score;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getScoreById(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        ScoreModel score = scoreService.getScoreById(id);
        if (score != null) {
            response.put("message", "Score retrieved successfully");
            response.put("score", score);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving score: ");
            response.put("message", "Failed to retrieve score");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Map<String, Object>> getScoreByStudentId(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        List<ScoreSummaryModel> scores = scoreService.getScoreByStudentId(id);
        if (!scores.isEmpty()) {
            response.put("message", "Score Summary retrieved successfully");
            response.put("scores", scores);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving score summary: ");
            response.put("scores", new ArrayList<ScoreSummaryModel>());
            response.put("message", "Failed to retrieve score summary");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createScore(@RequestBody ScoreModel score) {
        boolean success = scoreService.createScore(score);

        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Score added successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error adding score");
        response.put("message", "Failed to add score");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
