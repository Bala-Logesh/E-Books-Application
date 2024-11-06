package com.ncsu.ebooks.list.waitlist;

import org.springframework.dao.DataAccessException;
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

    public List<WaitListRespModel> findAllByFacultyUserID(String userID) {
        String sql = "SELECT " +
                "Wait.waitListID, " +
                "ActiveCourse.courseID, " +
                "Student.userID, " +
                "User.firstName AS firstName, " +
                "User.lastName AS lastName " +
                "FROM Wait " +
                "JOIN Student ON Wait.studentID = Student.studentID " +
                "JOIN User ON Student.userID = User.userID " +
                "JOIN ActiveCourse ON Wait.activeCourseID = ActiveCourse.activeCourseID " +
                "JOIN Course ON ActiveCourse.courseID = Course.courseID " +
                "WHERE Course.facultyID = (SELECT facultyId FROM Faculty WHERE userID = ?);";
        try {
            return jdbcTemplate.query(sql, new WaitListRespRM(), userID);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving wait list: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve wait list", e);
        }
    }

    public WaitListModel findById(int id) {
        String sql = "SELECT waitListId, studentID, activeCourseID FROM Wait WHERE waitListID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new WaitListRM(), id);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving wait list: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve wait list", e);
        }
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
            WaitListModel WList = new WaitListModel();
            WList.setWaitListID(rs.getInt("waitListID"));
            WList.setActiveCourseID(rs.getInt("activeCourseID"));
            WList.setStudentID(rs.getInt("studentID"));
            return WList;
        }
    }

    private static class WaitListRespRM implements RowMapper<WaitListRespModel> {
        @Override
        public WaitListRespModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            WaitListRespModel WListResp = new WaitListRespModel();
            WListResp.setWaitListID(rs.getInt("waitListID"));
            WListResp.setCourseID(rs.getString("courseID"));
            WListResp.setUserID(rs.getString("userID"));
            WListResp.setFirstName(rs.getString("firstName"));
            WListResp.setLastName(rs.getString("lastName"));
            return WListResp;
        }
    }
}