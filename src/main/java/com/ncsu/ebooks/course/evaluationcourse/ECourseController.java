package com.ncsu.ebooks.course.evaluationcourse;
import com.ncsu.ebooks.course.course.CourseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eCourses")
public class ECourseController {

    private final ECourseService ECourseService;

    public ECourseController(ECourseService ECourseService) {
        this.ECourseService = ECourseService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllECourses() {
        Map<String, Object> response = new HashMap<>();
        List<ECourseModel> evaluationCourseResponse;

        try {
            evaluationCourseResponse = ECourseService.getAllECourses();
            if (evaluationCourseResponse != null && !evaluationCourseResponse.isEmpty()) {
                response.put("message", "Evaluation courses retrieved successfully");
                response.put("aCourses", evaluationCourseResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No evaluation courses found");
            response.put("message", "No evaluation courses available");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error retrieving evaluation courses: " + e.getMessage());
            response.put("message", "Failed to retrieve evaluation courses");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ECourseModel> getECourseById(@PathVariable int id) {
        ECourseModel eCourse = ECourseService.getECourseById(id);
        if (eCourse != null) {
            return new ResponseEntity<>(eCourse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createECourse(@RequestBody CourseModel course) {
        boolean success = ECourseService.createECourse(course);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Evaluation Course created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating evaluation course");
        response.put("message", "Failed to create evaluation course");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateECourse(@PathVariable int id, @RequestBody int courseId) {
        ECourseService.updateECourse(id, courseId);
        return new ResponseEntity<>("ECourse updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteECourse(@PathVariable int id) {
        ECourseService.deleteECourse(id);
        return new ResponseEntity<>("ECourse deleted successfully", HttpStatus.OK);
    }
}
