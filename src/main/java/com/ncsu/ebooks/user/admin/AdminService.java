package com.ncsu.ebooks.user.admin;

import com.ncsu.ebooks.user.user.UserModel;
import com.ncsu.ebooks.user.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService;

    public AdminService(AdminRepository adminRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
    }

    public List<AdminModel> getAllAdmins() {
        return adminRepository.findAll();
    }

    public AdminModel getAdminById(int id) {
        return adminRepository.findById(id);
    }

    public void createAdmin(UserModel user) {
        this.userService.createUser(user);
        String userEmail = user.getEmail();
        int userId = this.userService.getUserByEmail(userEmail).getUserId();
        adminRepository.save(userId);
    }

    public void updateAdmin(int id, int userId) {
        adminRepository.update(id, userId);
    }

    public void deleteAdmin(int id) {
        adminRepository.delete(id);
    }
}
