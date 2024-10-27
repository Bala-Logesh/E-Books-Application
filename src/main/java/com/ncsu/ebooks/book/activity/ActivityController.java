package com.ncsu.ebooks.book.activity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public List<ActivityModel> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityModel> getActivityById(@PathVariable int id) {
        ActivityModel activity = activityService.getActivityById(id);
        if (activity != null) {
            return new ResponseEntity<>(activity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<List<ActivityModel>> getActivityByContentId(@PathVariable int contentId) {
        List<ActivityModel> activities = activityService.getActivityByContentId(contentId);
        if (!activities.isEmpty()) {
            return new ResponseEntity<>(activities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> creatAnswerSet(@RequestBody ActivityModel activity) {
        activityService.createActivity(activity);
        return new ResponseEntity<>("Activity created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAnswerSet(@PathVariable int id, @RequestBody ActivityModel activity) {
        activityService.updateActivity(id, activity);
        return new ResponseEntity<>("Activity updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnswerSet(@PathVariable int id) {
        activityService.deleteActivity(id);
        return new ResponseEntity<>("Activity deleted successfully", HttpStatus.OK);
    }
}
