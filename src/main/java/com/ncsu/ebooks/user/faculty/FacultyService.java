package com.ncsu.ebooks.user.faculty;

import com.ncsu.ebooks.book.chapter.ChapterModel;
import com.ncsu.ebooks.user.teachingassistant.TAModel;
import com.ncsu.ebooks.user.user.UserModel;
import com.ncsu.ebooks.user.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        try {
            List<FacultyModel> faculties = facultyRepository.findAll();

            for (FacultyModel faculty : faculties) {
                try {
                    faculty.setUser(userService.getUserById(faculty.getUserID()));
                } catch (DataAccessException e) {
                    System.err.println("Error retrieving user for faculty ID " + faculty.getFacultyID() + ": " + e.getMessage());
                    faculty.setUser(new UserModel());
                }
            }

            return faculties;

        } catch (DataAccessException e) {
            System.err.println("Error retrieving faculties: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve faculties", e);
        }
    }

    public FacultyModel getFacultyById(int id) {
        FacultyModel faculty = facultyRepository.findById(id);
        faculty.setUser(userService.getUserById(faculty.getUserID()));
        return faculty;
    }

    public boolean createFaculty(UserModel user) {
        boolean success = this.userService.createUser(user);
        if (!success) {
            return false;
        }

        try {
            String userID = user.getUserID();
            facultyRepository.save(userID);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating faculty: " + e.getMessage());
            return false;
        }
    }

    public void updateFaculty(int id, String userID) {
        facultyRepository.update(id, userID);
    }

    public void deleteFaculty(int id) {
        facultyRepository.delete(id);
    }

    public FacultyModel getFacultyByUserID(String userID) {
        try {
            return facultyRepository.findByUserID(userID);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving faculty: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve faculty", e);
        }
    }
}
