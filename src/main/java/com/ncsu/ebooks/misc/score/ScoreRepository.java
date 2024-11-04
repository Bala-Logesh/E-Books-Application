package com.ncsu.ebooks.misc.score;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ScoreRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ScoreModel> findAll() {
        String sql = "SELECT * FROM StudentScore";
        return jdbcTemplate.query(sql, new ScoreRM());
    }

    public ScoreModel findById(int id) {
        String sql = "SELECT scoreID, studentID, activeCourseID, activityID, point FROM StudentScore WHERE scoreID = ?";
        return jdbcTemplate.queryForObject(sql, new ScoreRM(), id);
    }

    public void save(int studentID, int activeCourseID, int activityID, int point) {
        String sql = "INSERT INTO StudentScore (studentID, activeCourseID, activityID, point) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, studentID, activeCourseID, activityID, point);
    }

    public void update(int id, int studentID, int activeCourseID, int activityID, int point) {
        String sql = "UPDATE StudentScore SET studentID = ?, activeCourseID = ?, activityID = ?, point = ? WHERE scoreID = ?";
        jdbcTemplate.update(sql, studentID, activeCourseID, activityID, point, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM StudentScore WHERE scoreID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ScoreRM implements RowMapper<ScoreModel> {
        @Override
        public ScoreModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ScoreModel Score = new ScoreModel();
            Score.setScoreID(rs.getInt("scoreID"));
            Score.setStudentID(rs.getInt("studentID"));
            Score.setActivityID(rs.getInt("activityID"));
            Score.setActiveCourseID(rs.getInt("activeCourseID"));
            Score.setPoint(rs.getInt("point"));
            Score.setTimeStamp(rs.getTimestamp("timeStamp"));
            return Score;
        }
    }
}