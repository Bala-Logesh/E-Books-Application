package com.ncsu.ebooks.user.teachingassistant;

import com.ncsu.ebooks.user.admin.AdminModel;
import com.ncsu.ebooks.user.user.UserModel;
import com.ncsu.ebooks.user.user.UserService;
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
        List<TAModel> tAs = TARepository.findAll();
        for (TAModel TA : tAs) {
            TA.setUser(userService.getUserById(TA.getUserID()));
        }

        return tAs;
    }

    public TAModel getTAById(int id) {
        TAModel TA = TARepository.findById(id);
        TA.setUser(userService.getUserById(TA.getUserID()));
        return TA;
    }

    public void createTA(UserModel user, int activeCourseID, boolean resetPassword) {
        this.userService.createUser(user);
        String userID = user.getUserID();
        TARepository.save(userID, activeCourseID, resetPassword);
    }

    public void updateTA(int id, String userId) {
        TARepository.update(id, userId);
    }

    public void deleteTA(int id) {
        TARepository.delete(id);
    }
}
