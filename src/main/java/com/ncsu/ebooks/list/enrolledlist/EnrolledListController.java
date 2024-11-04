package com.ncsu.ebooks.list.enrolledlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eList")
public class EnrolledListController {

    private final EnrolledListService enrolledListService;

    public EnrolledListController(EnrolledListService enrolledListService) {
        this.enrolledListService = enrolledListService;
    }

    @GetMapping
    public List<EnrolledListModel> getAllELists() {
        return enrolledListService.getAllELists();
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
    public ResponseEntity<List<EnrolledListModel>> getEListByCourseId(@PathVariable int courseID) {
        List<EnrolledListModel> eLists = enrolledListService.getEListByCourseId(courseID);
        if (!eLists.isEmpty()) {
            return new ResponseEntity<>(eLists, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/student/{studentID}")
    public ResponseEntity<List<EnrolledListModel>> getEListByStudentId(@PathVariable int studentID) {
        List<EnrolledListModel> eLists = enrolledListService.getEListByStudentId(studentID);
        if (!eLists.isEmpty()) {
            return new ResponseEntity<>(eLists, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
