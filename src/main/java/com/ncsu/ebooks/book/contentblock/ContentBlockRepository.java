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
        String sql = "SELECT * FROM ContentBlock WHERE contentBlockID = ?";
        return jdbcTemplate.queryForObject(sql, new ContentBlockRM(), id);
    }

    public List<ContentBlockModel> findBySectionID(int sectionId) {
        String sql = "SELECT CB.contentBlockID, CB.sectionID, CB.image, CB.textBlock, CB.hidden " +
                "FROM ContentBlock CB WHERE sectionID = ?";
        return jdbcTemplate.query(sql, new ContentBlockRM(), sectionId);
    }

    public void save(ContentBlockModel contentBlock) {
        String sql = "INSERT INTO ContentBlock (contentBlockID, sectionID, image, textBlock) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, contentBlock.getContentBlockID(), contentBlock.getSectionID(), contentBlock.getImage(), contentBlock.getTextBlock());
    }

    public void update(int id, ContentBlockModel contentBlock) {
        String sql = "UPDATE ContentBlock SET contentBlockID = ?, sectionID = ?, image = ?, textBlock = ? WHERE ContentBlock.contentBlockID = ?";
        jdbcTemplate.update(sql, contentBlock.getContentBlockID(), contentBlock.getSectionID(), contentBlock.getImage(), contentBlock.getImage(), contentBlock.getTextBlock(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM ContentBlock WHERE contentBlockID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ContentBlockRM implements RowMapper<ContentBlockModel> {
        @Override
        public ContentBlockModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ContentBlockModel ContentBlock = new ContentBlockModel();
            ContentBlock.setContentBlockID(rs.getInt("contentBlockID"));
            ContentBlock.setSectionID(rs.getInt("sectionID"));
            ContentBlock.setImage(rs.getString("image"));
            ContentBlock.setTextBlock(rs.getString("textBlock"));
            ContentBlock.setHidden(rs.getBoolean("hidden"));
            return ContentBlock;
        }
    }
}