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

    public UserModel getUserById(int id) {
        return userRepository.findById(id);
    }

    public UserModel getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createUser(UserModel user) {
        userRepository.save(user);
    }

    public void updateUser(int id, UserModel user) {
        user.setUserId(id);
        userRepository.update(id, user);
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }
}
