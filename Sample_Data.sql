INSERT INTO ETextBook (eTextBookID, title)
VALUES (101, "Database Management Systems"),
       (102, "Fundamentals of Software Engineering"),
       (103, "Fundamentals of Machine Learning");

INSERT INTO Chapter (chapterNumber, title, eTextBookID, hidden)
VALUES ("chap01", "Introduction to Database", 101,0),
       ("chap02", "The Relational Model", 101, 0),
       ("chap01", "Introduction to Software Engineering", 102, 0),
       ("chap02", "Introduction to Software Development to Life Cycle", 102, 0),
       ("chap01", "Introduction to Machine Learning", 103, 0);

INSERT INTO Section (sectionNumber, chapterID, title, hidden)
VALUES ("Sec01", 1, "Database Management Systems (DBMS) Overview", 0),
       ("Sec02", 1, "Data Models and Schemas", 0),
       ("Sec01", 2, "Entities, Atributes, and Relationships", 0),
       ("Sec02", 2, "Normalization and Integrity Constraints", 0),
       ("Sec01", 3, "History and Evolution of Software Engineering", 0),
       ("Sec02", 3, "Key Principles of Software Engineering", 0),
       ("Sec01", 4, "Phases of the SDLC", 1),
       ("Sec02", 4, "Agile vs. Waterfall Models", 0),
       ("Sec01", 5, "Overview of Machine Learning", 1),
       ("Sec02", 5, "Supervised vs Unsupervised Learning", 0);

