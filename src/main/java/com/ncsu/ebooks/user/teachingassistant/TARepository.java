package com.ncsu.ebooks.user.teachingassistant;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TARepository {

    private final JdbcTemplate jdbcTemplate;

    public TARepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TAModel> findAll() {
        String sql = "SELECT * FROM TeachingAssistant";
        return jdbcTemplate.query(sql, new TARM());
    }

    public TAModel findById(int id) {
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new TARM(), id);
    }

    public void save(int userId) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void update(int id, int userId) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void delete(int id) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    private static class TARM implements RowMapper<TAModel> {
        @Override
        public TAModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            TAModel TA = new TAModel();
            TA.setTeachingAsstId(rs.getInt("teachingAsstId"));
            TA.setUserId(rs.getInt("userId"));
            TA.setACourseId(rs.getInt("aCourseId"));
            TA.setResetPassword(rs.getBoolean("resetPassword"));
            return TA;
        }
    }
}