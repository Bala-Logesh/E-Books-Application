package com.ncsu.ebooks.list.enrolledlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eLists")
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

    @GetMapping("/{courseId}")
    public ResponseEntity<List<EnrolledListModel>> getEListByCourseId(@PathVariable int courseId) {
        List<EnrolledListModel> eLists = enrolledListService.getEListByCourseId(courseId);
        if (!eLists.isEmpty()) {
            return new ResponseEntity<>(eLists, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<EnrolledListModel>> getEListByStudentId(@PathVariable int studentId) {
        List<EnrolledListModel> eLists = enrolledListService.getEListByStudentId(studentId);
        if (!eLists.isEmpty()) {
            return new ResponseEntity<>(eLists, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createEList(@RequestBody EnrolledListModel eList) {
        enrolledListService.creatEList(eList);
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
