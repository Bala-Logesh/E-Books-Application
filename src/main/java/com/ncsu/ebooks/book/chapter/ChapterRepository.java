package com.ncsu.ebooks.book.chapter;

import com.ncsu.ebooks.book.etextbook.ETextBookModel;
import com.ncsu.ebooks.book.etextbook.ETextBookRepository;
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
public class ChapterRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChapterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ChapterModel> findAll() {
        String sql = "SELECT * FROM Chapter";
        try {
            return jdbcTemplate.query(sql, new ChapterRM());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving chapters: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve chapters", e);
        }
    }

    public ChapterModel findById(int id) {
        System.out.println(id);
        String sql = "SELECT chapterID, chapterNumber, title, eTextBookID, hidden FROM Chapter WHERE chapterID = ?";
        return jdbcTemplate.queryForObject(sql, new ChapterRM(), id);
    }

    public List<ChapterModel> findByETextBookID(int eTextBookID) {
        String sql = "SELECT chapterID, chapterNumber, title, eTextBookID, hidden FROM Chapter WHERE eTextBookID = ?";
        return jdbcTemplate.query(sql, new ChapterRM(), eTextBookID);
    }

    public ChapterModel save(ChapterModel chapter) {
        String sql = "INSERT INTO Chapter (chapterNumber, title, eTextBookID, hidden) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"chapterID"});
                ps.setString(1, chapter.getChapterNumber());
                ps.setString(2, chapter.getTitle());
                ps.setInt(3, chapter.getETextBookID());
                ps.setBoolean(4, chapter.isHidden());
                return ps;
            }, keyHolder);

            chapter.setChapterID(Objects.requireNonNull(keyHolder.getKey()).intValue());

            return chapter;
        } catch (DataAccessException e) {
            System.err.println("Error saving chapter: " + e.getMessage());
            throw new RuntimeException("Failed to save chapter: " + e.getMessage(), e);
        }
    }

    public void update(int id, ChapterModel chapter) {
        String sql = "UPDATE Chapter SET chapterID = ?, chapterNumber = ?, title = ?, eTextBookID = ? WHERE chapterID = ?";
        jdbcTemplate.update(sql, chapter.getChapterID(), chapter.getChapterNumber(), chapter.getTitle(), 101, id);
    }

    public void hideChapter(int id) {
        String sql = "UPDATE Chapter SET hidden = NOT hidden WHERE chapterID = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error hiding/unhiding chapter: " + e.getMessage());
            throw new RuntimeException("Failed to hide/unhide chapter", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Chapter WHERE chapterID = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting chapter: " + e.getMessage());
            throw new RuntimeException("Failed to delete chapter", e);
        }
    }

    private static class ChapterRM implements RowMapper<ChapterModel> {
        @Override
        public ChapterModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ChapterModel Chapter = new ChapterModel();
            Chapter.setChapterID(rs.getInt("chapterId"));
            Chapter.setChapterNumber(rs.getString("chapterNumber"));
            Chapter.seteTextBookID(rs.getInt("eTextBookID"));
            Chapter.setTitle(rs.getString("title"));
            Chapter.setHidden(rs.getBoolean("hidden"));
            return Chapter;
        }
    }
}