INSERT INTO ContentBlock (sectionID, image, textBlock, hidden)
VALUES (1, NULL, "A Database Management System (DBMS) is software that enables users to
efficiently create, manage, and manipulate databases. It serves as an interface between the database and end users,
ensuring data is stored securely, retrieved accurately, and maintained consistently. Key features of a DBMS include
data organization, transaction management, and support for multiple users accessng data concurrently.", 0),
       (2, NULL, NULL, 0),
       (3, NULL, "DBMS systems provide structured storage and ensure that data is accessible
through queries using languages like SQL. They handle critical tasks such as maintaining data integrity, enforcing
security protocols, and optimizing data retrieval, making them essential for both small-scale and enterprise-level
applications. Examples of popular DBMS include MySQL, Oracle, and PostgreSQL.",  0),
       (4, "sample.png", NULL, 0),
       (5, NULL, "The history of software engineering dates back to the 1960s, when the \"software
 crisis\" highlighted the need for structured approaches to software development due to rising complexity and project
failures. Over time, methodologies such as Waterfall, Agile, and DevOps evolved, transforming software engineering into
a disciplined, iterative process that emphasizes efficiency, collaboration, and adaptability.", 0),
       (6, NULL, NULL, 0),
       (7, NULL, "The Software Development Life Cycle (SDLC) consists of key phases including
requirements gathering, design, development, testing, deployment, and maintenance. Each phase plays a crucial role in
ensuring that software is built systematically, with feedback and revisions incorporated at each step to deliver a high-
quality product.", 0),
       (8, "sample2.png", NULL, 0),
       (9, NULL, "Machine learning is a subset of artificial intelligence that enables systems to
learn from data, identify patterns, and make decisions with minimal human intervention. By training algorithms on vast
datasets, machine learning models can improve their accuracy over time, driving advancements in fields like healthcare,
finance, and autonomous systems.", "no"),
       (10, NULL, NULL, 0);

INSERT INTO Activity (sectionID, contentBlockID, question, hidden)
VALUES (2, 2, "What does a DBMS provide?", 0),
       (2, 2, "Which of these is an example of a DBMS?", 0),
       (2, 2, "What type of data does a DBMS manage?", 0),
       (6, 6, "What was the \"software crisis\"?", 0),
       (6, 6, "Which methodology was first introduced in software engineering?", 0),
       (6, 6, "What challenge did early software engineering face?", 0),
       (10, 10, "What is the primary goal of supervised learning?", 0),
       (10, 10, "Which type of data is used in unsupervised learning?", 0),
       (10, 10, "In which scenario would you typically use supervised learning?", 0);

INSERT INTO AnswerSet (activityID, answerOption, correct, explanation)
VALUES (1, "Data storage only", 0, "Incorrect: DBMS provides more than just storage"),
       (1, "Data storage and retrieval", 1, "Correct: DBMS manages both storing and retrieving data"),
       (1, "Only security features", 0, "Incorrect: DBMS also handles other functions"),
       (1, "Network management", 0, "Incorrect: DBMS does not support network infrastructure"),
       (2, "Microsoft Excel", 0, "Incorrect: Excel is a spreadsheet application"),
       (2, "MySQL", 1, "Correct: MySQL is a popular DBMS"),
       (2, "Google Chrome", 0, "Incorrect: Chrome is a web browser"),
       (2, "Windows 10", 0, "Incorrect: Windows is an operating system"),
       (3, "Structured data", 1, "Correct: DBMS primarily manages structured data"),
       (3, "Unstructured multimedia", 0, "Incorrect: While some DBMS systems can handle it, it's not core"),
       (3, "Network traffic data", 0, "Incorrect: DBMS doesn't manage network data"),
       (3, "Hardware usage statistics", 0, "Incorrect: DBMS does not handle hardware usage data"),
       (4, "A hardware storage", 0, "Incorrect: The crisis was related to software development issues"),
       (4, "Difficult in software creation", 1, "Correct: The crisis was due to the complexity and unreliability of software"),
       (4, "A network issue", 0, "Incorrect: It was not related to networking"),
       (4, "Lack of storage devices", 0, "Incorrect: The crisis was not about physical storage limiations"),
       (5, "Waterfall model", 1, "Correct: Waterfall was the first formal software development model"),
       (5, "Agile methodology", 0, "Incorrect: Agile was introduced much later"),
       (5, "DevOps", 0, "Incorrect: DevOps is a more recent development approach"),
       (5, "Scrum", 0, "Incorrect: Scrum is a part of Agile, not the first methodology"),
       (6, "Lack of programming languages", 0, "Incorrect: Programming languages existed but were difficult to manage"),
       (6, "Increasing complexity of software", 1, "Correct: Early engineers struggled with managing large, complex systems"),
       (6, "Poor hardware development", 0, "Incorrect: The issue was primarily with software, not hardware"),
       (6, "Internet connectivity issues", 0, "Incorrect: Internet connectivity wasn't a challenge in early software"),
       (7, "Predict outcomes", 1, "Correct: The goal is to learn a mapping from inputs to outputs for prediction."),
       (7, "Group similar data", 0, "Incorrect: This is a more aligned with unsupervised learning."),
       (7, "Discover patterns", 0, "Incorrect: This is not the main goal of supervised learning."),
       (7, "Optimize cluster groups", 0, "Incorrect: This is not applicable to supervised learning."),
       (8, "Labeled data", 0, "Incorrect: Unsupervised learning used unlabeled data."),
       (8, "Unlabeled data", 1, "Correct: It analyzes data without pre-existing labels."),
       (8, "Structured data", 0, "Incorrect: Unlabeled data can be structured or unstructured."),
       (8, "Time-series data", 0, "Incorrect: Unsupervised learning does not specifically focus on time-series."),
       (9, "Customer segmentation", 0, "Incorrect: This is more relevant to unsupervised learning"),
       (9, "Fraud detection", 1, "Correct: Supervised learning is ideal for predicting fraud based on labeled examples."),
       (9, "Market based analysis", 0, "Incorrect: This is generally done using unsupervised methods."),
       (9, "Anamoly detection", 0, "Incorrect: While applicable, it is less common than fraud detection in supervised learning.");

INSERT INTO User (userID, firstName, lastName, email, password, role)
VALUES ("ErPe1024", "Eric", "Perrig", "ez356@example.mail", "qwdmq", "STUDENT"),
       ("AlAr1024", "Alice", "Artho", "aa23@edu.mail", "omdsws", "STUDENT"),
       ("BoTe1024", "Bob", "Temple", "bt163@template.mail", "sak+=", "STUDENT"),
       ("LiGa1024", "Lily", "Gaddy", "li123@example.edu", "cnaos", "STUDENT"),
       ("ArMo1024", "Aria", "Marrow", "am213@example.edu", "jwocals", "STUDENT"),
       ("KeRh1014", "Kellan", "Rhodes", "kr21@example.edu", "camome", "STUDENT"),
       ("SiHa1024", "Sienna", "Hayes", "sh13@example.edu", "asdqm", "STUDENT"),
       ("FiWi1024", "Finn", "Wilder", "fw23@example.edu", "f13mas", "STUDENT"),
       ("LeMe1024", "Leona", "Mercer", "lm56@example.edu", "ca32", "STUDENT"),
       ("JaWi1024", "James", "Williams", "jwilliams@ncsu.edu", "jwilliams@1234", "TEACHING_ASSISTANT"),
       ("LiAl0924", "Lisa", "Alberti", "lalberti@ncsu.edu", "lalberti&5678@", "TEACHING_ASSISTANT"),
       ("DaJo1024", "David", "Johnson", "djohnson@ncsu.edu", "djohnson%@1122", "TEACHING_ASSISTANT"),
       ("ElCl1024", "Ellie", "Clark", "eclark@ncsu.edu", "eclark^#3654", "TEACHING_ASSISTANT"),
       ("JeGi0924", "Jeff", "Gibson", "jgibson@ncsu.edu", "jgibson$#9877", "TEACHING_ASSISTANT"),
       ("KeOg1024", "Kemafor", "Ogan", "kogan@ncsu.edu", "Ko2024!rpc", "FACULTY"),
       ("JoDo1024", "John", "Doe", "john.doe@example.com", "Jd2024!abc", "FACULTY"),
       ("SaMi1024", "Sarah", "Miller", "sarah.miller@domain.com", "Sm#Secure2024", "FACULTY"),
       ("DaBr1024", "David", "Brown", "david.b@webmail.com", "DbPass123!", "FACULTY"),
       ("EmDa1024", "Emily", "Davis", "emily.davis@email.com", "Emily#2024!", "FACULTY"),
       ("MiWi1024", "Michael", "Wilson", "michael.w@service.com", "Mw987secure", "FACULTY");

INSERT INTO Student (userID)
VALUES ("ErPe1024"),
       ("AlAr1024"),
       ("BoTe1024"),
       ("LiGa1024"),
       ("ArMo1024"),
       ("KeRh1014"),
       ("SiHa1024"),
       ("FiWi1024"),
       ("LeMe1024");

INSERT INTO Faculty (userID)
VALUES ("KeOg1024"),
       ("JoDo1024"),
       ("SaMi1024"),
       ("DaBr1024"),
       ("EmDa1024"),
       ("MiWi1024");

INSERT INTO Course (courseID, title, facultyID, startDate, endDate, eTextBookID)
VALUES ("NCSUOganCSC440F24", "CSC440 Database Systems", "KeOg1024", "2024-08-15", "2024-12-15", 101),
       ("NCSUOganCSC540F24", "CSC540 Database Systems", "KeOg1024", "2024-08-17", "2024-12-15", 101),
       ("NCSUSaraCSC326F24", "CSC326 Software Engineering", "SaMi1024", "2024-08-23", "2024-10-23", 101),
       ("NCSUDoeCSC522F24", "CSC522 Fundamentals of Machine Learning", "JoDo1024", "2025-08-25", "2025-12-18", 103),
       ("NCSUSaraCSC326F25", "CSC326 Software Engineering", "SaMi1024", "2025-08-27", "2025-12-19", 102);

INSERT INTO ActiveCourse (activeCourseID, courseID, capacity)
VALUES ("XYJKLM", 1, 60),
       ("STUKZT", 2, 50),
       ("LRUFND", 3, 100);

INSERT INTO EvaluationCourse (courseID)
VALUES ("NCSUDoeCSC522F24"),
       ("NCSUSaraCSC326F25");

-- Need to do Scores by Textbook too
# INSERT INTO Scores (studentID, activeCourseID, courseScore, finishedActivities)
# VALUES (1, "NCSUOganCSC440F24", 4, 2),
#        (1, "NCSUOganCSC540f24", 1, 1),
#        (2, "NCSUOganCSC440F24", 3, 1),
#        (3, "NCSUOganCSC440F24", 0, 1),
#        (4, "NCSUOganCSC440F24", 9, 3),
#        (4, "NCSUOganCSC540F24", 0, 0),
#        (5, "NCSUOganCSC540F24", 0, 0),
#        (5, "NCSUOganCSC440F24", 4, 2),
#        (7, "NCSUOganCSC440F24", 0, 0),
#        (8, "NCSUSaraCSC326F24", 1, 1);

INSERT INTO Enrolled (studentID, activeCourseID)
VALUES (1, "XYJKLM"),
       (1, "STUKZT"),
       (2, "XYJKLM"),
       (3, "XYJKLM"),
       (4, "XYJKLM"),
       (4, "STUKZT"),
       (5, "STUKZT"),
       (5, "XYJKLM"),
       (7, "XYJKLM"),
       (8, "LRUFND"),
       (9, "XYJKLM");

INSERT INTO Wait (studentID, activeCourseID)
VALUES (8, "XYJKLM"),
       (9, "STUKZT"),
       (2, "STUKZT"),
       (7, "STUKZT"),
       (8, "STUKZT");

INSERT INTO TeachingAssistant (userID, activeCourseID)
VALUES ("JaWi1024", "XYJKLM"),
       ("LiAl0924", "STUKZT"),
       ("DaJo1024", "LRUFND"),
       ("ElCl1024", NULL), -- this will probably throw an error, but it's in the spreadsheet
       ("JeGi0924", NULL);