package com.ncsu.ebooks.list.waitlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wList")
public class WaitListController {

    private final WaitListService waitListService;

    public WaitListController(WaitListService waitListService) {
        this.waitListService = waitListService;
    }

    @GetMapping
    public List<WaitListModel> getAllWList() {
        return waitListService.getAllWList();
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
