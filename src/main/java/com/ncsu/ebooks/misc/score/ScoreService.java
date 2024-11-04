package com.ncsu.ebooks.misc.score;

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

    public void createScore(ScoreModel score) {
        scoreRepository.save(score.getStudentID(), score.getActiveCourseID(), score.getActivityID(), score.getPoint());
    }

    public void updateScore(int id, ScoreModel score) {
        score.setScoreID(id);
        scoreRepository.update(id, score.getStudentID(), score.getActiveCourseID(), score.getActivityID(), score.getPoint());
    }

    public void deleteScore(int id) {
        scoreRepository.delete(id);
    }
}
