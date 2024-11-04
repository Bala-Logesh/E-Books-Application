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
        String sql = "SELECT notificationID, userID, message, messageRead FROM Notification WHERE notificationID = ?";
        return jdbcTemplate.queryForObject(sql, new NotificationRM(), id);
    }

    public List<NotificationModel> findByUserId(String userId) {
        String sql = "SELECT notificationID, useriD, message, messageRead FROM Notification WHERE userID = ?";
        return jdbcTemplate.query(sql, new NotificationRM(), userId);
    }

    public void save(String userId, String message) {
        String sql = "INSERT INTO Notification (userID, message) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, message);
    }

    public void update(int id, String userId, String message) {
        String sql = "UPDATE Notification SET userID = ?, message = ? WHERE notificationID = ?";
        jdbcTemplate.update(sql, userId, message, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Notification WHERE notificationID = ?";
        jdbcTemplate.update(sql, id);
    }

    public void markNotificationAsRead(int id) {
        String sql = "UPDATE Notification SET messageRead = true WHERE notificationID = ?";
        jdbcTemplate.update(sql, id);
    }

    public void markNotificationAsUnread(int id) {
        String sql = "UPDATE Notification SET messageRead = false WHERE notificationID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class NotificationRM implements RowMapper<NotificationModel> {
        @Override
        public NotificationModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            NotificationModel Notification = new NotificationModel();
            Notification.setNotificationID(rs.getInt("notificationID"));
            Notification.setUserID(rs.getString("userID"));
            Notification.setMessage(rs.getString("message"));
            Notification.setMessageRead(rs.getBoolean("messageRead"));
            return Notification;
        }
    }
}