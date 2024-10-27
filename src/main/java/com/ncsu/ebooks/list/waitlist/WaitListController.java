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
    public ResponseEntity<WaitListModel> getEListById(@PathVariable int id) {
        WaitListModel eList = waitListService.getEListById(id);
        if (eList != null) {
            return new ResponseEntity<>(eList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<List<WaitListModel>> getEListByCourseId(@PathVariable int courseId) {
        List<WaitListModel> wList = waitListService.getEListByCourseId(courseId);
        if (!wList.isEmpty()) {
            return new ResponseEntity<>(wList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<WaitListModel>> getEListByStudentId(@PathVariable int studentId) {
        List<WaitListModel> wList = waitListService.getEListByStudentId(studentId);
        if (!wList.isEmpty()) {
            return new ResponseEntity<>(wList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createEList(@RequestBody WaitListModel eList) {
        waitListService.creatEList(eList);
        return new ResponseEntity<>("EList created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEList(@PathVariable int id, @RequestBody WaitListModel eList) {
        waitListService.updateEList(id, eList);
        return new ResponseEntity<>("EList updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEList(@PathVariable int id) {
        waitListService.deleteEList(id);
        return new ResponseEntity<>("EList deleted successfully", HttpStatus.OK);
    }
}
