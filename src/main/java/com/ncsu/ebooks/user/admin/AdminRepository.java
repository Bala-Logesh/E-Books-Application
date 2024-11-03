package com.ncsu.ebooks.user.admin;

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
        String sql = "SELECT * FROM Admin WHERE adminId = ?";
        return jdbcTemplate.queryForObject(sql, new AdminRM(), id);
    }

    public void save(String userID) {
        String sql = "INSERT INTO Admin (userID) VALUES (?)";
        jdbcTemplate.update(sql, userID);
    }

    public void update(int id, String userId) {
        String sql = "UPDATE Admin SET userId = ? WHERE adminId = ?";
        jdbcTemplate.update(sql, userId, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Admin WHERE adminId = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class AdminRM implements RowMapper<AdminModel> {
        @Override
        public AdminModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdminModel Admin = new AdminModel();
            Admin.setAdminID(rs.getInt("adminID"));
            Admin.setUserID(rs.getString("userID"));
            return Admin;
        }
    }
}