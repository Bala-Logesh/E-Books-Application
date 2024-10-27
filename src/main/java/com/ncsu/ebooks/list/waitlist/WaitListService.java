package com.ncsu.ebooks.list.waitlist;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaitListService {

    private final WaitListRepository waitListRepository;

    public WaitListService(WaitListRepository waitListRepository) {
        this.waitListRepository = waitListRepository;
    }

    public List<WaitListModel> getAllWList() {
        return waitListRepository.findAll();
    }

    public WaitListModel getEListById(int id) {
        return waitListRepository.findById(id);
    }

    public List<WaitListModel> getEListByCourseId(int courseId) {
        return waitListRepository.findByCourseId(courseId);
    }

    public List<WaitListModel> getEListByStudentId(int studentId) {
        return waitListRepository.findByStudentId(studentId);
    }

    public void creatEList(WaitListModel eList) {
        waitListRepository.save(eList);
    }

    public void updateEList(int id, WaitListModel eList) {
        eList.setWaitListId(id);
        waitListRepository.update(id, eList);
    }

    public void deleteEList(int id) {
        waitListRepository.delete(id);
    }
}
