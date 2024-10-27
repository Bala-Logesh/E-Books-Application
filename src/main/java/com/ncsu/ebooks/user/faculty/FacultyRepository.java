package com.ncsu.ebooks.user.faculty;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FacultyRepository {

    private final JdbcTemplate jdbcTemplate;

    public FacultyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<FacultyModel> findAll() {
        String sql = "SELECT * FROM Faculty";
        return jdbcTemplate.query(sql, new FacultyRM());
    }

    public FacultyModel findById(int id) {
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new FacultyRM(), id);
    }

    public void save(int userId) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void update(int id, int userId) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void delete(int id) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    private static class FacultyRM implements RowMapper<FacultyModel> {
        @Override
        public FacultyModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            FacultyModel Faculty = new FacultyModel();
            Faculty.setFacultyId(rs.getInt("facultyId"));
            Faculty.setUserId(rs.getInt("userId"));
            return Faculty;
        }
    }
}