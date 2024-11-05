package com.ncsu.ebooks.list.enrolledlist;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrolledListService {

    private final EnrolledListRepository enrolledListRepository;

    public EnrolledListService(EnrolledListRepository enrolledListRepository) {
        this.enrolledListRepository = enrolledListRepository;
    }

    public List<EnrolledListRespModel> getAllELists(int facultyID) {
        try {
            return enrolledListRepository.findAll(facultyID);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving enrolled list: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public EnrolledListModel getEListById(int id) {
        return enrolledListRepository.findById(id);
    }

    public List<EnrolledListModel> getEListByCourseId(int courseID) {
        return enrolledListRepository.findByCourseId(courseID);
    }

    public List<EnrolledListModel> getEListByStudentId(int studentID) {
        return enrolledListRepository.findByStudentId(studentID);
    }

    public void createEList(EnrolledListModel eList) {
        enrolledListRepository.save(eList.getStudentID(), eList.getActiveCourseID());
    }

    public void updateEList(int id, EnrolledListModel eList) {
        eList.setEnrolledID(id);
        enrolledListRepository.update(id, eList.getStudentID(), eList.getActiveCourseID());
    }

    public void deleteEList(int id) {
        enrolledListRepository.delete(id);
    }
}
