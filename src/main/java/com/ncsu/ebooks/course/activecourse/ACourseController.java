package com.ncsu.ebooks.course.activecourse;
import com.ncsu.ebooks.course.course.CourseModel;
import com.ncsu.ebooks.user.faculty.FacultyModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aCourses")
public class ACourseController {

    private final ACourseService ACourseService;

    public ACourseController(ACourseService ACourseService) {
        this.ACourseService = ACourseService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllACourses() {
        Map<String, Object> response = new HashMap<>();
        List<ACourseModel> activeCourseResponse;

        try {
            activeCourseResponse = ACourseService.getAllACourses();
            if (activeCourseResponse != null && !activeCourseResponse.isEmpty()) {
                response.put("message", "Active courses retrieved successfully");
                response.put("aCourses", activeCourseResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No active courses found");
            response.put("message", "No active courses available");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error retrieving active courses: " + e.getMessage());
            response.put("message", "Failed to retrieve active courses");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ACourseModel> getACourseById(@PathVariable int id) {
        ACourseModel aCourse = ACourseService.getACourseById(id);
        if (aCourse != null) {
            return new ResponseEntity<>(aCourse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createACourse(@RequestBody ACourseReqModel acourse) {
        boolean success = ACourseService.createACourse(acourse.getCourse(), acourse.getCapacity(), acourse.getToken(), acourse.isOpenToEnroll());
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Active Course created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating active course");
        response.put("message", "Failed to create active course");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateACourse(@PathVariable int id, @RequestBody int courseId) {
        ACourseService.updateACourse(id, courseId);
        return new ResponseEntity<>("ACourse updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteACourse(@PathVariable int id) {
        ACourseService.deleteACourse(id);
        return new ResponseEntity<>("ACourse deleted successfully", HttpStatus.OK);
    }
}
