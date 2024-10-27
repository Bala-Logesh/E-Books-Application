package com.ncsu.ebooks.user.faculty;

import com.ncsu.ebooks.user.user.UserModel;
import com.ncsu.ebooks.user.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final UserService userService;

    public FacultyService(FacultyRepository facultyRepository, UserService userService) {
        this.facultyRepository = facultyRepository;
        this.userService = userService;
    }

    public List<FacultyModel> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public FacultyModel getFacultyById(int id) {
        return facultyRepository.findById(id);
    }

    public void createFaculty(UserModel user) {
        this.userService.createUser(user);
        String userEmail = user.getEmail();
        int userId = this.userService.getUserByEmail(userEmail).getUserId();
        facultyRepository.save(userId);
    }

    public void updateFaculty(int id, int userId) {
        facultyRepository.update(id, userId);
    }

    public void deleteFaculty(int id) {
        facultyRepository.delete(id);
    }
}
