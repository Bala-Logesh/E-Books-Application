package com.ncsu.ebooks.list.waitlist;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WaitListRepository {

    private final JdbcTemplate jdbcTemplate;

    public WaitListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WaitListModel> findAll() {
        String sql = "SELECT * FROM Wait";
        return jdbcTemplate.query(sql, new WaitListRM());
    }

    public WaitListModel findById(int id) {
        String sql = "SELECT waitListId, studentID, activeCourseID FROM Wait WHERE waitListId = ?";
        return jdbcTemplate.queryForObject(sql, new WaitListRM(), id);
    }

    public List<WaitListModel> findByCourseId(int courseId) {
        String sql = "SELECT waitListId, studentID, activeCourseID FROM Wait WHERE activeCourseID = ?";
        return jdbcTemplate.query(sql, new WaitListRM(), courseId);
    }

    public List<WaitListModel> findByStudentId(int studentId) {
        String sql = "SELECT waitListID, studentID, activeCourseID FROM Wait WHERE studentID = ?";
        return jdbcTemplate.query(sql, new WaitListRM(), studentId);
    }

    public void save(WaitListModel waitList) {
        String sql = "INSERT INTO Wait(waitListId, studentID, activeCourseID) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, waitList.getWaitListId(), waitList.getStudentId(), waitList.getCourseId());
    }

    public void update(int id, WaitListModel waitList) {
        String sql = "UPDATE Wait SET waitListId = ?, studentID = ?, activeCourseID = ? WHERE waitListId = ?";
        jdbcTemplate.update(sql, waitList.getWaitListId(), waitList.getStudentId(), waitList.getCourseId(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Wait WHERE waitListId = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class WaitListRM implements RowMapper<WaitListModel> {
        @Override
        public WaitListModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            WaitListModel EList = new WaitListModel();
            EList.setWaitListId(rs.getInt("waitListId"));
            EList.setCourseId(rs.getInt("courseId"));
            EList.setStudentId(rs.getInt("studentId"));
            return EList;
        }
    }
}