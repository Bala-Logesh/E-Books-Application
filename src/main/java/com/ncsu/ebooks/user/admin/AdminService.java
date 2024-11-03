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

        List<AdminModel> admins = adminRepository.findAll();
        for (AdminModel admin : admins) {
            admin.setUser(userService.getUserById(admin.getUserID()));
        }

        return admins;
    }

    public AdminModel getAdminById(int id) {
        AdminModel admin = adminRepository.findById(id);
        admin.setUser(userService.getUserById(admin.getUserID()));
        return admin;
    }

    public void createAdmin(UserModel user) {
        this.userService.createUser(user);
        String userID = user.getUserID();
        adminRepository.save(userID);
    }

    public void updateAdmin(int id, String userId) {
        adminRepository.update(id, userId);
    }

    public void deleteAdmin(int id) {
        adminRepository.delete(id);
    }
}
