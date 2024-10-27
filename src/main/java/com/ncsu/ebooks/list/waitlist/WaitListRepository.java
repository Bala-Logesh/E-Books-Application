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
        String sql = "";
        return jdbcTemplate.query(sql, new EListRM());
    }

    public WaitListModel findById(int id) {
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new EListRM(), id);
    }

    public List<WaitListModel> findByCourseId(int courseId) {
        String sql = "";
        return jdbcTemplate.query(sql, new EListRM(), courseId);
    }

    public List<WaitListModel> findByStudentId(int studentId) {
        String sql = "";
        return jdbcTemplate.query(sql, new EListRM(), studentId);
    }

    public void save(WaitListModel eList) {
        String sql = "";
        jdbcTemplate.update(sql, eList);
    }

    public void update(int id, WaitListModel eList) {
        String sql = "";
        jdbcTemplate.update(sql, eList);
    }

    public void delete(int id) {
        String sql = "";
        jdbcTemplate.update(sql, id);
    }

    private static class EListRM implements RowMapper<WaitListModel> {
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