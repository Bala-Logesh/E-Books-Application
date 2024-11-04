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

    public WaitListModel getWListById(int id) {
        return waitListRepository.findById(id);
    }

    public List<WaitListModel> getWListByCourseId(int activeCourseID) {
        return waitListRepository.findByCourseId(activeCourseID);
    }

    public List<WaitListModel> getWListByStudentId(int studentID) {
        return waitListRepository.findByStudentId(studentID);
    }

    public void createWList(WaitListModel wList) {
        waitListRepository.save(wList.getStudentID(), wList.getActiveCourseID());
    }

    public void updateWList(int id, WaitListModel wList) {
        wList.setWaitListID(id);
        waitListRepository.update(id, wList.getStudentID(), wList.getActiveCourseID());
    }

    public void deleteWList(int id) {
        waitListRepository.delete(id);
    }
}
