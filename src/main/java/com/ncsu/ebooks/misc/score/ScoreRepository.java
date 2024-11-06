package com.ncsu.ebooks.misc.score;

import com.ncsu.ebooks.book.etextbook.ETextBookRepository;
import org.springframework.dao.DataAccessException;
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
        String sql = "INSERT INTO StudentScore (studentID, activeCourseID, activityID, point) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE point = IF(point > VALUES(point), point, VALUES(point))";
        try {
            jdbcTemplate.update(sql, studentID, activeCourseID, activityID, point);
        } catch (DataAccessException e) {
            System.err.println("Error saving score: " + e.getMessage());
            throw new RuntimeException("Failed to save score: " + e.getMessage(), e);
        }
    }

    public void update(int id, int studentID, int activeCourseID, int activityID, int point) {
        String sql = "UPDATE StudentScore SET studentID = ?, activeCourseID = ?, activityID = ?, point = ? WHERE scoreID = ?";
        jdbcTemplate.update(sql, studentID, activeCourseID, activityID, point, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM StudentScore WHERE scoreID = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<ScoreSummaryModel> findByStudentID(int id) {
        String sql = "SELECT ssummary.ssummaryID, ssummary.studentID, ssummary.activeCourseID, " +
                "ssummary.totalPoints, ssummary.totalActivities, ssummary.timeStamp, c.title AS courseName " +
                "FROM StudentScoreSummary ssummary " +
                "JOIN ActiveCourse ac ON ssummary.activeCourseID = ac.activeCourseID " +
                "JOIN Course c ON ac.courseID = c.courseID " +
                "WHERE ssummary.studentID = ?";
        try {
            return jdbcTemplate.query(sql, new ScoreSummaryRM(), id);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving score summary: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve  score summary", e);
        }
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

    private static class ScoreSummaryRM implements RowMapper<ScoreSummaryModel> {
        @Override
        public ScoreSummaryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ScoreSummaryModel Score = new ScoreSummaryModel();
            Score.setSsummaryID(rs.getInt("ssummaryID"));
            Score.setStudentID(rs.getInt("studentID"));
            Score.setActiveCourseID(rs.getInt("activeCourseID"));
            Score.setTotalPoints(rs.getInt("totalPoints"));
            Score.setTotalActivities(rs.getInt("totalActivities"));
            Score.setTimeStamp(rs.getTimestamp("timeStamp"));
            Score.setCourseName(rs.getString("courseName"));
            return Score;
        }
    }
}