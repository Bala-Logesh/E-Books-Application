package com.ncsu.ebooks.user.student;

import com.ncsu.ebooks.user.admin.AdminRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<StudentModel> findAll() {
        String sql = "SELECT * FROM Student";
        try {
            return jdbcTemplate.query(sql, new StudentRM());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve students", e);
        }
    }

    public StudentModel findById(int id) {
        String sql = "SELECT * FROM Student WHERE studentID = ?";
        return jdbcTemplate.queryForObject(sql, new StudentRM(), id);
    }


    public StudentModel findByUserID(String userID) {
        String sql = "SELECT * FROM Student WHERE userID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new StudentRM(), userID);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve student", e);
        }
    }

    public StudentModel findByUserByParams(StudentReqModel studentReq) {
        String sql = "SELECT * FROM Student " +
                "JOIN User ON Student.userID = User.userID " +
                "WHERE User.firstName = ? AND " +
                "User.lastName = ? AND " +
                "User.email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new StudentRM(), studentReq.getFirstName(), studentReq.getLastName(), studentReq.getEmail());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve student", e);
        }
    }

    public void save(String userId) {
        String sql = "INSERT INTO Student (userID) VALUES (?)";
        try {
            jdbcTemplate.update(sql, userId);
        } catch (DataAccessException e) {
            System.err.println("Error saving student: " + e.getMessage());
            throw new RuntimeException("Failed to save student: " + e.getMessage(), e);
        }
    }

    public void update(int id, String userId) {
        String sql = "UPDATE Student SET userId = ? WHERE studentID = ?";
        jdbcTemplate.update(sql, userId, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Student WHERE studentID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class StudentRM implements RowMapper<StudentModel> {
        @Override
        public StudentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudentModel Student = new StudentModel();
            Student.setStudentID(rs.getInt("studentID"));
            Student.setUserID(rs.getString("userID"));
            return Student;
        }
    }
}