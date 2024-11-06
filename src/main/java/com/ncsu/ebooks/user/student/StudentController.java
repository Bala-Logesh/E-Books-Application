package com.ncsu.ebooks.user.student;
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
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStudents() {
        Map<String, Object> response = new HashMap<>();
        List<StudentModel> studentResponse;

        try {
            studentResponse = studentService.getAllStudents();
            if (studentResponse != null && !studentResponse.isEmpty()) {
                response.put("message", "Students retrieved successfully");
                response.put("students", studentResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No students found");
            response.put("message", "No students available");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error retrieving students: " + e.getMessage());
            response.put("message", "Failed to retrieve students");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentModel> getStudentById(@PathVariable int id) {
        StudentModel student = studentService.getStudentById(id);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String, Object>> getStudentById(@RequestBody StudentReqModel studentReq) {
        StudentModel student = studentService.getStudentByParams(studentReq);
        Map<String, Object> response = new HashMap<>();
        if (student != null) {
            response.put("message", "Student retrieved successfully");
            response.put("student", student);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving students: ");
            response.put("message", "Failed to retrieve student");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/user/{userID}")
    public ResponseEntity<Map<String, Object>> getStudentByUserID(@PathVariable String userID) {
        StudentModel student = studentService.getStudentByUserId(userID);
        Map<String, Object> response = new HashMap<>();
        if (student != null) {
            response.put("message", "Student retrieved successfully");
            response.put("student", student);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving students: ");
            response.put("message", "Failed to retrieve student");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createStudent(@RequestBody UserModel user) {
        user.setRole(Role.STUDENT);
        boolean success = studentService.createStudent(user);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Student created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating student");
        response.put("message", "Failed to create student");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable int id, @RequestBody String userId) {
        studentService.updateStudent(id, userId);
        return new ResponseEntity<>("Student updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
    }
}
