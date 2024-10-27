package com.ncsu.ebooks.user.admin;
import com.ncsu.ebooks.user.user.Role;
import com.ncsu.ebooks.user.user.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<AdminModel> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminModel> getAdminById(@PathVariable int id) {
        AdminModel admin = adminService.getAdminById(id);
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createAdmin(@RequestBody UserModel user) {
        user.setRole(Role.ADMIN);
        adminService.createAdmin(user);
        return new ResponseEntity<>("Admin created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAdmin(@PathVariable int id, @RequestBody int userId) {
        adminService.updateAdmin(id, userId);
        return new ResponseEntity<>("Admin updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable int id) {
        adminService.deleteAdmin(id);
        return new ResponseEntity<>("Admin deleted successfully", HttpStatus.OK);
    }
}
