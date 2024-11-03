package com.ncsu.ebooks.course.activecourse;
import com.ncsu.ebooks.course.course.CourseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aCourses")
public class ACourseController {

    private final ACourseService ACourseService;

    public ACourseController(ACourseService ACourseService) {
        this.ACourseService = ACourseService;
    }

    @GetMapping
    public List<ACourseModel> getAllACourses() {
        return ACourseService.getAllACourses();
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
    public ResponseEntity<String> createACourse(@RequestBody ACourseReqModel acourse) {
        ACourseService.createACourse(acourse.getCourse(), acourse.getCapacity(), acourse.getToken(), acourse.isOpenToEnroll());
        return new ResponseEntity<>("ACourse created successfully", HttpStatus.CREATED);
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
