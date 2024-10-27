package com.ncsu.ebooks.answerset;

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
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new AnswerSetRM(), id);
    }

    public List<AnswerSetModel> findByActivityId(int activityId) {
        String sql = "";
        return jdbcTemplate.query(sql, new AnswerSetRM(), activityId);
    }

    public void save(AnswerSetModel answerSet) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void update(int id, AnswerSetModel answerSet) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void delete(int id) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    private static class AnswerSetRM implements RowMapper<AnswerSetModel> {
        @Override
        public AnswerSetModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            AnswerSetModel AnswerSet = new AnswerSetModel();
            AnswerSet.setAnswerSetID(rs.getInt("answerSetId"));
            AnswerSet.setActivityId(rs.getInt("activityId"));
            AnswerSet.setTitle(rs.getString("title"));
            AnswerSet.setAnswerOption(rs.getString("answerOption"));
            AnswerSet.setCorrect(rs.getBoolean("correct"));
            AnswerSet.setExplanation(rs.getString("explanation"));
            return AnswerSet;
        }
    }
}