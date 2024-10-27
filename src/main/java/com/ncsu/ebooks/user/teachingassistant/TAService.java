package com.ncsu.ebooks.user.teachingassistant;

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
        return TARepository.findAll();
    }

    public TAModel getTAById(int id) {
        return TARepository.findById(id);
    }

    public void createTA(UserModel user) {
        this.userService.createUser(user);
        String userEmail = user.getEmail();
        int userId = this.userService.getUserByEmail(userEmail).getUserId();
        TARepository.save(userId);
    }

    public void updateTA(int id, int userId) {
        TARepository.update(id, userId);
    }

    public void deleteTA(int id) {
        TARepository.delete(id);
    }
}
