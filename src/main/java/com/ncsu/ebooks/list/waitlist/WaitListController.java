package com.ncsu.ebooks.list.waitlist;
import com.ncsu.ebooks.course.activecourse.ACourseModel;
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

    @GetMapping("/faculty/{facultyID}")
    public ResponseEntity<Map<String, Object>> getAllWList(@PathVariable int facultyID) {
        Map<String, Object> response = new HashMap<>();
        List<WaitListRespModel> waitListResponse;
        try {
            waitListResponse = waitListService.getAllWList(facultyID);
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
    public ResponseEntity<WaitListModel> getWListById(@PathVariable int id) {
        WaitListModel wList = waitListService.getWListById(id);
        if (wList != null) {
            return new ResponseEntity<>(wList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<String> createWList(@RequestBody WaitListModel wList) {
        waitListService.createWList(wList);
        return new ResponseEntity<>("WList created successfully", HttpStatus.CREATED);
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
