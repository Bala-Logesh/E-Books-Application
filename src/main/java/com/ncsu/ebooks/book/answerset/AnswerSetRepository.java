package com.ncsu.ebooks.book.answerset;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AnswerSetRepository {

    private final JdbcTemplate jdbcTemplate;

    public AnswerSetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AnswerSetModel> findAll() {
        String sql = "SELECT * FROM AnswerSet";
        return jdbcTemplate.query(sql, new AnswerSetRM());
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

    public void save(AnswerSetModel answerSet) {
        String sql = "INSERT INTO AnswerSet (answerSetID, activityID, answerOption, explanation) " +
                     "VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, answerSet.getAnswerSetID(), answerSet.getActivityID(), answerSet.getAnswerOption(), answerSet.getExplanation());
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