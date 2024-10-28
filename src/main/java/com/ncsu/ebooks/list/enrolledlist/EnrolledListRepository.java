package com.ncsu.ebooks.list.enrolledlist;

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

    public List<EnrolledListModel> findAll() {
        String sql = "SELECT * FROM Enrolled";
        return jdbcTemplate.query(sql, new EListRM());
    }

    public EnrolledListModel findById(int id) {
        String sql = "SELECT enrolledID, studentID, activeCourseID, courseScore FROM Enrolled WHERE enrolledID = ?";
        return jdbcTemplate.queryForObject(sql, new EListRM(), id);
    }

    public List<EnrolledListModel> findByCourseId(int courseId) {
        String sql = "SELECT enrolledID, studentID, activeCourseID, courseScore FROM Enrolled WHERE activeCourseID = ?";
        return jdbcTemplate.query(sql, new EListRM(), courseId);
    }

    public List<EnrolledListModel> findByStudentId(int studentId) {
        String sql = "SELECT enrolledID, studentID, activeCourseID, courseScore FROM Enrolled WHERE studentID = ?";
        return jdbcTemplate.query(sql, new EListRM(), studentId);
    }

    public void save(EnrolledListModel eList) {
        String sql = "INSERT INTO Enrolled (enrolledID, studentID, activeCourseID, courseScore) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, eList.getEnrollmentId(), eList.getStudentId(), eList.getCourseId(), eList.getCourseScore());
    }

    public void update(int id, EnrolledListModel eList) {
        String sql = "UPDATE Enrolled SET enrolledID = ?, studentID = ?, activeCourseID = ?, courseScore = ? WHERE enrolledID = ?";
        jdbcTemplate.update(sql, eList.getEnrollmentId(), eList.getStudentId(), eList.getCourseId(), eList.getCourseScore(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Enrolled WHERE enrolledID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class EListRM implements RowMapper<EnrolledListModel> {
        @Override
        public EnrolledListModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            EnrolledListModel EList = new EnrolledListModel();
            EList.setEnrollmentId(rs.getInt("enrollmentId"));
            EList.setCourseId(rs.getInt("courseId"));
            EList.setStudentId(rs.getInt("studentId"));
            EList.setCourseScore(rs.getInt("courseScore"));
            return EList;
        }
    }
}