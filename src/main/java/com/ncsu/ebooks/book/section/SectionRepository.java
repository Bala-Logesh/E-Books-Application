package com.ncsu.ebooks.book.section;

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
public class SectionRepository {

    private final JdbcTemplate jdbcTemplate;

    public SectionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SectionModel> findAll() {
        String sql = "SELECT * FROM Section";
        try {
            return jdbcTemplate.query(sql, new SectionRM());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving sections: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve sections", e);
        }
    }

    public SectionModel findById(int id) {
        String sql = "SELECT sectionID, sectionNumber, title, chapterID, hidden FROM Section WHERE sectionID = ?";
        return jdbcTemplate.queryForObject(sql, new SectionRM(), id);
    }

    public List<SectionModel> findByChapterID(int chapterId) {
        String sql = "SELECT sectionID, sectionNumber, title, chapterID, hidden FROM Section WHERE chapterID = ?";
        return jdbcTemplate.query(sql, new SectionRM(), chapterId);
    }

    public SectionModel save(SectionModel section) {
        String sql = "INSERT INTO Section (sectionNumber, chapterID, title, hidden) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"sectionID"});
                ps.setString(1, section.getSectionNumber());
                ps.setInt(2, section.getChapterID());
                ps.setString(3, section.getTitle());
                ps.setBoolean(4, section.isHidden());
                return ps;
            }, keyHolder);

            section.setSectionID(Objects.requireNonNull(keyHolder.getKey()).intValue());

            return section;
        } catch (DataAccessException e) {
            System.err.println("Error saving section: " + e.getMessage());
            throw new RuntimeException("Failed to save section: " + e.getMessage(), e);
        }
    }

    public void update(int id, SectionModel section) {
        String sql = "UPDATE Section SET sectionID = ?, sectionNumber = ?, chapterID = ?, title = ? WHERE sectionID = ?";
        jdbcTemplate.update(sql, section.getSectionID(), section.getSectionNumber(), section.getChapterID(), section.getTitle(), section.getSectionID(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Section WHERE sectionID = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class SectionRM implements RowMapper<SectionModel> {
        @Override
        public SectionModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            SectionModel Section = new SectionModel();
            Section.setSectionID(rs.getInt("sectionID"));
            Section.setChapterID(rs.getInt("chapterID"));
            Section.setSectionNumber(rs.getString("sectionNumber"));
            Section.setTitle(rs.getString("title"));
            Section.setHidden(rs.getBoolean("hidden"));
            return Section;
        }
    }
}