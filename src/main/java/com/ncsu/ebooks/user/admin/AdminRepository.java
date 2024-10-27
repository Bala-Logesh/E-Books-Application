package com.ncsu.ebooks.user.admin;

import com.ncsu.ebooks.user.user.UserModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AdminRepository {

    private final JdbcTemplate jdbcTemplate;

    public AdminRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AdminModel> findAll() {
        String sql = "SELECT * FROM Admin";
        return jdbcTemplate.query(sql, new AdminRM());
    }

    public AdminModel findById(int id) {
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new AdminRM(), id);
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

    private static class AdminRM implements RowMapper<AdminModel> {
        @Override
        public AdminModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdminModel Admin = new AdminModel();
            Admin.setAdminId(rs.getInt("adminId"));
            Admin.setUserId(rs.getInt("userId"));
            return Admin;
        }
    }
}