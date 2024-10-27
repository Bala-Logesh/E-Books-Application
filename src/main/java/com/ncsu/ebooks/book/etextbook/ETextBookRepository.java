package com.ncsu.ebooks.book.etextbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ETextBookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ETextBookModel> findAll() {
        String sql = "SELECT * FROM ETextBook";
        return jdbcTemplate.query(sql, new ETextBookRM());
    }

    public ETextBookModel findById(int id) {
        String sql = "SELECT * FROM ETextBook WHERE `eTextBookID` = (?)";
        return jdbcTemplate.queryForObject(sql, new ETextBookRM(), id);
    }

    public void save(ETextBookModel eTextBook) {
        String sql = "INSERT INTO ETextBook (title) VALUES (?)";
        jdbcTemplate.update(sql, eTextBook.getTitle());
    }

    public void update(int id, ETextBookModel ETextBook) {
        String sql = "UPDATE ETextBook SET title = ? WHERE eTextBookID = ?";
        jdbcTemplate.update(sql, ETextBook.getTitle(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM ETextBook WHERE eTextBookID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ETextBookRM implements RowMapper<ETextBookModel> {
        @Override
        public ETextBookModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ETextBookModel ETextBook = new ETextBookModel();
            ETextBook.setETextBookID(rs.getInt("eTextBookID"));
            ETextBook.setTitle(rs.getString("title"));
            return ETextBook;
        }
    }
}