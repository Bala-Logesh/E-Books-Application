package com.ncsu.ebooks.user.teachingassistant;

import com.ncsu.ebooks.user.faculty.FacultyRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TARepository {

    private final JdbcTemplate jdbcTemplate;

    public TARepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TAModel> findAll() {
        String sql = "SELECT * FROM TeachingAssistant";
        try {
            return jdbcTemplate.query(sql, new TARM());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving TAs: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve TAs", e);
        }
    }

    public TAModel findById(int id) {
        String sql = "SELECT * FROM TeachingAssistant WHERE teachingAsstID = ?";
        return jdbcTemplate.queryForObject(sql, new TARM(), id);
    }

    public TAModel findByUserID(String userID) {
        String sql = "SELECT * FROM TeachingAssistant WHERE userID = ?";
        try{
            return jdbcTemplate.queryForObject(sql, new TARM(), userID);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving TA: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve TA", e);
        }

    }

    public void save(String userId, int activeCourseID, boolean resetPassword) {
        String sql = "INSERT INTO TeachingAssistant (userID, activeCourseID, resetPassword) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, userId, activeCourseID, resetPassword);
        } catch (DataAccessException e) {
            System.err.println("Error saving TA: " + e.getMessage());
            throw new RuntimeException("Failed to save TA: " + e.getMessage(), e);
        }
    }

    public void update(int id, String userId) {
        String sql = "UPDATE TeachingAssistant SET userId = ? WHERE teachingAsstID = ?";
        jdbcTemplate.update(sql, userId, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM TeachingAssistant WHERE teachingAsstID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class TARM implements RowMapper<TAModel> {
        @Override
        public TAModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            TAModel TA = new TAModel();
            TA.setTeachingAsstID(rs.getInt("teachingAsstID"));
            TA.setUserID(rs.getString("userID"));
            TA.setActiveCourseID(rs.getInt("activeCourseID"));
            TA.setResetPassword(rs.getBoolean("resetPassword"));
            return TA;
        }
    }
}