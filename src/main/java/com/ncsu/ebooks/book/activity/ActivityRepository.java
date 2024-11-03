package com.ncsu.ebooks.book.activity;

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
        String sql = "SELECT * FROM Activity";
        return jdbcTemplate.query(sql, new ActivityRM());
    }

    public ActivityModel findById(int id) {
        String sql = "SELECT activityID, sectionID, contentBlockID, question, hidden FROM Activity WHERE activityID = ?";
        return jdbcTemplate.queryForObject(sql, new ActivityRM(), id);
    }

    public List<ActivityModel> findByContentID(int contentBlockID) {
        String sql = "SELECT activityID, sectionID, contentBlockID, question, hidden FROM Activity WHERE contentBlockID = ?";
        return jdbcTemplate.query(sql, new ActivityRM(), contentBlockID);
    }

    public void save(ActivityModel activity) {
        System.out.println(activity);
        String sql = "INSERT INTO Activity (activityID, sectionID, contentBlockID, question) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, activity.getActivityID(), activity.getSectionID(), activity.getContentBlockID(), activity.getQuestion());
    }

    public void update(int id, ActivityModel activity) {
        String sql = "UPDATE Activity SET activityID = ?, sectionID = ?, contentBlockID = ?, question = ? WHERE activityID = ?";
        jdbcTemplate.update(sql, activity.getActivityID(), activity.getSectionID(), activity.getContentBlockID(), activity.getQuestion(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Activity WHERE activityID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ActivityRM implements RowMapper<ActivityModel> {
        @Override
        public ActivityModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ActivityModel Activity = new ActivityModel();
            Activity.setActivityID(rs.getInt("activityID"));
            Activity.setSectionID(rs.getInt("sectionID"));
            Activity.setcontentBlockID(rs.getInt("contentBlockID"));
            Activity.setQuestion(rs.getString("question"));
            Activity.setHidden(rs.getBoolean("hidden"));
            return Activity;
        }
    }
}