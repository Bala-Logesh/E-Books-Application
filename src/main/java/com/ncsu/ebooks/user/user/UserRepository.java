package com.ncsu.ebooks.user.user;

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

    public UserModel findById(int id) {
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new UserRM(), id);
    }

    public UserModel findByEmail(String email) {
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new UserRM(), email);
    }

    public void save(UserModel user) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void update(int id, UserModel user) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void delete(int id) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    private static class UserRM implements RowMapper<UserModel> {
        @Override
        public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserModel User = new UserModel();
            User.setUserId(rs.getInt("userId"));
            User.setFirstName(rs.getString("firstName"));
            User.setLastName(rs.getString("lastName"));
            User.setEmail(rs.getString("email"));
            User.setPassword(rs.getString("password"));
            User.setRole(Role.valueOf(rs.getString("role")));
            return User;
        }
    }
}