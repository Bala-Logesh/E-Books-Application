package com.ncsu.ebooks.user.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserById(String id) {
        return userRepository.findById(id);
    }

    public UserModel getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean createUser(UserModel user) {
        System.out.println(user.toString());
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }


    public void updateUser(String id, UserModel user) {
        user.setUserID(id);
        userRepository.update(id, user);
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    public UserModel loginUser(UserLoginModel user) {
        return userRepository.loginUser(user);
    }
}
