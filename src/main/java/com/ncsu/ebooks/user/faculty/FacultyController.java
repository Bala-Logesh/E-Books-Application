package com.ncsu.ebooks.user.faculty;
import com.ncsu.ebooks.user.user.Role;
import com.ncsu.ebooks.user.user.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public List<FacultyModel> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyModel> getFacultyById(@PathVariable int id) {
        FacultyModel faculty = facultyService.getFacultyById(id);
        if (faculty != null) {
            return new ResponseEntity<>(faculty, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createFaculty(@RequestBody UserModel user) {
        user.setRole(Role.FACULTY);
        facultyService.createFaculty(user);
        return new ResponseEntity<>("Faculty created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFaculty(@PathVariable int id, @RequestBody String userId) {
        facultyService.updateFaculty(id, userId);
        return new ResponseEntity<>("Faculty updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable int id) {
        facultyService.deleteFaculty(id);
        return new ResponseEntity<>("Faculty deleted successfully", HttpStatus.OK);
    }
}
