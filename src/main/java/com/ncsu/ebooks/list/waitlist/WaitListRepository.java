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
        String sql = "SELECT waitListId, studentID, activeCourseID FROM Wait WHERE waitListID = ?";
        return jdbcTemplate.queryForObject(sql, new WaitListRM(), id);
    }

    public List<WaitListModel> findByCourseId(int courseId) {
        String sql = "SELECT waitListID, studentID, activeCourseID FROM Wait WHERE activeCourseID = ?";
        return jdbcTemplate.query(sql, new WaitListRM(), courseId);
    }

    public List<WaitListModel> findByStudentId(int studentId) {
        String sql = "SELECT waitListID, studentID, activeCourseID FROM Wait WHERE studentID = ?";
        return jdbcTemplate.query(sql, new WaitListRM(), studentId);
    }

    public void save(int studentID, int activeCourseID) {
        String sql = "INSERT INTO Wait(studentID, activeCourseID) VALUES (?, ?)";
        jdbcTemplate.update(sql, studentID, activeCourseID);
    }

    public void update(int id, int studentID, int activeCourseID) {
        String sql = "UPDATE Wait SET studentID = ?, activeCourseID = ? WHERE waitListID = ?";
        jdbcTemplate.update(sql, studentID, activeCourseID, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Wait WHERE waitListID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class WaitListRM implements RowMapper<WaitListModel> {
        @Override
        public WaitListModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            WaitListModel EList = new WaitListModel();
            EList.setWaitListID(rs.getInt("waitListID"));
            EList.setActiveCourseID(rs.getInt("activeCourseID"));
            EList.setStudentID(rs.getInt("studentID"));
            return EList;
        }
    }
}