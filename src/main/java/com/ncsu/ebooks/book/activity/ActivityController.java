package com.ncsu.ebooks.book.activity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllChapters() {
        Map<String, Object> response = new HashMap<>();
        List<ActivityModel> activityResponse;

        try {
            activityResponse = activityService.getAllActivities();
            if (activityResponse != null && !activityResponse.isEmpty()) {
                response.put("message", "Activities retrieved successfully");
                response.put("activities", activityResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No activities found");
            response.put("message", "No activities available");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error retrieving activities: " + e.getMessage());
            response.put("message", "Failed to retrieve activities");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @GetMapping("/contentblock/{contentID}")
    public ResponseEntity<List<ActivityModel>> getActivityByContentID(@PathVariable int contentID) {
        List<ActivityModel> activities = activityService.getActivityByContentID(contentID);
        if (!activities.isEmpty()) {
            return new ResponseEntity<>(activities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createActivity(@RequestBody ActivityModel activity) {
        System.out.println(activity.toString());
        ActivityModel activityResponse = activityService.createActivity(activity);

        Map<String, Object> response = new HashMap<>();
        if (activityResponse != null) {
            response.put("message", "Activity created successfully");
            response.put("activity", activityResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating activity");
        response.put("message", "Failed to create activity");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateActivity(@PathVariable int id, @RequestBody ActivityModel activity) {
        activityService.updateActivity(id, activity);
        return new ResponseEntity<>("Activity updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable int id) {
        activityService.deleteActivity(id);
        return new ResponseEntity<>("Activity deleted successfully", HttpStatus.OK);
    }
}
