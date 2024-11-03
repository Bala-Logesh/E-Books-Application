package com.ncsu.ebooks.book.activity;

import com.ncsu.ebooks.book.answerset.AnswerSetService;
import org.springframework.stereotype.Service;

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
        List<ActivityModel> activities = activityRepository.findAll();
        for (ActivityModel activity : activities) {
            activity.setAnswerSets(answerSetService.getAnswerSetByActivityID(activity.getActivityID()));
        }

        return activities;
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

    public void createActivity(ActivityModel activity) {
        activityRepository.save(activity);
    }

    public void updateActivity(int id, ActivityModel activity) {
        activity.setActivityID(id);
        activityRepository.update(id, activity);
    }

    public void deleteActivity(int id) {
        activityRepository.delete(id);
    }
}
