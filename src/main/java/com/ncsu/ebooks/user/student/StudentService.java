package com.ncsu.ebooks.user.student;

import com.ncsu.ebooks.user.user.UserModel;
import com.ncsu.ebooks.user.user.UserService;
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
        return studentRepository.findAll();
    }

    public StudentModel getStudentById(int id) {
        return studentRepository.findById(id);
    }

    public void createStudent(UserModel user) {
        this.userService.createUser(user);
        String userEmail = user.getEmail();
        int userId = this.userService.getUserByEmail(userEmail).getUserId();
        studentRepository.save(userId);
    }

    public void updateStudent(int id, int userId) {
        studentRepository.update(id, userId);
    }

    public void deleteStudent(int id) {
        studentRepository.delete(id);
    }
}
