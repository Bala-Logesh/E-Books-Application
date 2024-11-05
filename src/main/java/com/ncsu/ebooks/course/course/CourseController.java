package com.ncsu.ebooks.course.course;
import com.ncsu.ebooks.book.chapter.ChapterModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseModel> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseModel> getCourseById(@PathVariable String id) {
        CourseModel course = courseService.getCourseById(id);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createCourse(@RequestBody CourseModel course) {
        System.out.println(course.toString());
        boolean success = courseService.createCourse(course);

        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Course created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating course");
        response.put("message", "Failed to create course");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable String id, @RequestBody CourseModel course) {
        courseService.updateCourse(id, course);
        return new ResponseEntity<>("Course updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
    }
}
