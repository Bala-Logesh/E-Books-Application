package com.ncsu.ebooks.book.contentblock;

import com.ncsu.ebooks.book.chapter.ChapterModel;
import com.ncsu.ebooks.book.chapter.ChapterRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class ContentBlockRepository {

    private final JdbcTemplate jdbcTemplate;

    public ContentBlockRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ContentBlockModel> findAll() {
        String sql = "SELECT * FROM ContentBlock";
        try {
            return jdbcTemplate.query(sql, new ContentBlockRM());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving content blocks: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve content blocks", e);
        }
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

    public ContentBlockModel save(ContentBlockModel contentBlock) {
        String sql = "INSERT INTO ContentBlock (sectionID, image, textBlock, hidden) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"contentBlockID"});
                ps.setInt(1, contentBlock.getSectionID());
                ps.setString(2, contentBlock.getImage());
                ps.setString(3, contentBlock.getTextBlock());
                ps.setBoolean(4, contentBlock.isHidden());
                return ps;
            }, keyHolder);

            contentBlock.setContentBlockID(Objects.requireNonNull(keyHolder.getKey()).intValue());

            return contentBlock;
        } catch (DataAccessException e) {
            System.err.println("Error saving content block: " + e.getMessage());
            throw new RuntimeException("Failed to save content block: " + e.getMessage(), e);
        }
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