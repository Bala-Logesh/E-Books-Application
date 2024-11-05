package com.ncsu.ebooks.user.user;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable String id) {
        UserModel user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createUser(@RequestBody UserModel user) {
        boolean success = userService.createUser(user);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "User created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        System.err.println("Error creating user");
        response.put("message", "Failed to create user");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserLoginModel user) {
        UserModel loggedInUser = userService.loginUser(user);
        Map<String, Object> response = new HashMap<>();
        if (loggedInUser != null) {
            response.put("message", "User logged in successfully");
            response.put("user", loggedInUser);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("message", "User login failed! Invalid Credentials");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody UserChangePwdModel user) {
        boolean success = userService.changePassword(user);
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Password changed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("message", "Password change failed! Invalid Credentials");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserModel user) {
        userService.updateUser(id, user);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
