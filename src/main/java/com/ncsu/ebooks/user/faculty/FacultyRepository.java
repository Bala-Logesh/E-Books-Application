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
        String sql = "SELECT * FROM Faculty WHERE facultyId = ?";
        return jdbcTemplate.queryForObject(sql, new FacultyRM(), id);
    }

    public void save(String userId) {
        String sql = "INSERT INTO Faculty (userID) VALUES (?)";
        jdbcTemplate.update(sql, userId);
    }

    public void update(int id, String userId) {
        String sql = "UPDATE Faculty SET userId = ? WHERE facultyId = ?";
        jdbcTemplate.update(sql, userId, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Faculty WHERE facultyId = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class FacultyRM implements RowMapper<FacultyModel> {
        @Override
        public FacultyModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            FacultyModel Faculty = new FacultyModel();
            Faculty.setFacultyID(rs.getInt("facultyID"));
            Faculty.setUserID(rs.getString("userID"));
            return Faculty;
        }
    }
}