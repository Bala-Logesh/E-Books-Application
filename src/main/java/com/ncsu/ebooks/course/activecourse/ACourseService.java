package com.ncsu.ebooks.course.activecourse;

import com.ncsu.ebooks.book.chapter.ChapterModel;
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
        List<ACourseModel> aCourses = ACourseRepository.findAll();
        for (ACourseModel aCourse : aCourses) {
            aCourse.setCourse(courseService.getCourseById(aCourse.getCourseID()));
        }

        return aCourses;
    }

    public ACourseModel getACourseById(int id) {
        ACourseModel aCourse = ACourseRepository.findById(id);
        aCourse.setCourse(courseService.getCourseById(aCourse.getCourseID()));
        return aCourse;
    }

    public void createACourse(CourseModel course, int capacity, String token, boolean openToEnroll) {
        this.courseService.createCourse(course);
        String courseId = course.getCourseID();
        ACourseRepository.save(courseId, capacity, token, openToEnroll);
    }

    public void updateACourse(int id, int courseId) {
        ACourseRepository.update(id, courseId);
    }

    public void deleteACourse(int id) {
        ACourseRepository.delete(id);
    }
}
