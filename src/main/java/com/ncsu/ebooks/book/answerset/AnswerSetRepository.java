package com.ncsu.ebooks.book.answerset;

import com.ncsu.ebooks.book.chapter.ChapterRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class AnswerSetRepository {

    private final JdbcTemplate jdbcTemplate;

    public AnswerSetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AnswerSetModel> findAll() {
        String sql = "SELECT * FROM AnswerSet";
        try {
            return jdbcTemplate.query(sql, new AnswerSetRM());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving answer sets: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve answer sets", e);
        }
    }

    public AnswerSetModel findById(int id) {
        String sql = "SELECT * FROM AnswerSet WHERE answerSetID = ?";
        return jdbcTemplate.queryForObject(sql, new AnswerSetRM(), id);
    }

    public List<AnswerSetModel> findByActivityID(int activityID) {
        String sql = "SELECT ASW.answerSetID, ASW.activityID, ASW.answerOption, ASW.correct, ASW.explanation " +
                     "FROM AnswerSet ASW WHERE ASW.activityID = ?";
        return jdbcTemplate.query(sql, new AnswerSetRM(), activityID);
    }

    public AnswerSetModel save(AnswerSetModel answerSet) {
        String sql = "INSERT INTO AnswerSet (activityID, answerOption, explanation, correct) VALUES (?,?,?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"answerSetID"});
                ps.setInt(1, answerSet.getActivityID());
                ps.setString(2, answerSet.getAnswerOption());
                ps.setString(3, answerSet.getExplanation());
                ps.setBoolean(4, answerSet.isCorrect());
                return ps;
            }, keyHolder);

            answerSet.setAnswerSetID(Objects.requireNonNull(keyHolder.getKey()).intValue());

            return answerSet;
        } catch (DataAccessException e) {
            System.err.println("Error saving answer set: " + e.getMessage());
            throw new RuntimeException("Failed to save answer set: " + e.getMessage(), e);
        }
    }

    public void update(int id, AnswerSetModel answerSet) {
        String sql = "UPDATE AnswerSet SET activityID = ?, answerOption = ?, explanation = ? WHERE answerSetID = ?"; // correct?
        jdbcTemplate.update(sql, answerSet.getActivityID(), answerSet.getAnswerOption(), answerSet.getExplanation(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM AnswerSet WHERE answerSetID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class AnswerSetRM implements RowMapper<AnswerSetModel> {
        @Override
        public AnswerSetModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            AnswerSetModel AnswerSet = new AnswerSetModel();
            AnswerSet.setAnswerSetID(rs.getInt("answerSetID"));
            AnswerSet.setActivityID(rs.getInt("activityID"));
            AnswerSet.setAnswerOption(rs.getString("answerOption"));
            AnswerSet.setCorrect(rs.getBoolean("correct"));
            AnswerSet.setExplanation(rs.getString("explanation"));
            return AnswerSet;
        }
    }
}