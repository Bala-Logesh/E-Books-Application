package com.ncsu.ebooks.book.answerset;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerSetService {

    private final AnswerSetRepository answerSetRepository;

    public AnswerSetService(AnswerSetRepository answerSetRepository) {
        this.answerSetRepository = answerSetRepository;
    }

    public List<AnswerSetModel> getAllAnswerSets() {
        return answerSetRepository.findAll();
    }

    public AnswerSetModel getAnswerSetById(int id) {
        return answerSetRepository.findById(id);
    }

    public List<AnswerSetModel> getAnswerSetByActivityId(int activityId) {
        return answerSetRepository.findByActivityId(activityId);
    }

    public void creatAnswerSet(AnswerSetModel answerSet) {
        answerSetRepository.save(answerSet);
    }

    public void updateAnswerSet(int id, AnswerSetModel answerSet) {
        answerSet.setAnswerSetID(id);
        answerSetRepository.update(id, answerSet);
    }

    public void deleteAnswerSet(int id) {
        answerSetRepository.delete(id);
    }
}
