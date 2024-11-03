package com.ncsu.ebooks.user.student;

import com.ncsu.ebooks.user.admin.AdminModel;
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
        List<StudentModel> students = studentRepository.findAll();
        for (StudentModel student : students) {
            student.setUser(userService.getUserById(student.getUserID()));
        }

        return students;
    }

    public StudentModel getStudentById(int id) {
        StudentModel student = studentRepository.findById(id);
        student.setUser(userService.getUserById(student.getUserID()));
        return student;
    }

    public void createStudent(UserModel user) {
        this.userService.createUser(user);
        String userID = user.getUserID();
        studentRepository.save(userID);
    }

    public void updateStudent(int id, String userId) {
        studentRepository.update(id, userId);
    }

    public void deleteStudent(int id) {
        studentRepository.delete(id);
    }
}
