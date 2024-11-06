package com.ncsu.ebooks.book.etextbook;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ETextBookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ETextBookModel> findAll() {
        String sql = "SELECT * FROM ETextBook";
        try {
            return jdbcTemplate.query(sql, new ETextBookRM());
        } catch (DataAccessException e) {
            System.err.println("Error retrieving e-textbooks: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve e-textbooks", e);
        }
    }

    public ETextBookModel findById(int id) {
        String sql = "SELECT * FROM ETextBook WHERE `eTextBookID` = (?)";
        return jdbcTemplate.queryForObject(sql, new ETextBookRM(), id);
    }

    public ETextBookModel save(ETextBookModel eTextBook) {
        String sql = "INSERT INTO ETextBook (title) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"eTextBookID"});
                ps.setString(1, eTextBook.getTitle());
                return ps;
            }, keyHolder);

            eTextBook.setETextBookID(Objects.requireNonNull(keyHolder.getKey()).intValue());

            return eTextBook;
        } catch (DataAccessException e) {
            System.err.println("Error saving e-textbook: " + e.getMessage());
            throw new RuntimeException("Failed to save e-textbook: " + e.getMessage(), e);
        }
    }

    public void update(int id, ETextBookModel ETextBook) {
        String sql = "UPDATE ETextBook SET title = ? WHERE eTextBookID = ?";
        jdbcTemplate.update(sql, ETextBook.getTitle(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM ETextBook WHERE eTextBookID = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<ETextBookRespModel> findAllByStudentUserID(String userID) {
        String sql = "SELECT Student.studentID, Enrolled.activeCourseID, ActiveCourse.courseID, " +
                "Course.eTextBookID, ETextBook.title, Course.title as courseName " +
                "FROM User " +
                "JOIN Student ON User.userID = Student.userID " +
                "JOIN Enrolled ON Student.studentID = Enrolled.studentID " +
                "JOIN ActiveCourse ON Enrolled.activeCourseID = ActiveCourse.activeCourseID " +
                "JOIN Course ON ActiveCourse.courseID = Course.courseID " +
                "JOIN ETextBook ON Course.eTextBookID = ETextBook.eTextBookID " +
                "WHERE User.userID = ?";
        try {
            return jdbcTemplate.query(sql, new ETextBookRespModelMapper(), userID);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving e-textbooks: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve e-textbooks", e);
        }
    }

    public List<ETextBookRespModel2> findAllByUserID(String userID, String userType) {

        String sql = "";
        if (Objects.equals(userType, "FACULTY")) {
           sql = "SELECT " +
                    "Faculty.facultyId AS userTypeID, " +
                    "Course.courseID, " +
                    "Course.title AS courseName, " +
                    "CASE " +
                    "    WHEN ActiveCourse.activeCourseID IS NOT NULL THEN 'Active' " +
                    "    WHEN EvaluationCourse.evaluationID IS NOT NULL THEN 'Evaluation' " +
                    "    ELSE NULL " +
                    "END AS courseType, " +
                    "COALESCE(ActiveCourse.activeCourseID, EvaluationCourse.evaluationID) AS courseTypeID, " +
                    "ETextBook.eTextBookID AS etextBookID, " +
                    "ETextBook.title AS title " +
                    "FROM Faculty " +
                    "JOIN Course ON Faculty.facultyId = Course.facultyID " +
                    "LEFT JOIN ActiveCourse ON Course.courseID = ActiveCourse.courseID " +
                    "LEFT JOIN EvaluationCourse ON Course.courseID = EvaluationCourse.courseID " +
                    "JOIN ETextBook ON Course.eTextBookID = ETextBook.eTextBookID " +
                    "WHERE Faculty.userID = ?;";
        } else if (Objects.equals(userType, "TEACHING_ASSISTANT")) {
            sql = "SELECT " +
                    "TeachingAssistant.teachingAsstID AS userTypeID, " +
                    "Course.courseID, " +
                    "Course.title AS courseName, " +
                    "'Active' AS courseType, " +
                    "ActiveCourse.activeCourseID AS courseTypeID, " +
                    "ETextBook.eTextBookID AS etextBookID, " +
                    "ETextBook.title AS title " +
                    "FROM TeachingAssistant " +
                    "JOIN ActiveCourse ON TeachingAssistant.activeCourseID = ActiveCourse.activeCourseID " +
                    "JOIN Course ON ActiveCourse.courseID = Course.courseID " +
                    "JOIN ETextBook ON Course.eTextBookID = ETextBook.eTextBookID " +
                    "WHERE TeachingAssistant.userID = ?;";
        }
        try {
            return jdbcTemplate.query(sql, new ETextBookRespModel2Mapper(), userID);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving e-textbooks: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve e-textbooks", e);
        }
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

    public static class ETextBookRespModelMapper implements RowMapper<ETextBookRespModel> {
        @Override
        public ETextBookRespModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ETextBookRespModel eTextBook = new ETextBookRespModel();
            eTextBook.setStudentId(rs.getInt("studentID"));
            eTextBook.setActiveCourseId(rs.getInt("activeCourseID"));
            eTextBook.setETextBookId(rs.getInt("eTextBookID"));
            eTextBook.setCourseId(rs.getString("courseID"));
            eTextBook.setTitle(rs.getString("title"));
            eTextBook.setCourseName(rs.getString("courseName"));
            return eTextBook;
        }
    }

    public static class ETextBookRespModel2Mapper implements RowMapper<ETextBookRespModel2> {
        @Override
        public ETextBookRespModel2 mapRow(ResultSet rs, int rowNum) throws SQLException {
            ETextBookRespModel2 eTextBook = new ETextBookRespModel2();
            eTextBook.setUserTypeID(rs.getInt("userTypeID"));
            eTextBook.setCourseID(rs.getString("courseID"));
            eTextBook.setCourseName(rs.getString("courseName")); // Populate course name
            eTextBook.setCourseType(rs.getString("courseType"));
            eTextBook.setCourseTypeID(rs.getInt("courseTypeID"));
            eTextBook.setEtextBookID(rs.getInt("etextBookID"));
            eTextBook.setTitle(rs.getString("title"));
            return eTextBook;
        }
    }
}