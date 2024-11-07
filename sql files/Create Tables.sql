USE bsudala;

CREATE TABLE IF NOT EXISTS ETextBook (
    eTextBookID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    title VARCHAR(255) NOT NULL);

CREATE TABLE IF NOT EXISTS Chapter (
    chapterID INT AUTO_INCREMENT UNIQUE NOT NULL,
    chapterNumber VARCHAR(6) NOT NULL,
    title VARCHAR(255) NOT NULL,
    eTextBookID INT NOT NULL,
    hidden BOOLEAN DEFAULT 0,
    PRIMARY KEY (chapterID),
    UNIQUE (title, chapterNumber, eTextBookID),
    FOREIGN KEY (eTextBookID) REFERENCES ETextBook(eTextBookID)
    ON UPDATE CASCADE ON DELETE CASCADE,
    CHECK (chapterNumber REGEXP '^chap[0-9]{2,}$'));

CREATE TABLE IF NOT EXISTS Section (
    sectionID INT AUTO_INCREMENT UNIQUE NOT NULL,
    sectionNumber VARCHAR(6) NOT NULL,
    chapterID INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    hidden BOOLEAN DEFAULT 0,
    PRIMARY KEY (sectionID),
    UNIQUE (title, sectionNumber, chapterID),
    FOREIGN KEY (chapterID) REFERENCES Chapter(chapterID)
    ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS ContentBlock (
    ContentBlockID INT AUTO_INCREMENT UNIQUE NOT NULL,
    sectionID INT NOT NULL,
    image LONGTEXT,
    textBlock LONGTEXT,
    hidden BOOLEAN DEFAULT 0,
    PRIMARY KEY (contentBlockID),
    UNIQUE (contentBlockID, sectionID),
    FOREIGN KEY (sectionID) REFERENCES Section(sectionID)
    ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS Activity (
    activityID INT AUTO_INCREMENT UNIQUE NOT NULL,
    sectionID INT NOT NULL,
    contentBlockID INT NOT NULL,
    question LONGTEXT NOT NULL,
    points INT NOT NULL DEFAULT 0,
    hidden BOOLEAN DEFAULT 0,
    PRIMARY KEY (activityID),
    UNIQUE (activityID, sectionID),
    FOREIGN KEY (contentBlockID) REFERENCES ContentBlock(contentBlockID)
    ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (sectionID) REFERENCES Section(sectionID)
    ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS AnswerSet (
    answerSetID INT AUTO_INCREMENT UNIQUE NOT NULL,
    activityID INT NOT NULL,
    answerOption LONGTEXT NOT NULL,
    correct BOOLEAN NOT NULL DEFAULT 0,
    explanation LONGTEXT NOT NULL,
    PRIMARY KEY (activityID, answerSetID),
    FOREIGN KEY (activityID) REFERENCES Activity(activityID)
    ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS User (
    userID VARCHAR(8) NOT NULL UNIQUE,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    accountCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role ENUM('ADMIN', 'FACULTY', 'TEACHING_ASSISTANT', 'STUDENT') NOT NULL,
    PRIMARY KEY (userID),
    UNIQUE (userID, email));

CREATE TABLE IF NOT EXISTS Admin (
    adminID INT NOT NULL AUTO_INCREMENT,
    userID VARCHAR(8) NOT NULL,
    PRIMARY KEY (adminID),
    FOREIGN KEY (UserID) REFERENCES User(userID)
    ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS Faculty (
    facultyId INT NOT NULL AUTO_INCREMENT,
    userID VARCHAR(8) NOT NULL,
    PRIMARY KEY (facultyId),
    FOREIGN KEY (UserID) REFERENCES User(userID)
    ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS Student (
    studentID INT NOT NULL AUTO_INCREMENT,
    userID VARCHAR(8) NOT NULL,
    PRIMARY KEY (studentID),
    FOREIGN KEY (UserID) REFERENCES User(userID)
    ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS Course (
    courseID VARCHAR(30) NOT NULL,
    title VARCHAR(255) NOT NULL,
    facultyID INT NOT NULL,
    startDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    eTextBookID INT NOT NULL,
    PRIMARY KEY (courseID),
    FOREIGN KEY (facultyID) REFERENCES Faculty(facultyID)
    ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (eTextBookID) REFERENCES ETextBook(eTextBookID)
    ON UPDATE CASCADE ON DELETE RESTRICT);

CREATE TABLE IF NOT EXISTS EvaluationCourse (
    evaluationID INT NOT NULL AUTO_INCREMENT,
    courseID VARCHAR(30) NOT NULL,
    PRIMARY KEY (evaluationID),
    FOREIGN KEY (courseID) REFERENCES Course(courseID)
    ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS ActiveCourse (
    activeCourseID INT AUTO_INCREMENT NOT NULL,
    courseID VARCHAR(30) NOT NULL,
    capacity INT NOT NULL,
    token VARCHAR(7) NOT NULL,
    openToEnroll BOOLEAN NOT NULL DEFAULT 0,
    PRIMARY KEY (activeCourseID),
    FOREIGN KEY (courseID) REFERENCES Course(courseID)
    ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS TeachingAssistant (
    teachingAsstID INT NOT NULL AUTO_INCREMENT,
    userID VARCHAR(8) NOT NULL,
    activeCourseID INT NOT NULL,
    resetPassword BOOLEAN DEFAULT 1,
    PRIMARY KEY (teachingAsstID),
    FOREIGN KEY (userID) REFERENCES User(userID)
    ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (activeCourseID) REFERENCES ActiveCourse(activeCourseID)
    ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS Enrolled (
    enrolledID INT NOT NULL AUTO_INCREMENT,
    studentID INT NOT NULL,
    activeCourseID INT NOT NULL,
    courseScore INT NOT NULL DEFAULT 0,
    PRIMARY KEY (enrolledID),
    FOREIGN KEY (studentID) REFERENCES Student(studentID)
    ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (activeCourseID) REFERENCES ActiveCourse(activeCourseID)
    ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (studentID, activeCourseID));

CREATE TABLE IF NOT EXISTS Wait (
    waitListID INT NOT NULL AUTO_INCREMENT,
    studentID INT NOT NULL,
    activeCourseID INT NOT NULL,
    PRIMARY KEY (waitListId),
    FOREIGN KEY (studentID) REFERENCES Student(studentID)
    ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (activeCourseID) REFERENCES ActiveCourse(activeCourseID)
    ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS Notification (
    notificationID INT NOT NULL AUTO_INCREMENT,
    userID VARCHAR(8) NOT NULL,
    message LONGTEXT NOT NULL,
    messageRead BOOLEAN DEFAULT 0,
    PRIMARY KEY (notificationID),
    FOREIGN KEY (userID) REFERENCES User(userID)
    ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS StudentScore (
    scoreID INT NOT NULL AUTO_INCREMENT,
    studentID INT NOT NULL,
    activeCourseID INT NOT NULL,
    activityID INT NOT NULL,
    point INT DEFAULT 0,
    PRIMARY KEY (scoreID),
    FOREIGN KEY (studentID) REFERENCES Student(studentID)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (activityID) REFERENCES Activity(activityID)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (activeCourseID) REFERENCES ActiveCourse(activeCourseID)
    ON UPDATE CASCADE ON DELETE CASCADE,
    timeStamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (studentID, activeCourseID, activityID));

CREATE TABLE IF NOT EXISTS StudentScoreSummary (
    ssummaryID INT NOT NULL AUTO_INCREMENT,
    studentID INT NOT NULL,
    activeCourseID INT NOT NULL,
    totalPoints INT NOT NULL DEFAULT 0,
    totalActivities INT DEFAULT 0,
    PRIMARY KEY (ssummaryID),
    FOREIGN KEY (studentID) REFERENCES Student(studentID)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (activeCourseID) REFERENCES ActiveCourse(activeCourseID)
    ON UPDATE CASCADE ON DELETE CASCADE,
    timeStamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (studentID, activeCourseID));
    
-- Trigger and Procedures

DELIMITER //

-- Procedure to run before user insert for uesrid
CREATE PROCEDURE GenerateUserID(
    IN first_name VARCHAR(50),
    IN last_name VARCHAR(50),
    OUT user_id VARCHAR(20)
)
BEGIN
    DECLARE fn_prefix CHAR(2) DEFAULT LEFT(first_name, 2);
    DECLARE ln_prefix CHAR(2) DEFAULT LEFT(last_name, 2);
    DECLARE timestamp_part VARCHAR(4) DEFAULT DATE_FORMAT(NOW(), '%H%i');
    SET user_id = CONCAT(fn_prefix, ln_prefix, timestamp_part);
END //

-- Trigger to run before user insert for uesrid
CREATE TRIGGER before_user_insert
BEFORE INSERT ON User
FOR EACH ROW
BEGIN
    DECLARE generated_user_id VARCHAR(20);
    CALL GenerateUserID(NEW.firstName, NEW.lastName, generated_user_id);
    SET NEW.userID = generated_user_id;
END //

-- Procedure to run before insert to score table
CREATE PROCEDURE UpdateOrInsertStudentScore(
    IN p_studentID INT,
    IN p_activeCourseID INT,
    IN p_activityID INT,
    IN p_point INT
)
BEGIN
    DECLARE existing_point INT;
    SELECT point
    INTO existing_point
    FROM StudentScore
    WHERE studentID = p_studentID
      AND activeCourseID = p_activeCourseID
      AND activityID = p_activityID
      AND point < p_point
    LIMIT 1;
    IF existing_point IS NOT NULL THEN
        UPDATE StudentScore
        SET point = 3
        WHERE studentID = p_studentID
          AND activeCourseID = p_activeCourseID
          AND activityID = p_activityID
          AND point < p_point;
    ELSE
        INSERT INTO StudentScore (studentID, activeCourseID, activityID, point)
        VALUES (p_studentID, p_activeCourseID, p_activityID, p_point);
    END IF;
END //

-- Trigger to run before insert to score table
CREATE TRIGGER update_before_student_score_insert
BEFORE INSERT ON StudentScore
FOR EACH ROW
BEGIN
    CALL UpdateOrInsertStudentScore(NEW.studentID, NEW.activeCourseID, NEW.activityID, NEW.point);
    SET NEW.studentID = NULL;
    SET NEW.activeCourseID = NULL;
    SET NEW.activityID = NULL;
    SET NEW.point = NULL;
END //

DELIMITER ;

DROP PROCEDURE UpdateOrInsertStudentScore;
DROP TRIGGER update_before_student_score_insert;

DELIMITER //

-- Procedure to run after insert to score table to update summary table
CREATE PROCEDURE UpdateStudentScoreSummary(IN p_studentID INT, IN p_activeCourseID INT)
BEGIN
    DECLARE total_points INT;
    DECLARE total_activities INT;

    SELECT SUM(point), COUNT(*)
    INTO total_points, total_activities
    FROM StudentScore
    WHERE studentID = p_studentID
      AND activeCourseID = p_activeCourseID;

    IF EXISTS (SELECT 1 FROM StudentScoreSummary WHERE studentID = p_studentID AND activeCourseID = p_activeCourseID) THEN
        UPDATE StudentScoreSummary
        SET totalPoints = total_points, totalActivities = total_activities
        WHERE studentID = p_studentID AND activeCourseID = p_activeCourseID;
    ELSE
        INSERT INTO StudentScoreSummary (studentID, activeCourseID, totalPoints, totalActivities)
        VALUES (p_studentID, p_activeCourseID, total_points, total_activities);
    END IF;
END //

-- Procedure to run after insert to score table to update summary table
CREATE TRIGGER after_student_score_insert
AFTER INSERT ON StudentScore
FOR EACH ROW
BEGIN
    CALL UpdateStudentScoreSummary(NEW.studentID, NEW.activeCourseID);
END //

-- Procedure to run after update to score table to update summary table
CREATE TRIGGER after_student_score_update
AFTER UPDATE ON StudentScore
FOR EACH ROW
BEGIN
    CALL UpdateStudentScoreSummary(NEW.studentID, NEW.activeCourseID);
END //

DELIMITER ;