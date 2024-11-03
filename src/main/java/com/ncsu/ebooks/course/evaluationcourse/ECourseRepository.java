package com.ncsu.ebooks.course.evaluationcourse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ECourseRepository {

    private final JdbcTemplate jdbcTemplate;

    public ECourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ECourseModel> findAll() {
        String sql = "SELECT * FROM EvaluationCourse";
        return jdbcTemplate.query(sql, new ECourseRM());
    }

    public ECourseModel findById(int id) {
        String sql = "SELECT evaluationID, courseID FROM EvaluationCourse WHERE evaluationID = ?";
        return jdbcTemplate.queryForObject(sql, new ECourseRM(), id);
    }

    public void save(String courseId) {
        String sql = "INSERT INTO EvaluationCourse (courseID) VALUES (?)";
        jdbcTemplate.update(sql, courseId);
    }

    public void update(int id, int courseId) {
        String sql = "UPDATE EvaluationCourse SET courseID = ? WHERE evaluationID = ?";
        jdbcTemplate.update(sql, courseId, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM EvaluationCourse WHERE evaluationID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ECourseRM implements RowMapper<ECourseModel> {
        @Override
        public ECourseModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ECourseModel ECourse = new ECourseModel();
            ECourse.setEvaluationID(rs.getInt("evaluationID"));
            ECourse.setCourseID(rs.getString("courseID"));
            return ECourse;
        }
    }
}