package com.ncsu.ebooks.misc.notification;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NotificationRepository {

    private final JdbcTemplate jdbcTemplate;

    public NotificationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<NotificationModel> findAll() {
        String sql = "SELECT * FROM Notification";
        return jdbcTemplate.query(sql, new NotificationRM());
    }

    public NotificationModel findById(int id) {
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new NotificationRM(), id);
    }

    public List<NotificationModel> findByUserId(int userId) {
        String sql = "";
        return jdbcTemplate.query(sql, new NotificationRM(), userId);
    }

    public void save(NotificationModel notification) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void update(int id, NotificationModel notification) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void delete(int id) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    private static class NotificationRM implements RowMapper<NotificationModel> {
        @Override
        public NotificationModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            NotificationModel Notification = new NotificationModel();
            Notification.setNotificationId(rs.getInt("notificationId"));
            Notification.setUserId(rs.getInt("userId"));
            Notification.setMessage(rs.getString("message"));
            Notification.setReadStatus(rs.getBoolean("readStatus"));
            return Notification;
        }
    }
}