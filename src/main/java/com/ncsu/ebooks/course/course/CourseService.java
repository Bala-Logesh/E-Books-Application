package com.ncsu.ebooks.course.course;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseRespModel> getAllCourses(int facultyID) {
        try {
            return courseRepository.findAll(facultyID);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving courses: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve courses", e);
        }
    }

    public CourseModel getCourseById(String id) {
        return courseRepository.findById(id);
    }

    public CourseModel getCourseByTitle(String title) {
        return courseRepository.findByTitle(title);
    }

    public boolean createCourse(CourseModel course) {
        try {
            courseRepository.save(course);
            return true;
        } catch (DataAccessException e) {
            System.err.println("Error creating course: " + e.getMessage());
            throw new RuntimeException("Failed to create course: " + e.getMessage(), e);
        }
    }

    public void updateCourse(String id, CourseModel course) {
        course.setCourseID(id);
        courseRepository.update(id, course);
    }

    public void deleteCourse(int id) {
        courseRepository.delete(id);
    }
}
