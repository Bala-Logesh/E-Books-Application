package com.ncsu.ebooks.course.course;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    public CourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CourseModel> findAll() {
        String sql = "SELECT * FROM Course";
        return jdbcTemplate.query(sql, new CourseRM());
    }

    public CourseModel findById(int id) {
        String sql = "SELECT courseID, title, facultyID, startDate, endDate, eTextBookId FROM Course WHERE courseID = ?";
        return jdbcTemplate.queryForObject(sql, new CourseRM(), id);
    }

    public CourseModel findByTitle(String title) {
        String sql = "SELECT courseID, title, facultyID, startDate, endDate, eTextBookID FROM Course WHERE title = ?";
        return jdbcTemplate.queryForObject(sql, new CourseRM(), title);
    }

    public void save(CourseModel course) {
        String sql = "INSERT INTO Course (courseID, title, facultyID, startDate, endDate, eTextBookID) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, course.getCourseId(), course.getTitle(), course.getFacultyId(), course.getStartDate(), course.getEndDate(), course.getETextBookId());
    }

    public void update(int id, CourseModel course) {
        String sql = "UPDATE Course SET courseID = ?, title = ?, facultyID = ?, startDate = ?, endDate = ?, eTextBookID = ? WHERE courseID = ?";
        jdbcTemplate.update(sql, course.getCourseId(), course.getTitle(), course.getFacultyId(), course.getStartDate(), course.getEndDate(), course.getETextBookId(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Course WHERE courseID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class CourseRM implements RowMapper<CourseModel> {
        @Override
        public CourseModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            CourseModel Course = new CourseModel();
            Course.setCourseId(rs.getInt("courseId"));
            Course.setTitle(rs.getString("title"));
            Course.setFacultyId(rs.getInt("facultyId"));
            Course.setStartDate(rs.getTimestamp("startDate"));
            Course.setEndDate(rs.getTimestamp("endDate"));
            Course.setETextBookId(rs.getInt("eTextBookId"));
            return Course;
        }
    }
}