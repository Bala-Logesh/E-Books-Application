package com.ncsu.ebooks.activity;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ActivityRepository {

    private final JdbcTemplate jdbcTemplate;

    public ActivityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ActivityModel> findAll() {
        String sql = "SELECT * FROM AnswerSet";
        return jdbcTemplate.query(sql, new ActivityRM());
    }

    public ActivityModel findById(int id) {
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new ActivityRM(), id);
    }

    public List<ActivityModel> findByContentId(int activityId) {
        String sql = "";
        return jdbcTemplate.query(sql, new ActivityRM(), activityId);
    }

    public void save(ActivityModel answerSet) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void update(int id, ActivityModel answerSet) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void delete(int id) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    private static class ActivityRM implements RowMapper<ActivityModel> {
        @Override
        public ActivityModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ActivityModel Activity = new ActivityModel();
            Activity.setSectionId(rs.getInt("sectionId"));
            Activity.setContentId(rs.getInt("contentId"));
            Activity.setQuestion(rs.getString("question"));
            Activity.setHidden(rs.getBoolean("hidden"));
            return Activity;
        }
    }
}