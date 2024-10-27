package com.ncsu.ebooks.section;

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
        String sql = "";
        return jdbcTemplate.queryForObject(sql, new SectionRM(), id);
    }

    public List<SectionModel> findByChapterId(int chapterId) {
        String sql = "";
        return jdbcTemplate.query(sql, new SectionRM(), chapterId);
    }

    public void save(SectionModel section) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void update(int id, SectionModel section) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    public void delete(int id) {
        String sql = "";
        jdbcTemplate.update(sql);
    }

    private static class SectionRM implements RowMapper<SectionModel> {
        @Override
        public SectionModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            SectionModel Section = new SectionModel();
            Section.setSectionId(rs.getInt("sectionId"));
            Section.setChapterId(rs.getInt("chapterId"));
            Section.setSectionNumber(rs.getInt("contentId"));
            Section.setTitle(rs.getString("question"));
            Section.setHidden(rs.getBoolean("hidden"));
            return Section;
        }
    }
}