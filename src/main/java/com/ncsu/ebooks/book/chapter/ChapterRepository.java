package com.ncsu.ebooks.book.chapter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ChapterRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChapterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ChapterModel> findAll() {
        String sql = "SELECT * FROM Chapter";
        return jdbcTemplate.query(sql, new ChapterRM());
    }

    public ChapterModel findById(int id) {
        String sql = "SELECT chapterID, chapterNumber, title, eTextBookID, hidden FROM Chapter WHERE chapterID = ?";
        return jdbcTemplate.queryForObject(sql, new ChapterRM(), id);
    }

    public List<ChapterModel> findByETextBookId(int eTextBookId) {
        String sql = "SELECT chapterID, chapterNumber, title, eTextBookID, hidden FROM Chapter WHERE eTextBookID = ?";
        return jdbcTemplate.query(sql, new ChapterRM(), eTextBookId);
    }

    public void save(ChapterModel chapter) {
        String sql = "INSERT INTO Chapter (chapterID, chapterNumber, title, eTextBookID) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, chapter.getChapterId(), chapter.getChapterNumber(), chapter.getTitle(), chapter.getETextBookID());
    }

    public void update(int id, ChapterModel chapter) {
        String sql = "UPDATE Chapter SET chapterID = ?, chapterNumber = ?, title = ?, eTextBookID = ? WHERE chapterID = ?";
        jdbcTemplate.update(sql, chapter.getChapterId(), chapter.getChapterNumber(), chapter.getTitle(), chapter.getETextBookID(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Chapter WHERE chapterID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ChapterRM implements RowMapper<ChapterModel> {
        @Override
        public ChapterModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ChapterModel Chapter = new ChapterModel();
            Chapter.setChapterId(rs.getInt("chapterId"));
            Chapter.setChapterNumber(rs.getInt("chapterNumber"));
            Chapter.setTitle(rs.getString("title"));
            Chapter.setHidden(rs.getBoolean("hidden"));
            return Chapter;
        }
    }
}