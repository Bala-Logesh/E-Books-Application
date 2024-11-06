package com.ncsu.ebooks.list.enrolledlist;

import com.ncsu.ebooks.list.waitlist.WaitListRepository;
import com.ncsu.ebooks.list.waitlist.WaitListRespModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EnrolledListRepository {

    private final JdbcTemplate jdbcTemplate;

    public EnrolledListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EnrolledListRespModel> findAll() {
        String sql = "SELECT " +
                "Enrolled.enrolledID, " +
                "ActiveCourse.courseID, " +
                "Student.userID, " +
                "User.firstName AS firstName, " +
                "User.lastName AS lastName " +
                "FROM Enrolled " +
                "JOIN Student ON Enrolled.studentID = Student.studentID " +
                "JOIN User ON Student.userID = User.userID " +
                "JOIN ActiveCourse ON Enrolled.activeCourseID = ActiveCourse.activeCourseID " +
                "JOIN Course ON ActiveCourse.courseID = Course.courseID;";
        try {
            return jdbcTemplate.query(sql, new EnrolledListRespRM());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving enrolled list: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve enrolled list", e);
        }
    }

    public List<EnrolledListRespModel> findAllByFacultyID(int facultyID) {
        String sql = "SELECT " +
                "Enrolled.enrolledID, " +
                "ActiveCourse.courseID, " +
                "Student.userID, " +
                "User.firstName AS firstName, " +
                "User.lastName AS lastName " +
                "FROM Enrolled " +
                "JOIN Student ON Enrolled.studentID = Student.studentID " +
                "JOIN User ON Student.userID = User.userID " +
                "JOIN ActiveCourse ON Enrolled.activeCourseID = ActiveCourse.activeCourseID " +
                "JOIN Course ON ActiveCourse.courseID = Course.courseID " +
                "WHERE Course.facultyID = ?;";
        try {
            return jdbcTemplate.query(sql, new EnrolledListRespRM(), facultyID);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving enrolled list: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve enrolled list", e);
        }
    }

    public EnrolledListModel findById(int id) {
        String sql = "SELECT enrolledID, studentID, activeCourseID, courseScore FROM Enrolled WHERE enrolledID = ?";
        return jdbcTemplate.queryForObject(sql, new EListRM(), id);
    }

    public List<EnrolledListModel> findByCourseId(int courseID) {
        String sql = "SELECT enrolledID, studentID, activeCourseID, courseScore FROM Enrolled WHERE activeCourseID = ?";
        return jdbcTemplate.query(sql, new EListRM(), courseID);
    }

    public List<EnrolledListModel> findByStudentId(int studentID) {
        String sql = "SELECT enrolledID, studentID, activeCourseID, courseScore FROM Enrolled WHERE studentID = ?";
        return jdbcTemplate.query(sql, new EListRM(), studentID);
    }

    public void save(int studentID, int activeCourseID) {
        String sql = "INSERT INTO Enrolled(studentID, activeCourseID) VALUES (?, ?)";
        jdbcTemplate.update(sql, studentID, activeCourseID);
    }

    public void update(int id, int studentID, int activeCourseID) {
        String sql = "UPDATE Enrolled SET studentID = ?, activeCourseID = ? WHERE enrolledID = ?";
        jdbcTemplate.update(sql, studentID, activeCourseID, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Enrolled WHERE enrolledID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class EListRM implements RowMapper<EnrolledListModel> {
        @Override
        public EnrolledListModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            EnrolledListModel EList = new EnrolledListModel();
            EList.setEnrolledID(rs.getInt("enrolledID"));
            EList.setActiveCourseID(rs.getInt("activeCourseID"));
            EList.setStudentID(rs.getInt("studentID"));
            EList.setCourseScore(rs.getInt("courseScore"));
            return EList;
        }
    }

    private static class EnrolledListRespRM implements RowMapper<EnrolledListRespModel> {
        @Override
        public EnrolledListRespModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            EnrolledListRespModel EListResp = new EnrolledListRespModel();
            EListResp.setEnrolledListID(rs.getInt("enrolledID"));
            EListResp.setCourseID(rs.getString("courseID"));
            EListResp.setUserID(rs.getString("userID"));
            EListResp.setFirstName(rs.getString("firstName"));
            EListResp.setLastName(rs.getString("lastName"));
            return EListResp;
        }
    }
}