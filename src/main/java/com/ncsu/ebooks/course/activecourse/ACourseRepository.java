package com.ncsu.ebooks.course.activecourse;

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
        return jdbcTemplate.query(sql, new ACourseRM());
    }

    public ACourseModel findById(int id) {
        String sql = "SELECT activeCourseid, courseID, capacity, openToEnroll FROM ActiveCourse WHERE activeCourseID = ?";
        return jdbcTemplate.queryForObject(sql, new ACourseRM(), id);
    }

    public void save(int id) {
        String sql = "INSERT INTO ActiveCourse (activeCourseID, courseID, capacity) VALUES (?, ?, ?)"; // not sure how to do this
        jdbcTemplate.update(sql, id, 0, 0);
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
            ACourse.setACourseId(rs.getInt("aCourseId"));
            ACourse.setCourseId(rs.getInt("courseId"));
            ACourse.setCapacity(rs.getInt("capacity"));
            ACourse.setToken(rs.getString("token"));
            ACourse.setOpenToEnroll(rs.getBoolean("openToEnroll"));
            return ACourse;
        }
    }
}