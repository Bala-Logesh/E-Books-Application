package com.ncsu.ebooks.misc.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/query")
public class Query {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/1/{etextbookID}")
    public int query1(@PathVariable("etextbookID") int etextbookID) {
        String chapterQuery = "SELECT chapterID " +
                "FROM Chapter " +
                "WHERE eTextBookID = ? " +
                "ORDER BY chapterID ASC " +
                "LIMIT 1";

        Integer chapterID = jdbcTemplate.queryForObject(chapterQuery, Integer.class, etextbookID);

        if (chapterID != null) {
            String sectionQuery = "SELECT COUNT(sectionID) " +
                    "FROM Section " +
                    "WHERE chapterID = ?";
            return jdbcTemplate.queryForObject(sectionQuery, Integer.class, chapterID);
        }
        return 0;
    }

    @GetMapping("/2")
    public List<CoursePersonDTO> getTeachingAssistantsAndFaculty() {
        String sql = "SELECT " +
                "    u.firstName AS first_name, " +
                "    u.lastName AS last_name, " +
                "    u.role AS role, " +
                "    c.courseID AS courseID " +
                "FROM " +
                "    Course c " +
                "JOIN " +
                "    Faculty f ON c.facultyID = f.facultyId " +
                "JOIN " +
                "    User u ON f.userID = u.userID " +
                "WHERE " +
                "    u.role = 'FACULTY' " +
                "UNION " +
                "SELECT " +
                "    u.firstName AS first_name, " +
                "    u.lastName AS last_name, " +
                "    u.role AS role, " +
                "    c.courseID AS courseID " +
                "FROM " +
                "    TeachingAssistant ta " +
                "JOIN " +
                "    User u ON ta.userID = u.userID " +
                "JOIN " +
                "    ActiveCourse ac ON ta.activeCourseID = ac.activeCourseID " +
                "JOIN " +
                "    Course c ON ac.courseID = c.courseID " +
                "WHERE " +
                "    u.role = 'TEACHING_ASSISTANT'";

        return jdbcTemplate.query(sql, new RowMapper<CoursePersonDTO>() {
            @Override
            public CoursePersonDTO mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
                return new CoursePersonDTO(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("role"),
                        rs.getString("courseID")
                );
            }
        });
    }

    @GetMapping("/3")
    public List<ActiveCourseDetailsDTO> getActiveCourseDetails() {
        // Updated SQL query with Enrolled table
        String sql = "SELECT " +
                "    ac.activeCourseID AS activeCourseID, " +
                "    c.courseID AS courseID, " +
                "    u.firstName AS facultyFirstName, " +
                "    u.lastName AS facultyLastName, " +
                "    COUNT(e.studentID) AS totalStudents " +
                "FROM " +
                "    ActiveCourse ac " +
                "JOIN " +
                "    Course c ON ac.courseID = c.courseID " +
                "JOIN " +
                "    Faculty f ON c.facultyID = f.facultyId " +
                "JOIN " +
                "    User u ON f.userID = u.userID " +
                "LEFT JOIN " +
                "    Enrolled e ON e.activeCourseID = ac.activeCourseID " +
                "GROUP BY " +
                "    ac.activeCourseID, c.courseID, u.firstName, u.lastName";

        // Execute the query and map the result to ActiveCourseDetails objects
        return jdbcTemplate.query(sql, new RowMapper<ActiveCourseDetailsDTO>() {
            @Override
            public ActiveCourseDetailsDTO mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
                return new ActiveCourseDetailsDTO(
                        rs.getString("courseID"),
                        rs.getString("facultyFirstName"),
                        rs.getString("facultyLastName"),
                        rs.getInt("totalStudents")
                );
            }
        });
    }

    @GetMapping("/4")
    public CourseWaitListDTO getCourseWithLargestWaitlist() {
        String sql = "SELECT " +
                "    ac.courseID AS courseID, " +
                "    COUNT(w.studentID) AS totalWaitList " +
                "FROM " +
                "    Wait w " +
                "JOIN " +
                "    ActiveCourse ac ON w.activeCourseID = ac.activeCourseID " +
                "GROUP BY " +
                "    ac.courseID " +
                "ORDER BY " +
                "    totalWaitList DESC " +
                "LIMIT 1";

        return jdbcTemplate.queryForObject(sql, new RowMapper<CourseWaitListDTO>() {
            @Override
            public CourseWaitListDTO mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
                return new CourseWaitListDTO(
                        rs.getString("courseID"),
                        rs.getInt("totalWaitList")
                );
            }
        });
    }

    @GetMapping("/5")
    public List<ContentDTO> getChapterData() {
        String query =
                "SELECT " +
                        "s.sectionNumber, " +
                        "s.title AS sectionTitle, " +
                        "cb.contentBlockID, " +
                        "cb.textBlock, " +
                        "cb.image, " +
                        "a.activityID, " +
                        "a.question, " +
                        "a.points, " +
                        "ans.answerSetID, " +
                        "ans.answerOption, " +
                        "ans.correct, " +
                        "ans.explanation " +
                        "FROM " +
                        "Chapter ch " +
                        "JOIN Section s ON ch.chapterID = s.chapterID " +
                        "JOIN ContentBlock cb ON s.sectionID = cb.sectionID " +
                        "LEFT JOIN Activity a ON cb.contentBlockID = a.contentBlockID " +
                        "LEFT JOIN AnswerSet ans ON a.activityID = ans.activityID " +
                        "WHERE ch.eTextBookID = ? " +
                        "AND ch.chapterID = ? " +
                        "ORDER BY s.sectionNumber, cb.contentBlockID, a.activityID, ans.answerSetID";

        return jdbcTemplate.query(query, new Object[]{101, 1},
                (rs, rowNum) -> {
                    ContentDTO chapterData = new ContentDTO();
                    chapterData.setSectionNumber(rs.getString("sectionNumber"));
                    chapterData.setSectionTitle(rs.getString("sectionTitle"));
                    chapterData.setContentBlockID(rs.getInt("contentBlockID"));
                    chapterData.setTextBlock(rs.getString("textBlock"));
                    chapterData.setImage(rs.getString("image"));
                    chapterData.setActivityID(rs.getInt("activityID"));
                    chapterData.setQuestion(rs.getString("question"));
                    chapterData.setAnswerSetID(rs.getInt("answerSetID"));
                    chapterData.setAnswerOption(rs.getString("answerOption"));
                    chapterData.setCorrect(rs.getBoolean("correct"));
                    chapterData.setExplanation(rs.getString("explanation"));
                    return chapterData;
                });
    }

    @GetMapping("/6")
    public List<AnswerDTO> getIncorrectAnswers() {
        String query =
                "SELECT " +
                        "ans.answerOption, " +
                        "ans.explanation " +
                        "FROM " +
                        "Chapter ch " +
                        "JOIN Section s ON ch.chapterID = s.chapterID " +
                        "JOIN ContentBlock cb ON s.sectionID = cb.sectionID " +
                        "LEFT JOIN Activity a ON cb.contentBlockID = a.contentBlockID " +
                        "LEFT JOIN AnswerSet ans ON a.activityID = ans.activityID " +
                        "WHERE ch.eTextBookID = ? " +
                        "AND ch.chapterID = ? " +
                        "AND s.sectionID = ? " +
                        "AND a.activityID = ? " +
                        "AND ans.correct = false " +
                        "ORDER BY ans.answerSetID";

        return jdbcTemplate.query(query, new Object[]{101, 1, 2, 1},
                (rs, rowNum) -> {
                    AnswerDTO answer = new AnswerDTO();
                    answer.setAnswerOption(rs.getString("answerOption"));
                    answer.setExplanation(rs.getString("explanation"));
                    return answer;
                });
    }

    @GetMapping("/7")
    public List<TextbookCoursesDTO> findBooksInActiveAndEvaluationCourses() {
        String sql = "SELECT " +
                "    c.eTextBookID, " +
                "    et.title AS textbookTitle, " +
                "    GROUP_CONCAT(DISTINCT c.courseID ORDER BY c.courseID) AS courseIDs " +
                "FROM " +
                "    Course c " +
                "LEFT JOIN " +
                "    ActiveCourse ac ON c.courseID = ac.courseID " +
                "LEFT JOIN " +
                "    EvaluationCourse ec ON c.courseID = ec.courseID " +
                "JOIN " +
                "    ETextBook et ON c.eTextBookID = et.eTextBookID " +
                "WHERE " +
                "    ac.courseID IS NOT NULL OR ec.courseID IS NOT NULL " +
                "GROUP BY " +
                "    c.eTextBookID, et.title";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        return rows.stream().map(row -> {
            int eTextBookID = (Integer) row.get("eTextBookID");
            String textbookTitle = (String) row.get("textbookTitle");
            String courseIDsStr = (String) row.get("courseIDs");

            List<String> courseIDs = List.of(courseIDsStr.split(","));

            return new TextbookCoursesDTO(eTextBookID, textbookTitle, courseIDs);
        }).toList();
    }
}
