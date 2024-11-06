package com.ncsu.ebooks.list.waitlist;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WaitListService {

    private final WaitListRepository waitListRepository;

    public WaitListService(WaitListRepository waitListRepository) {
        this.waitListRepository = waitListRepository;
    }

    public List<WaitListRespModel> getAllWListByFacultyUserID(String userID) {
        try {
            return waitListRepository.findAllByFacultyUserID(userID);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving wait list: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public WaitListModel getWListById(int id) {
        try {
            return waitListRepository.findById(id);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving wait list: " + e.getMessage());
            throw new RuntimeException("Error retrieving wait list: " + e.getMessage());
        }
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
