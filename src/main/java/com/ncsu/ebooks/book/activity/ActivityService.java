package com.ncsu.ebooks.book.activity;

import com.ncsu.ebooks.book.answerset.AnswerSetService;
import com.ncsu.ebooks.book.chapter.ChapterModel;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final AnswerSetService answerSetService;

    public ActivityService(ActivityRepository activityRepository, AnswerSetService answerSetService) {
        this.activityRepository = activityRepository;
        this.answerSetService = answerSetService;
    }

    public List<ActivityModel> getAllActivities() {
        try {
            List<ActivityModel> activities = activityRepository.findAll();

            for (ActivityModel activity : activities) {
                try {
                    activity.setAnswerSets(answerSetService.getAnswerSetByActivityID(activity.getActivityID()));
                } catch (DataAccessException e) {
                    System.err.println("Error retrieving answer sets for activity ID " + activity.getActivityID() + ": " + e.getMessage());
                    activity.setAnswerSets(Collections.emptyList());
                }
            }

            return activities;

        } catch (DataAccessException e) {
            System.err.println("Error retrieving activities: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve activities", e);
        }
    }

    public ActivityModel getActivityById(int id) {
        ActivityModel activity = activityRepository.findById(id);
        activity.setAnswerSets(answerSetService.getAnswerSetByActivityID(activity.getActivityID()));
        return activity;
    }

    public List<ActivityModel> getActivityByContentID(int contentID) {
        List<ActivityModel> activities = activityRepository.findByContentID(contentID);
        for (ActivityModel activity : activities) {
            activity.setAnswerSets(answerSetService.getAnswerSetByActivityID(activity.getActivityID()));
        }

        return activities;
    }

    public ActivityModel createActivity(ActivityModel activity) {
        try {
            return activityRepository.save(activity);
        } catch (DataAccessException e) {
            System.err.println("Error creating activity: " + e.getMessage());
            throw new RuntimeException("Failed to create activity: " + e.getMessage(), e);
        }
    }

    public void updateActivity(int id, ActivityModel activity) {
        activity.setActivityID(id);
        activityRepository.update(id, activity);
    }

    public void deleteActivity(int id) {
        activityRepository.delete(id);
    }
}
