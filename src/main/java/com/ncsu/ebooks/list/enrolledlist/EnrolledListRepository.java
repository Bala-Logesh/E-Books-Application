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
}