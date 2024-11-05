package com.ncsu.ebooks.user.student;

import com.ncsu.ebooks.user.admin.AdminModel;
import com.ncsu.ebooks.user.faculty.FacultyModel;
import com.ncsu.ebooks.user.user.UserModel;
import com.ncsu.ebooks.user.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;

    public StudentService(StudentRepository studentRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    public List<StudentModel> getAllStudents() {
        try {
            List<StudentModel> students = studentRepository.findAll();

            for (StudentModel student : students) {
                try {
                    student.setUser(userService.getUserById(student.getUserID()));
                } catch (DataAccessException e) {
                    System.err.println("Error retrieving user for student ID " + student.getStudentID() + ": " + e.getMessage());
                    student.setUser(new UserModel());
                }
            }

            return students;

        } catch (DataAccessException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve students", e);
        }
    }

    public StudentModel getStudentById(int id) {
        StudentModel student = studentRepository.findById(id);
        student.setUser(userService.getUserById(student.getUserID()));
        return student;
    }

    public boolean createStudent(UserModel user) {
        boolean success = this.userService.createUser(user);
        if (!success) {
            return false;
        }

        try {
            String userID = user.getUserID();
            studentRepository.save(userID);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating student: " + e.getMessage());
            return false;
        }
    }

    public void updateStudent(int id, String userId) {
        studentRepository.update(id, userId);
    }

    public void deleteStudent(int id) {
        studentRepository.delete(id);
    }
}
