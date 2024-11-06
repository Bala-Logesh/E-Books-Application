package com.ncsu.ebooks.user.teachingassistant;

import com.ncsu.ebooks.user.admin.AdminModel;
import com.ncsu.ebooks.user.faculty.FacultyModel;
import com.ncsu.ebooks.user.user.UserModel;
import com.ncsu.ebooks.user.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TAService {

    private final TARepository TARepository;
    private final UserService userService;

    public TAService(TARepository TARepository, UserService userService) {
        this.TARepository = TARepository;
        this.userService = userService;
    }

    public List<TAModel> getAllTAs() {
        try {
            List<TAModel> tAs = TARepository.findAll();

            for (TAModel TA : tAs) {
                try {
                    TA.setUser(userService.getUserById(TA.getUserID()));
                } catch (DataAccessException e) {
                    System.err.println("Error retrieving user for TA ID " + TA.getTeachingAsstID() + ": " + e.getMessage());
                    TA.setUser(new UserModel());
                }
            }

            return tAs;

        } catch (DataAccessException e) {
            System.err.println("Error retrieving TAs: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve TAs", e);
        }
    }

    public TAModel getTAById(int id) {
        TAModel TA = TARepository.findById(id);
        TA.setUser(userService.getUserById(TA.getUserID()));
        return TA;
    }

    public TAModel getTAByUserId(String userID) {
        try {
            TAModel TA = TARepository.findByUserID(userID);
            TA.setUser(userService.getUserById(TA.getUserID()));
            return TA;
        }  catch (DataAccessException e) {
            System.err.println("Error retrieving TA: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve TA", e);
        }

    }

    public boolean createTA(UserModel user, int activeCourseID, boolean resetPassword) {
        boolean success = this.userService.createUser(user);
        if (!success) {
            return false;
        }

        try {
            String userID = user.getUserID();
            TARepository.save(userID, activeCourseID, resetPassword);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating TA: " + e.getMessage());
            return false;
        }
    }

    public void updateTA(int id, String userId) {
        TARepository.update(id, userId);
    }

    public void deleteTA(int id) {
        TARepository.delete(id);
    }
}
