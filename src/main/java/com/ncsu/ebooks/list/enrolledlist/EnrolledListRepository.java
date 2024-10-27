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
        String sql = "";
        return jdbcTemplate.query(sql, new EListRM());
    }

    public EnrolledListModel findById(int id) {
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new EListRM(), id);
    }

    public List<EnrolledListModel> findByCourseId(int courseId) {
        String sql = "";
        return jdbcTemplate.query(sql, new EListRM(), courseId);
    }

    public List<EnrolledListModel> findByStudentId(int studentId) {
        String sql = "";
        return jdbcTemplate.query(sql, new EListRM(), studentId);
    }

    public void save(EnrolledListModel eList) {
        String sql = "";
        jdbcTemplate.update(sql, eList);
    }

    public void update(int id, EnrolledListModel eList) {
        String sql = "";
        jdbcTemplate.update(sql, eList);
    }

    public void delete(int id) {
        String sql = "";
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