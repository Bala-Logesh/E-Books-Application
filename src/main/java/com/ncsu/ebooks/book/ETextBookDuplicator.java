package com.ncsu.ebooks.book;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ETextBookDuplicator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int duplicateETextBook(int eTextBookId) {
        // Step 1: Duplicate the ETextBook
        int newETextBookId = duplicateEntity("ETextBook", "eTextBookID", eTextBookId);

        // Step 2: Duplicate Chapters
        List<Integer> chapterIds = jdbcTemplate.queryForList(
                "SELECT chapterID FROM Chapter WHERE ETextBookID = ?", Integer.class, eTextBookId);

        for (int chapterId : chapterIds) {
            int newChapterId = duplicateEntityWithForeignKey("Chapter", "chapterID", "eTextBookID", chapterId, newETextBookId);

            // Step 3: Duplicate Sections
            List<Integer> sectionIds = jdbcTemplate.queryForList(
                    "SELECT sectionID FROM Section WHERE chapterID = ?", Integer.class, chapterId);

            for (int sectionId : sectionIds) {
                int newSectionId = duplicateEntityWithForeignKey("Section", "sectionID", "chapterID", sectionId, newChapterId);

                // Step 4: Duplicate ContentBlocks
                List<Integer> contentBlockIds = jdbcTemplate.queryForList(
                        "SELECT contentBlockID FROM ContentBlock WHERE sectionID = ?", Integer.class, sectionId);

                for (int contentBlockId : contentBlockIds) {
                    int newContentBlockID = duplicateEntityWithForeignKey("ContentBlock", "contentBlockID", "sectionID", contentBlockId, newSectionId);

                    // Step 5: Duplicate Activities
                    List<Integer> activityIds = jdbcTemplate.queryForList(
                            "SELECT activityID FROM Activity WHERE sectionID = ? AND contentBlockID = ?", Integer.class, sectionId, contentBlockId);

                    for (int activityId : activityIds) {
                        int newActivityId = duplicateEntityWithMultipleForeignKeys(
                                "Activity", "activityID", activityId,
                                new String[]{"sectionID", "contentBlockID"},
                                new int[]{newSectionId, newContentBlockID}
                        );


                        // Step 6: Duplicate AnswerSets
                        List<Integer> answerSetIds = jdbcTemplate.queryForList(
                                "SELECT answerSetID FROM AnswerSet WHERE activityID = ?", Integer.class, activityId);

                        for (int answerSetId : answerSetIds) {
                            duplicateEntityWithForeignKey("AnswerSet", "answerSetID", "activityID", answerSetId, newActivityId);
                        }
                    }
                }
            }
        }
        return newETextBookId;
    }
    private int duplicateEntityWithAllFields(
            String tableName, String idColumn, int id,
            String[] fkColumns, int[] newFkValues) {

        // Construct the list of foreign key assignments for the INSERT SELECT statement
        StringBuilder fkAssignments = new StringBuilder();
        for (int i = 0; i < fkColumns.length; i++) {
            if (i > 0) fkAssignments.append(", ");
            fkAssignments.append(" ? AS ").append(fkColumns[i]);
        }

        // SQL query to copy all fields, replacing only the foreign key values
        String sql = String.format(
                "INSERT INTO %s (%s) " +
                        "SELECT %s FROM %s WHERE %s = ?",
                tableName,
                buildColumnList(tableName, idColumn),
                buildSelectList(tableName, fkColumns),
                tableName,
                idColumn
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set the foreign key values in the INSERT SELECT statement
            for (int i = 0; i < newFkValues.length; i++) {
                ps.setInt(i + 1, newFkValues[i]);
            }

            // Set the original id at the end for WHERE clause
            ps.setInt(newFkValues.length + 1, id);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    // Helper function to build SELECT list with one foreign key overridden
    private String buildSelectListWithForeignKeyOverride(String tableName, String fkColumn, String pkColumn) {
        List<String> columns = jdbcTemplate.query(
                String.format("SHOW COLUMNS FROM %s", tableName),
                (rs, rowNum) -> rs.getString("Field")
        );

        return columns.stream()
                .filter(col -> !col.equals(pkColumn))                // Exclude primary key column
                .map(col -> col.equals(fkColumn) ? "?" : col)        // Replace foreign key column with '?'
                .collect(Collectors.joining(", "));
    }


    // Helper function to build a comma-separated column list excluding the primary key
    private String buildColumnList(String tableName, String idColumn) {
        // Query to get column names dynamically (modify based on your JDBC metadata support)
        List<String> columns = jdbcTemplate.query(
                String.format("SHOW COLUMNS FROM %s", tableName),
                (rs, rowNum) -> rs.getString("Field")
        );
        // Remove the primary key column
        columns.remove(idColumn);

        // Return as comma-separated string
        return String.join(", ", columns);
    }

    // Helper function to build the SELECT part with placeholders for foreign key overrides
    private String buildSelectList(String tableName, String[] fkColumns) {
        List<String> columns = jdbcTemplate.query(
                String.format("SHOW COLUMNS FROM %s", tableName),
                (rs, rowNum) -> rs.getString("Field")
        );

        // Map foreign key columns to '?' placeholders and keep others as-is
        return columns.stream()
                .map(col -> Arrays.asList(fkColumns).contains(col) ? "?" : col)
                .collect(Collectors.joining(", "));
    }

    private int duplicateEntity(String tableName, String idColumn, int id) {
        // Build the column list without the primary key column
        String columns = buildColumnList(tableName, idColumn);

        // Build the SQL statement to copy all columns except the primary key
        String sql = String.format(
                "INSERT INTO %s (%s) SELECT %s FROM %s WHERE %s = ?",
                tableName, columns, columns, tableName, idColumn
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    private int duplicateEntityWithForeignKey(String tableName, String idColumn, String fkColumn, int id, int newFkValue) {
        // Build the column list excluding the primary key column
        String columns = buildColumnList(tableName, idColumn);

        String selectList = buildSelectListWithForeignKeyOverride(tableName, fkColumn, idColumn);

        // Build the SQL statement to copy all columns except the primary key, overriding the foreign key
        String sql = String.format(
                "INSERT INTO %s (%s) SELECT %s FROM %s WHERE %s = ?",
                tableName, columns, selectList, tableName, idColumn
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, newFkValue);
            ps.setInt(2, id);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

private int duplicateEntityWithMultipleForeignKeys(
        String tableName, String idColumn, int id,
        String[] fkColumns, int[] newFkValues) {

    // Get all column names except the primary key column
    String columns = buildColumnList(tableName, idColumn);

    // Create lists for the column names and values, handling placeholders for foreign keys
    StringBuilder columnsPart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    String[] allColumns = columns.split(", ");
    for (String col : allColumns) {
        if (columnsPart.length() > 0) {
            columnsPart.append(", ");
            valuesPart.append(", ");
        }

        if (Arrays.asList(fkColumns).contains(col)) {
            valuesPart.append("?");
        } else {
            valuesPart.append(col);
        }
        columnsPart.append(col);
    }

    // SQL statement for duplication with placeholders for foreign keys
    String sql = String.format("INSERT INTO %s (%s) SELECT %s FROM %s WHERE %s = ?",
            tableName, columnsPart, valuesPart, tableName, idColumn);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // Set foreign key values
        for (int i = 0; i < newFkValues.length; i++) {
            ps.setInt(i + 1, newFkValues[i]);
        }
        // Set the ID for the WHERE clause
        ps.setInt(newFkValues.length + 1, id);
        return ps;
    }, keyHolder);

    return Objects.requireNonNull(keyHolder.getKey()).intValue();
}


}
