package com.ncsu.ebooks.user.user;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserModel> findAll() {
        String sql = "SELECT * FROM User";
        return jdbcTemplate.query(sql, new UserRM());
    }

    public UserModel findById(String id) {
        String sql = "SELECT * FROM User WHERE userID = ?";
        return jdbcTemplate.queryForObject(sql, new UserRM(), id);
    }

    public UserModel findByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new UserRM(), email);
    }

    public void save(UserModel user) {
        String sql = "INSERT INTO User (userID, firstName, lastName, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, user.getUserID(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole().toString());
        } catch (DataAccessException e) {
            System.err.println("Error saving user: " + e.getMessage());
            throw new RuntimeException("Failed to save user: " + e.getMessage(), e);
        }
    }

    public void update(String id, UserModel user) {
        String sql = "UPDATE User SET firstName = ?, lastName = ?, email = ?, password = ?, role = ? WHERE userID = ?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole().toString(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM User WHERE userID = ?";
        jdbcTemplate.update(sql,id);
    }

    public UserModel loginUser(UserLoginModel user) {
        String sql = "SELECT * FROM User WHERE userID = ? and password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRM(), user.getUserID(), user.getPassword());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void changePassword(UserChangePwdModel user) {
        String sqlCheckOldPassword = "SELECT password FROM User WHERE userID = ?";
        String sqlUpdatePassword = "UPDATE User SET password = ? WHERE userID = ?";

        try {
            String currentPassword = jdbcTemplate.queryForObject(sqlCheckOldPassword, new Object[]{user.getUserID()}, String.class);
            if (currentPassword != null && user.getOldPassword().matches(currentPassword)) {
                jdbcTemplate.update(sqlUpdatePassword, user.getNewPassword(), user.getUserID());
            } else {
                throw new IllegalArgumentException("Old password is incorrect.");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("User not found.");
        }
    }

    private static class UserRM implements RowMapper<UserModel> {
        @Override
        public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserModel User = new UserModel();
            User.setUserID(rs.getString("userID"));
            User.setFirstName(rs.getString("firstName"));
            User.setLastName(rs.getString("lastName"));
            User.setEmail(rs.getString("email"));
            User.setPassword(rs.getString("password"));
            User.setRole(Role.valueOf(rs.getString("role")));
            User.setAccountCreation(rs.getTimestamp("accountCreation"));
            return User;
        }
    }
}