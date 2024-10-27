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

    public List<EnrolledListModel> getEListByCourseId(int courseId) {
        return enrolledListRepository.findByCourseId(courseId);
    }

    public List<EnrolledListModel> getEListByStudentId(int studentId) {
        return enrolledListRepository.findByStudentId(studentId);
    }

    public void creatEList(EnrolledListModel eList) {
        enrolledListRepository.save(eList);
    }

    public void updateEList(int id, EnrolledListModel eList) {
        eList.setEnrollmentId(id);
        enrolledListRepository.update(id, eList);
    }

    public void deleteEList(int id) {
        enrolledListRepository.delete(id);
    }
}
