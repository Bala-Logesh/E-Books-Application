package com.ncsu.ebooks.user.student;

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
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new StudentRM(), id);
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

    private static class StudentRM implements RowMapper<StudentModel> {
        @Override
        public StudentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudentModel Student = new StudentModel();
            Student.setStudentId(rs.getInt("studentId"));
            Student.setUserId(rs.getInt("userId"));
            return Student;
        }
    }
}