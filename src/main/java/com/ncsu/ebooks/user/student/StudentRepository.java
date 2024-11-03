package com.ncsu.ebooks.user.student;

import com.ncsu.ebooks.user.admin.AdminRepository;
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
        return jdbcTemplate.query(sql, new StudentRM());
    }

    public StudentModel findById(int id) {
        String sql = "SELECT * FROM Student WHERE studentID = ?";
        return jdbcTemplate.queryForObject(sql, new StudentRM(), id);
    }

    public void save(String userId) {
        String sql = "INSERT INTO Student (userID) VALUES (?)";
        jdbcTemplate.update(sql, userId);
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