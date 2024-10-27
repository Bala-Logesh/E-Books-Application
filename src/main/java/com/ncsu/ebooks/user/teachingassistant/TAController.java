package com.ncsu.ebooks.user.teachingassistant;
import com.ncsu.ebooks.user.user.Role;
import com.ncsu.ebooks.user.user.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tas")
public class TAController {

    private final TAService TAService;

    public TAController(TAService TAService) {
        this.TAService = TAService;
    }

    @GetMapping
    public List<TAModel> getAllTAs() {
        return TAService.getAllTAs();
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

    @PostMapping
    public ResponseEntity<String> createTA(@RequestBody UserModel user) {
        user.setRole(Role.TEACHING_ASSISTANT);
        TAService.createTA(user);
        return new ResponseEntity<>("TA created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTA(@PathVariable int id, @RequestBody int userId) {
        TAService.updateTA(id, userId);
        return new ResponseEntity<>("TA updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTA(@PathVariable int id) {
        TAService.deleteTA(id);
        return new ResponseEntity<>("TA deleted successfully", HttpStatus.OK);
    }
}
