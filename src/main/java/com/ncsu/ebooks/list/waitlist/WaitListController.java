package com.ncsu.ebooks.list.waitlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wList")
public class WaitListController {

    private final WaitListService waitListService;

    public WaitListController(WaitListService waitListService) {
        this.waitListService = waitListService;
    }

    @GetMapping("/faculty/{userID}")
    public ResponseEntity<Map<String, Object>> getAllWList(@PathVariable String userID) {
        Map<String, Object> response = new HashMap<>();
        List<WaitListRespModel> waitListResponse;
        try {
            waitListResponse = waitListService.getAllWListByFacultyUserID(userID);
            if (waitListResponse != null && !waitListResponse.isEmpty()) {
                response.put("message", "Wait List retrieved successfully");
                response.put("wList", waitListResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            System.err.println("No wait list found");
            response.put("message", "No wait list available");
            response.put("wList", new ArrayList<WaitListRespModel>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error retrieving wait list: " + e.getMessage());
            response.put("message", "Failed to retrieve wait list");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getWListById(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        WaitListModel wList = waitListService.getWListById(id);
        if (wList != null) {
            response.put("message", "Wait List retrieved successfully");
            response.put("wList", wList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.err.println("Error retrieving wait list: ");
            response.put("message", "Failed to retrieve wait list");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/course/{courseID}")
    public ResponseEntity<List<WaitListModel>> getWListByCourseId(@PathVariable int courseID) {
        List<WaitListModel> wList = waitListService.getWListByCourseId(courseID);
        if (!wList.isEmpty()) {
            return new ResponseEntity<>(wList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/student/{studentID}")
    public ResponseEntity<List<WaitListModel>> getWListByStudentId(@PathVariable int studentID) {
        List<WaitListModel> wList = waitListService.getWListByStudentId(studentID);
        if (!wList.isEmpty()) {
            return new ResponseEntity<>(wList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createWList(@RequestBody WaitListModel wList) {
        boolean success = waitListService.createWList(wList);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Added to waitlist successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        System.err.println("Error adding to wait list: ");
        response.put("message", "Failed to add to wait list");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateWList(@PathVariable int id, @RequestBody WaitListModel wList) {
        waitListService.updateWList(id, wList);
        return new ResponseEntity<>("WList updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWList(@PathVariable int id) {
        waitListService.deleteWList(id);
        return new ResponseEntity<>("WList deleted successfully", HttpStatus.OK);
    }
}
