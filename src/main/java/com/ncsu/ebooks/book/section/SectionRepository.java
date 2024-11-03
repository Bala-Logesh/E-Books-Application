package com.ncsu.ebooks.book.section;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SectionRepository {

    private final JdbcTemplate jdbcTemplate;

    public SectionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SectionModel> findAll() {
        String sql = "SELECT * FROM Section";
        return jdbcTemplate.query(sql, new SectionRM());
    }

    public SectionModel findById(int id) {
        String sql = "SELECT sectionID, sectionNumber, title, chapterID, hidden FROM Section WHERE sectionID = ?";
        return jdbcTemplate.queryForObject(sql, new SectionRM(), id);
    }

    public List<SectionModel> findByChapterID(int chapterId) {
        String sql = "SELECT sectionID, sectionNumber, title, chapterID, hidden FROM Section WHERE chapterID = ?";
        return jdbcTemplate.query(sql, new SectionRM(), chapterId);
    }

    public void save(SectionModel section) {
        String sql = "INSERT INTO Section (sectionID, sectionNumber, chapterID, title) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, section.getSectionID(), section.getSectionNumber(), section.getChapterID(), section.getTitle());
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