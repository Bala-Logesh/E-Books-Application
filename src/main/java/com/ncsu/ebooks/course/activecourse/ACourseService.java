package com.ncsu.ebooks.course.activecourse;

import com.ncsu.ebooks.book.chapter.ChapterModel;
import com.ncsu.ebooks.course.course.CourseModel;
import com.ncsu.ebooks.course.course.CourseService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        try {
            List<ACourseModel> aCourses = ACourseRepository.findAll();

            for (ACourseModel aCourse : aCourses) {
                try {
                    aCourse.setCourse(courseService.getCourseById(aCourse.getCourseID()));
                } catch (DataAccessException e) {
                    System.err.println("Error retrieving course for active course ID " + aCourse.getActiveCourseID() + ": " + e.getMessage());
                    aCourse.setCourse(new CourseModel());
                }
            }

            return aCourses;

        } catch (DataAccessException e) {
            System.err.println("Error retrieving active courses: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve active courses", e);
        }
    }

    public ACourseModel getACourseById(int id) {
        ACourseModel aCourse = ACourseRepository.findById(id);
        aCourse.setCourse(courseService.getCourseById(aCourse.getCourseID()));
        return aCourse;
    }

    public boolean createACourse(CourseModel course, int capacity, String token, boolean openToEnroll) {
        boolean success = this.courseService.createCourse(course);
        if (!success) {
            return false;
        }

        try {
            String courseId = course.getCourseID();
            ACourseRepository.save(courseId, capacity, token, openToEnroll);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating active course: " + e.getMessage());
            return false;
        }
    }

    public void updateACourse(int id, int courseId) {
        ACourseRepository.update(id, courseId);
    }

    public void deleteACourse(int id) {
        ACourseRepository.delete(id);
    }
}
