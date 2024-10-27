package com.ncsu.ebooks.course.course;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseModel> getAllCourses() {
        return courseRepository.findAll();
    }

    public CourseModel getCourseById(int id) {
        return courseRepository.findById(id);
    }

    public CourseModel getCourseByTitle(String title) {
        return courseRepository.findByTitle(title);
    }

    public void createCourse(CourseModel course) {
        courseRepository.save(course);
    }

    public void updateCourse(int id, CourseModel course) {
        course.setCourseId(id);
        courseRepository.update(id, course);
    }

    public void deleteCourse(int id) {
        courseRepository.delete(id);
    }
}
