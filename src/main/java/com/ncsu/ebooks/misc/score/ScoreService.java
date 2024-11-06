package com.ncsu.ebooks.misc.score;

import com.ncsu.ebooks.book.etextbook.ETextBookRespModel;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<ScoreModel> getAllScores() {
        return scoreRepository.findAll();
    }

    public ScoreModel getScoreById(int id) {
        return scoreRepository.findById(id);
    }

    public boolean createScore(ScoreModel score) {
        try {
            scoreRepository.save(score.getStudentID(), score.getActiveCourseID(), score.getActivityID(), score.getPoint());
            return true;
        } catch (DataAccessException e) {
            System.err.println("Error creating score: " + e.getMessage());
            throw new RuntimeException("Failed to create score: " + e.getMessage(), e);
        }
    }

    public void updateScore(int id, ScoreModel score) {
        score.setScoreID(id);
        scoreRepository.update(id, score.getStudentID(), score.getActiveCourseID(), score.getActivityID(), score.getPoint());
    }

    public void deleteScore(int id) {
        scoreRepository.delete(id);
    }

    public List<ScoreSummaryModel> getScoreByStudentId(int id) {
        try {
            return scoreRepository.findByStudentID(id);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving score summary: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve score summary", e);
        }
    }
}
