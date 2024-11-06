package com.ncsu.ebooks.book.answerset;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerSetService {

    private final AnswerSetRepository answerSetRepository;

    public AnswerSetService(AnswerSetRepository answerSetRepository) {
        this.answerSetRepository = answerSetRepository;
    }

    public List<AnswerSetModel> getAllAnswerSets() {
        try {
            return answerSetRepository.findAll();
        } catch (DataAccessException e) {
            System.err.println("Error retrieving answer sets: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve answer sets", e);
        }
    }

    public AnswerSetModel getAnswerSetById(int id) {
        return answerSetRepository.findById(id);
    }

    public List<AnswerSetModel> getAnswerSetByActivityID(int activityID) {
        return answerSetRepository.findByActivityID(activityID);
    }

    public AnswerSetModel createAnswerSet(AnswerSetModel answerSet) {
        try {
            return answerSetRepository.save(answerSet);
        } catch (DataAccessException e) {
            System.err.println("Error creating answer set: " + e.getMessage());
            throw new RuntimeException("Failed to create answer set: " + e.getMessage(), e);
        }
    }

    public void updateAnswerSet(int id, AnswerSetModel answerSet) {
        answerSetRepository.update(id, answerSet);
    }

    public void deleteAnswerSet(int id) {
        answerSetRepository.delete(id);
    }
}
