package com.ncsu.ebooks.course.activecourse;

import com.ncsu.ebooks.course.course.CourseModel;
import com.ncsu.ebooks.course.course.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ACourseService {

    private final ACourseRepository ACourseRepository;
    private final CourseService courseService;

    public ACourseService(ACourseRepository ACourseRepository, CourseService courseService) {
        this.ACourseRepository = ACourseRepository;
        this.courseService = courseService;
    }

    public List<ACourseModel> getAllACourses() {
        return ACourseRepository.findAll();
    }

    public ACourseModel getACourseById(int id) {
        return ACourseRepository.findById(id);
    }

    public void createACourse(CourseModel course) {
        this.courseService.createCourse(course);
        String courseTitle = course.getTitle();
        int courseId = this.courseService.getCourseByTitle(courseTitle).getCourseId();
        ACourseRepository.save(courseId);
    }

    public void updateACourse(int id, int courseId) {
        ACourseRepository.update(id, courseId);
    }

    public void deleteACourse(int id) {
        ACourseRepository.delete(id);
    }
}
