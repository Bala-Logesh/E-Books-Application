package com.ncsu.ebooks.course.activecourse;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ACourseRepository {

    private final JdbcTemplate jdbcTemplate;

    public ACourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ACourseModel> findAll() {
        String sql = "SELECT * FROM ActiveCourse";
        try {
            return jdbcTemplate.query(sql, new ACourseRM());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving active courses: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve active courses", e);
        }
    }

    public ACourseModel findById(int id) {
        String sql = "SELECT activeCourseID, courseID, capacity, openToEnroll FROM ActiveCourse WHERE activeCourseID = ?";
        return jdbcTemplate.queryForObject(sql, new ACourseRM(), id);
    }

    public void save(String id, int capacity, String token, boolean openToEnroll) {
        String sql = "INSERT INTO ActiveCourse (courseID, capacity, openToEnroll, token) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, id, capacity, openToEnroll, token);
        } catch (DataAccessException e) {
            System.err.println("Error saving active course: " + e.getMessage());
            throw new RuntimeException("Failed to save active course: " + e.getMessage(), e);
        }
    }

    public void update(int id, int courseId) {
        String sql = "UPDATE ActiveCourse SET courseID = ? WHERE activeCourseID = ?";
        jdbcTemplate.update(sql, id, courseId);
    }

    public void delete(int id) {
        String sql = "DELETE FROM ActiveCourse WHERE activeCourseID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ACourseRM implements RowMapper<ACourseModel> {
        @Override
        public ACourseModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ACourseModel ACourse = new ACourseModel();
            ACourse.setActiveCourseID(rs.getInt("activeCourseID"));
            ACourse.setCourseID(rs.getString("courseID"));
            ACourse.setCapacity(rs.getInt("capacity"));
            ACourse.setToken(rs.getString("token"));
            ACourse.setOpenToEnroll(rs.getBoolean("openToEnroll"));
            return ACourse;
        }
    }
}