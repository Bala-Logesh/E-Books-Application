package com.ncsu.ebooks.book.activity;

import com.ncsu.ebooks.book.chapter.ChapterModel;
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
public class ActivityRepository {

    private final JdbcTemplate jdbcTemplate;

    public ActivityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ActivityModel> findAll() {
        String sql = "SELECT * FROM Activity";
        try {
            return jdbcTemplate.query(sql, new ActivityRM());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving activities: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve activities", e);
        }
    }

    public ActivityModel findById(int id) {
        String sql = "SELECT activityID, sectionID, contentBlockID, question, hidden FROM Activity WHERE activityID = ?";
        return jdbcTemplate.queryForObject(sql, new ActivityRM(), id);
    }

    public List<ActivityModel> findByContentID(int contentBlockID) {
        String sql = "SELECT activityID, sectionID, contentBlockID, question, hidden FROM Activity WHERE contentBlockID = ?";
        return jdbcTemplate.query(sql, new ActivityRM(), contentBlockID);
    }

    public ActivityModel save(ActivityModel activity) {
        String sql = "INSERT INTO Activity (sectionID, contentBlockID, question, hidden) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"activityID"});
                ps.setInt(1, activity.getSectionID());
                ps.setInt(2, activity.getContentBlockID());
                ps.setString(3, activity.getQuestion());
                ps.setBoolean(4, activity.isHidden());
                return ps;
            }, keyHolder);

            activity.setActivityID(Objects.requireNonNull(keyHolder.getKey()).intValue());

            return activity;
        } catch (DataAccessException e) {
            System.err.println("Error saving activity: " + e.getMessage());
            throw new RuntimeException("Failed to save activity: " + e.getMessage(), e);
        }
    }

    public void update(int id, ActivityModel activity) {
        String sql = "UPDATE Activity SET activityID = ?, sectionID = ?, contentBlockID = ?, question = ? WHERE activityID = ?";
        jdbcTemplate.update(sql, activity.getActivityID(), activity.getSectionID(), activity.getContentBlockID(), activity.getQuestion(), id);
    }

    public void hideActivity(int id) {
        String sql = "UPDATE Activity SET hidden = NOT hidden WHERE activityID = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error hiding/unhiding activity: " + e.getMessage());
            throw new RuntimeException("Failed to hide/unhide activity", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Activity WHERE activityID = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting activity: " + e.getMessage());
            throw new RuntimeException("Failed to delete activity", e);
        }
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