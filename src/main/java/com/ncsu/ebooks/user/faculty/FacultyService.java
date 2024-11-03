package com.ncsu.ebooks.user.faculty;

import com.ncsu.ebooks.user.teachingassistant.TAModel;
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
        List<FacultyModel> faculties = facultyRepository.findAll();
        for (FacultyModel faculty : faculties) {
            faculty.setUser(userService.getUserById(faculty.getUserID()));
        }

        return faculties;
    }

    public FacultyModel getFacultyById(int id) {
        FacultyModel faculty = facultyRepository.findById(id);
        faculty.setUser(userService.getUserById(faculty.getUserID()));
        return faculty;
    }

    public void createFaculty(UserModel user) {
        this.userService.createUser(user);
        String userID = user.getUserID();
        facultyRepository.save(userID);
    }

    public void updateFaculty(int id, String userID) {
        facultyRepository.update(id, userID);
    }

    public void deleteFaculty(int id) {
        facultyRepository.delete(id);
    }
}
