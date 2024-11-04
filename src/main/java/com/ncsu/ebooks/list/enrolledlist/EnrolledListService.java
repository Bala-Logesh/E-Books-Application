package com.ncsu.ebooks.list.enrolledlist;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrolledListService {

    private final EnrolledListRepository enrolledListRepository;

    public EnrolledListService(EnrolledListRepository enrolledListRepository) {
        this.enrolledListRepository = enrolledListRepository;
    }

    public List<EnrolledListModel> getAllELists() {
        return enrolledListRepository.findAll();
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
