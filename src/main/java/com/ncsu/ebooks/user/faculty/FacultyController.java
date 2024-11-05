package com.ncsu.ebooks.user.faculty;
import com.ncsu.ebooks.user.user.Role;
import com.ncsu.ebooks.user.user.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllFaculties() {
        Map<String, Object> response = new HashMap<>();
        List<FacultyModel> facultyResponse;

        try {
            facultyResponse = facultyService.getAllFaculties();
            if (facultyResponse != null && !facultyResponse.isEmpty()) {
                response.put("message", "Faculties retrieved successfully");
                response.put("faculties", facultyResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No faculties found");
            response.put("message", "No faculties available");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error retrieving faculties: " + e.getMessage());
            response.put("message", "Failed to retrieve faculties");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    public ResponseEntity<Map<String, String>> createFaculty(@RequestBody UserModel user) {
        user.setRole(Role.FACULTY);
        boolean success = facultyService.createFaculty(user);;
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Faculty created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating faculty");
        response.put("message", "Failed to create faculty");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
