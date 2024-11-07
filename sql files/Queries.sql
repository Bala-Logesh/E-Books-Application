-- Query 1: Write a query that prints the number of sections of the first chapter of a textbook.
-- Let the ID of the textbook be ETBId
SELECT COUNT(*)
FROM Section s
JOIN Chapter c ON s.ChapterID = c.ChapterID
JOIN ETextBook e ON c.ETextBookID = e.ETextBookID
WHERE e.ETextBookID = 101 AND c.ChapterID = 1;


-- Query 2: Print the names of faculty and TAs of all courses. For each person print their role next to
-- their names.
SELECT
	CONCAT(u.FirstName, ' ', u.LastName) AS Name,
	'Faculty' AS Role
FROM
	Faculty f
JOIN
	User u ON f.UserID = u.UserID
JOIN
    Course c ON f.FacultyID = c.FacultyID
    
UNION

SELECT
	CONCAT(u.FirstName, ' ', u.LastName) AS Name,
	'Teaching Assistant' AS Role
FROM
	TeachingAssistant ta
JOIN
	User u ON ta.UserID = u.UserID
JOIN
	ActiveCourse ac ON ta.ActiveCourseID = ac.ActiveCourseID

-- 3. For each active course, print the course id, faculty member and total number of students
SELECT ac.courseID,
       CONCAT(u.FirstName, ' ', u.LastName) AS FacultyMember,
       COUNT (e.StudentID) AS TotalStudents
FROM ActiveCourse ac
JOIN Course c ON ac.courseID = c.courseID
JOIN Faculty f ON f.facultyId = c.facultyID
JOIN User u ON f.userID = u.userID
LEFT JOIN Enrolled e ON e.activeCourseID = ac.activeCourseID
GROUP BY ac.courseID, FacultyMember;
-- 4. Find the course which the largest waiting list, print the course id and the total number of
-- people on the list
SELECT w.activeCourseID,
       COUNT(w.studentID) AS TotalWaitlisted
FROM Wait w
GROUP BY w.activeCourseID
ORDER BY TotalWaitlisted DESC
LIMIT 1;
-- 5. Print the contents of Chapter 02 of textbook 101 in proper sequence.
SELECT
    cb.contentBlockID,
    cb.image,
    cb.textBlock -- need to add activities
FROM
    Chapter c
        JOIN
    Section s ON c.chapterID = s.chapterID
        JOIN
    ContentBlock cb ON s.sectionID = cb.sectionID
WHERE
    c.chapterNumber = '02' AND c.eTextBookID = '101'
ORDER BY
    s.sectionNumber, cb.contentBlockID;

-- 6. For Q2 of Activity0 in Sec02 of Chap01 in textbook 101, print the incorrect answers for
-- that question and their corresponding explanations.

SELECT
    ans.AnswerOption,
    ans.Explanation
FROM
    Chapter ch
        JOIN
    Section sec ON ch.ChapterID = sec.ChapterID
        JOIN
    Activity act ON sec.SectionID = act.SectionID
        JOIN
    AnswerSet ans ON act.ActivityID = ans.ActivityID
WHERE
    ch.ETextBookID = '101'
  AND ch.ChapterNumber = '01'
  AND sec.SectionNumber = '02'
  AND act.ActivityID = 'Activity0'
  AND ans.Question = 'Q2' -- need to resolve this issue
  AND ans.Correct = FALSE
ORDER BY
    ans.AnswerOption;

-- 7. Find any book that is in active status by one instructor but evaluation status by a different
-- instructor.
-- a. P.S. Active/Evaltuation status means that the textbook belongs to active/evaluation
-- course.

-- Not sure how to do this...
SELECT
    ac.ETextBookID AS BookID,
    u1.UserID AS ActiveInstructor,
    u2.UserID AS EvaluationInstructor
FROM
    ActiveCourse ac
        JOIN
    Course c ON ac.courseID = c.courseID
    JOIN
    Faculty f1 ON c.FacultyID = f1.facultyId
        JOIN
    User u1 ON f1.UserID = u1.UserID
        JOIN
    EvaluationCourse ec ON ac.ETextBookID = ec.ETextBookID
        JOIN
    Faculty f2 ON ec.CourseID = f2.CourseID
        JOIN
    User u2 ON f2.UserID = u2.UserID
WHERE
    u1.UserID <> u2.UserID;
