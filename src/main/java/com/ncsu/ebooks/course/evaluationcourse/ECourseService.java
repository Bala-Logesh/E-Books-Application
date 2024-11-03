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
        List<ECourseModel> eCourses = ECourseRepository.findAll();
        for (ECourseModel eCourse : eCourses) {
            eCourse.setCourse(courseService.getCourseById(eCourse.getCourseID()));
        }

        return eCourses;
    }

    public ECourseModel getECourseById(int id) {
        ECourseModel eCourse = ECourseRepository.findById(id);
        eCourse.setCourse(courseService.getCourseById(eCourse.getCourseID()));
        return eCourse;
    }

    public void createECourse(CourseModel course) {
        this.courseService.createCourse(course);
        String courseId = course.getCourseID();
        ECourseRepository.save(courseId);
    }

    public void updateECourse(int id, int courseId) {
        ECourseRepository.update(id, courseId);
    }

    public void deleteECourse(int id) {
        ECourseRepository.delete(id);
    }
}
