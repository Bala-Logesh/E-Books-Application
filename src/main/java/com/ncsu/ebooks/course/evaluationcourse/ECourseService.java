package com.ncsu.ebooks.course.evaluationcourse;

import com.ncsu.ebooks.course.activecourse.ACourseModel;
import com.ncsu.ebooks.course.course.CourseModel;
import com.ncsu.ebooks.course.course.CourseService;
import org.springframework.dao.DataAccessException;
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
        try {
            List<ECourseModel> eCourses = ECourseRepository.findAll();

            for (ECourseModel eCourse : eCourses) {
                try {
                    eCourse.setCourse(courseService.getCourseById(eCourse.getCourseID()));
                } catch (DataAccessException e) {
                    System.err.println("Error retrieving course for evaluation course ID " + eCourse.getEvaluationID() + ": " + e.getMessage());
                    eCourse.setCourse(new CourseModel());
                }
            }

            return eCourses;

        } catch (DataAccessException e) {
            System.err.println("Error retrieving evaluation courses: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve evaluation courses", e);
        }
    }

    public ECourseModel getECourseById(int id) {
        ECourseModel eCourse = ECourseRepository.findById(id);
        eCourse.setCourse(courseService.getCourseById(eCourse.getCourseID()));
        return eCourse;
    }

    public boolean createECourse(CourseModel course) {
        boolean success = this.courseService.createCourse(course);
        if (!success) {
            return false;
        }

        try {
            String courseId = course.getCourseID();
            ECourseRepository.save(courseId);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating evaluation course: " + e.getMessage());
            return false;
        }
    }

    public void updateECourse(int id, int courseId) {
        ECourseRepository.update(id, courseId);
    }

    public void deleteECourse(int id) {
        ECourseRepository.delete(id);
    }
}
