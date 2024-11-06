package com.ncsu.ebooks.user.teachingassistant;
import com.ncsu.ebooks.user.faculty.FacultyModel;
import com.ncsu.ebooks.user.user.Role;
import com.ncsu.ebooks.user.user.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tas")
public class TAController {

    private final TAService TAService;

    public TAController(TAService TAService) {
        this.TAService = TAService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTAs(){
        Map<String, Object> response = new HashMap<>();
        List<TAModel> tAResponse;

        try {
            tAResponse = TAService.getAllTAs();
            if (tAResponse != null && !tAResponse.isEmpty()) {
                response.put("message", "TAs retrieved successfully");
                response.put("tas", tAResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No TAs found");
            response.put("message", "No TAs available");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error retrieving TAs: " + e.getMessage());
            response.put("message", "Failed to retrieve TAs");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TAModel> getTAById(@PathVariable int id) {
        TAModel ta = TAService.getTAById(id);
        if (ta != null) {
            return new ResponseEntity<>(ta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<Map<String, Object>> getTAById(@PathVariable String userID) {
        TAModel ta = TAService.getTAByUserId(userID);
        Map<String, Object> response = new HashMap<>();
        if (ta != null) {
            response.put("message", "TA retrieved successfully");
            response.put("ta", ta);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving TA");
            response.put("message", "Failed to retrieve TA");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createTA(@RequestBody TAReqModel taUser) {
        taUser.getUser().setRole(Role.TEACHING_ASSISTANT);
        boolean success = TAService.createTA(taUser.getUser(), taUser.getActiveCourseID(), taUser.isResetPassword());
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "TA created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating TA");
        response.put("message", "Failed to create TA");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTA(@PathVariable int id, @RequestBody String userId) {
        TAService.updateTA(id, userId);
        return new ResponseEntity<>("TA updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTA(@PathVariable int id) {
        TAService.deleteTA(id);
        return new ResponseEntity<>("TA deleted successfully", HttpStatus.OK);
    }
}
