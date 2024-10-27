package com.ncsu.ebooks.book.contentblock;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContentBlockRepository {

    private final JdbcTemplate jdbcTemplate;

    public ContentBlockRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ContentBlockModel> findAll() {
        String sql = "SELECT * FROM ContentBlock";
        return jdbcTemplate.query(sql, new ContentBlockRM());
    }

    public ContentBlockModel findById(int id) {
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new ContentBlockRM(), id);
    }

    public List<ContentBlockModel> findBySectionId(int sectionId) {
        String sql = "";
        return jdbcTemplate.query(sql, new ContentBlockRM(), sectionId);
    }

    public void save(ContentBlockModel answerSet) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void update(int id, ContentBlockModel answerSet) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void delete(int id) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    private static class ContentBlockRM implements RowMapper<ContentBlockModel> {
        @Override
        public ContentBlockModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ContentBlockModel ContentBlock = new ContentBlockModel();
            ContentBlock.setContentBlockId(rs.getInt("contentBlockId"));
            ContentBlock.setSectionId(rs.getInt("sectionId"));
            ContentBlock.setImage(rs.getString("image"));
            ContentBlock.setTextBlock(rs.getString("textBlock"));
            ContentBlock.setHidden(rs.getBoolean("hidden"));
            return ContentBlock;
        }
    }
}