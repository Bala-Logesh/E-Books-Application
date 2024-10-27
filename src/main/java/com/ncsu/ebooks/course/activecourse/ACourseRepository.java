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
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new ACourseRM(), id);
    }

    public void save(int courseId) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void update(int id, int courseId) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void delete(int id) {
        String sql = "";
        jdbcTemplate.update(sql);
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