package com.ncsu.ebooks.book.activity;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<ActivityModel> getAllActivities() {
        return activityRepository.findAll();
    }

    public ActivityModel getActivityById(int id) {
        return activityRepository.findById(id);
    }

    public List<ActivityModel> getActivityByContentId(int contentId) {
        return activityRepository.findByContentId(contentId);
    }

    public void createActivity(ActivityModel activity) {
        activityRepository.save(activity);
    }

    public void updateActivity(int id, ActivityModel activity) {
        activity.setActivityId(id);
        activityRepository.update(id, activity);
    }

    public void deleteActivity(int id) {
        activityRepository.delete(id);
    }
}
