package com.ncsu.ebooks.course.evaluationcourse;
import com.ncsu.ebooks.course.course.CourseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eCourses")
public class ECourseController {

    private final ECourseService ECourseService;

    public ECourseController(ECourseService ECourseService) {
        this.ECourseService = ECourseService;
    }

    @GetMapping
    public List<ECourseModel> getAllECourses() {
        return ECourseService.getAllECourses();
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
    public ResponseEntity<String> createECourse(@RequestBody CourseModel course) {
        ECourseService.createECourse(course);
        return new ResponseEntity<>("ECourse created successfully", HttpStatus.CREATED);
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
