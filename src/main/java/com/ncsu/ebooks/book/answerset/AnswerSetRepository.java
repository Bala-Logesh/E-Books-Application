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

    public List<AnswerSetModel> findByActivityId(int activityId) {
        String sql = "SELECT ASW.answerSetID, ASW.activityID, ASW.answerOption, ASW.correct, ASW.explanation " +
                     "FROM Activity A JOIN AnswerSet ASW ON A.activityID = ASW.activityID " +
                     "WHERE A.activityID = ?"; // not sure about title field here
        return jdbcTemplate.query(sql, new AnswerSetRM(), activityId);
    }

    public void save(AnswerSetModel answerSet) {
        String sql = "INSERT INTO AnswerSet (answerSetID, activityId, answerOption, explanation) " +
                     "VALUES (?,?,?,?)"; // no need for correct?
        jdbcTemplate.update(sql, answerSet.getAnswerSetID(), answerSet.getActivityId(), answerSet.getAnswerOption(), answerSet.getExplanation());
    }

    public void update(int id, AnswerSetModel answerSet) {
        String sql = "UPDATE AnswerSet SET activityID = ?, answerOption = ?, explanation = ? WHERE answerSetID = ?"; // correct?
        jdbcTemplate.update(sql, answerSet.getActivityId(), answerSet.getAnswerOption(), answerSet.getExplanation(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM AnswerSet WHERE answerSetID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class AnswerSetRM implements RowMapper<AnswerSetModel> {
        @Override
        public AnswerSetModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            AnswerSetModel AnswerSet = new AnswerSetModel();
            AnswerSet.setAnswerSetID(rs.getInt("answerSetId"));
            AnswerSet.setActivityId(rs.getInt("activityId"));
            // AnswerSet.setTitle(rs.getString("title"));
            AnswerSet.setAnswerOption(rs.getString("answerOption"));
            AnswerSet.setCorrect(rs.getBoolean("correct"));
            AnswerSet.setExplanation(rs.getString("explanation"));
            return AnswerSet;
        }
    }
}