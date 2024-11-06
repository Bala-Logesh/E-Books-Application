package com.ncsu.ebooks.list.enrolledlist;
import com.ncsu.ebooks.list.waitlist.WaitListRespModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eList")
public class EnrolledListController {

    private final EnrolledListService enrolledListService;

    public EnrolledListController(EnrolledListService enrolledListService) {
        this.enrolledListService = enrolledListService;
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEList() {
        Map<String, Object> response = new HashMap<>();
        List<EnrolledListRespModel> enrollListResponse;
        try {
            enrollListResponse = enrolledListService.getAllELists();
            if (enrollListResponse != null && !enrollListResponse.isEmpty()) {
                response.put("message", "Enrolled List retrieved successfully");
                response.put("eList", enrollListResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No enrolled list found");
            response.put("message", "No enrolled list available");
            response.put("eList", new ArrayList<WaitListRespModel>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error retrieving enrolled list: " + e.getMessage());
            response.put("message", "Failed to retrieve enrolled list");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/faculty/{facultyID}")
    public ResponseEntity<Map<String, Object>> getAllEList(@PathVariable int facultyID) {
        Map<String, Object> response = new HashMap<>();
        List<EnrolledListRespModel> enrollListResponse;
        try {
            enrollListResponse = enrolledListService.getAllEListsByFacultyID(facultyID);
            if (enrollListResponse != null && !enrollListResponse.isEmpty()) {
                response.put("message", "Enrolled List retrieved successfully");
                response.put("eList", enrollListResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No enrolled list found");
            response.put("message", "No enrolled list available");
            response.put("eList", new ArrayList<WaitListRespModel>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error retrieving enrolled list: " + e.getMessage());
            response.put("message", "Failed to retrieve enrolled list");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrolledListModel> getEListById(@PathVariable int id) {
        EnrolledListModel eList = enrolledListService.getEListById(id);
        if (eList != null) {
            return new ResponseEntity<>(eList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/course/{courseID}")
    public ResponseEntity<Map<String, Object>> getEListByCourseId(@PathVariable int courseID) {
        Map<String, Object> response = new HashMap<>();
        List<EnrolledListModel> eLists = enrolledListService.getEListByCourseId(courseID);
        if (!eLists.isEmpty()) {
            response.put("message", "Enrolled List retrieved successfully");
            response.put("eList", eLists);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving enrolled list: ");
            response.put("message", "Failed to retrieve enrolled list");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/student/{studentID}")
    public ResponseEntity<Map<String, Object>> getEListByStudentId(@PathVariable int studentID) {
        Map<String, Object> response = new HashMap<>();
        List<EnrolledListModel> eLists = enrolledListService.getEListByStudentId(studentID);
        if (!eLists.isEmpty()) {
            response.put("message", "Enrolled List retrieved successfully");
            response.put("eList", eLists);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving enrolled list: ");
            response.put("message", "Failed to retrieve enrolled list");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createEList(@RequestBody EnrolledListModel eList) {
        enrolledListService.createEList(eList);
        return new ResponseEntity<>("EList created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEList(@PathVariable int id, @RequestBody EnrolledListModel eList) {
        enrolledListService.updateEList(id, eList);
        return new ResponseEntity<>("EList updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEList(@PathVariable int id) {
        enrolledListService.deleteEList(id);
        return new ResponseEntity<>("EList deleted successfully", HttpStatus.OK);
    }
}
