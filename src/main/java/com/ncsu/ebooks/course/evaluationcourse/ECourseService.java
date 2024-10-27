package com.ncsu.ebooks.course.evaluationcourse;

import com.ncsu.ebooks.course.course.CourseModel;
import com.ncsu.ebooks.course.course.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ECourseService {

    private final ECourseRepository ECourseRepository;
    private final CourseService courseService;

    public ECourseService(ECourseRepository ECourseRepository, CourseService courseService) {
        this.ECourseRepository = ECourseRepository;
        this.courseService = courseService;
    }

    public List<ECourseModel> getAllECourses() {
        return ECourseRepository.findAll();
    }

    public ECourseModel getECourseById(int id) {
        return ECourseRepository.findById(id);
    }

    public void createECourse(CourseModel course) {
        this.courseService.createCourse(course);
        String courseTitle = course.getTitle();
        int courseId = this.courseService.getCourseByTitle(courseTitle).getCourseId();
        ECourseRepository.save(courseId);
    }

    public void updateECourse(int id, int courseId) {
        ECourseRepository.update(id, courseId);
    }

    public void deleteECourse(int id) {
        ECourseRepository.delete(id);
    }
}
