create table STUDENT (
    STUDENT_ID INTEGER PRIMARY KEY,
    NAME string,
    LAST_NAME string,
    CITY INTEGER,
    FOREIGN KEY (CITY) REFERENCES CITY (CITY_ID)

);
-- drop the hole table
drop table STUDENT;

INSERT into STUDENT values
    (1, 'Aisha', 'Lincon',1),
    (2, 'Anya', 'Nielsen', 1),
    (3, 'Benjamin', 'Jensen',2),
    (4, 'Berta','Bertelsen', 3),
    (5, 'Albert', 'Antonsen', 4),
    (6, 'Eske', 'Eriksen', 5),
    (7, 'Olaf', 'Olesen', 6),
    (8, 'Salma', 'Simonsen', 7),
    (9, 'Theis', 'Thomasen', 8),
    (10, 'Janet', 'Jensen', 9);

-- deleting all students from table, without dropping the table
DELETE FROM STUDENT
WHERE STUDENT_ID > 0;

SELECT * FROM STUDENT;

SELECT
    S.STUDENT_ID,
    S.NAME,
    S.LAST_NAME,
    C.NAME as CITY
FROM
    STUDENT S,
    CITY    C
where
        S.CITY=C.CITY_ID
;

------------------------------------
----------- CITY TABLE -------------
------------------------------------

create table CITY(
    CITY_ID integer primary key,
    NAME string
);


INSERT INTO CITY VALUES
(1, 'Nykøbing F'),(2, 'Camas'),(3, 'Billund'),(4, 'Sorø'),(5, 'Eskildstrup'),(6, 'Odense'), (7, 'Stockholm'),(8,'Tølløse'),(9, 'Jyllinge');

-- SPØRG FAR
DELETE FROM CITY
WHERE CITY_ID > 0;

SELECT * FROM CITY;

-------------------------------------
----------- TEACHER TABLE -----------
-------------------------------------

CREATE TABLE TEACHER(
    TEACHER_ID INTEGER PRIMARY KEY,
    NAME string
);

INSERT INTO TEACHER VALUES
(1, 'Line'),(2, 'Ebbe');

------- example on update --------
-- update TEACHER set NAME = 'Per'
-- where TEACHER_ID = 2;

SELECT * FROM TEACHER;

-------------------------------------
------------ COURSE TABLE -----------
-------------------------------------

CREATE TABLE COURSE(
  COURSE_ID INTEGER PRIMARY KEY,
  NAME string,
  DESCRIPTION string
);

INSERT INTO COURSE VALUES
(1, 'SD', 'Software Development'),(2, 'EC', 'Essential Computing');

SELECT * FROM COURSE;

DELETE FROM COURSE
WHERE COURSE_ID > 0;

-------------------------------------
-------- SEMESTER TABLE -------------
-------------------------------------

CREATE TABLE SEMESTER(
    SEMESTER_ID INTEGER PRIMARY KEY,
    YEAR string,
    SEASON string
);

DELETE FROM SEMESTER
WHERE SEMESTER_ID > 0;

INSERT INTO SEMESTER VALUES
(1, '2019','Autumn'),(2, '2020', 'Spring'),(3, '2020', 'Autumn');

SELECT * FROM SEMESTER;

-- Concat YEAR and SEASON into one column
SELECT
       YEAR || '-' || SEASON AS SEMESTER
FROM SEMESTER;



-----------------------------------
---------- GRADE ------------------
-----------------------------------

CREATE TABLE GRADE_SCALE(
  GRADE_SCALE_ID INTEGER PRIMARY KEY,
  GRADE string,
  DESCRIPTION string
);

INSERT INTO GRADE_SCALE VALUES
(1, '-3', 'Unacceptable Performance'),(2, '00', 'Inadequate Performance'),(3,'02','Aduquate Performance'),(4,'4','Fair Performance'),
(5, '7', 'Good Performance'),(6,'10','Very Good Performance'),(7, '12','Excellent Performance');


SELECT * FROM GRADE_SCALE;

---------------------------------
---- COURCE_CALENDER TABLE ------
---------------------------------

CREATE TABLE COURSE_CALENDER(
    COURCE_CALENDER_ID INTEGER PRIMARY KEY,
    SEMESTER_ID INTEGER,
    TEACHER_ID INTEGER,
    COURCE_ID INTEGER,
    FOREIGN KEY (SEMESTER_ID) REFERENCES SEMESTER (SEMESTER_ID),
    FOREIGN KEY (TEACHER_ID) REFERENCES TEACHER (TEACHER_ID),
    FOREIGN KEY (COURCE_ID) REFERENCES COURSE (COURSE_ID)
);

INSERT INTO COURSE_CALENDER VALUES
-- ID's from COURCE_CALENDER, SEMESTER, COURSE and TEACHER tables.
(1, 1, 1, 1),(2, 1, 2, 2),(3, 2, 1, 1);

SELECT * FROM COURSE_CALENDER;

SELECT
    CC.COURCE_CALENDER_ID,
    S.YEAR,
    S.SEASON,
    T.NAME AS TEACHER_NAME,
    C.NAME AS COURSE_NAME

FROM
    COURSE_CALENDER CC
    JOIN SEMESTER S ON S.SEMESTER_ID = CC.SEMESTER_ID
    JOIN TEACHER T ON T.TEACHER_ID = CC.TEACHER_ID
    JOIN COURSE C ON C.COURSE_ID = CC.COURCE_ID
;

-------------------------------
----- STUDENT_CALENDER --------
-------------------------------

CREATE TABLE STUDENT_CALENDER(
  STUDENT_CALENDER_ID INTEGER PRIMARY KEY,
  STUDENT_ID INTEGER,
  COURCE_CALENDER_ID INTEGER,
  GRADE_SCALE_ID INTEGER,
  FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT (STUDENT_ID),
  FOREIGN KEY (COURCE_CALENDER_ID) REFERENCES COURSE_CALENDER (COURCE_CALENDER_ID),
  FOREIGN KEY (GRADE_SCALE_ID) REFERENCES GRADE_SCALE (GRADE_SCALE_ID)

);

INSERT INTO STUDENT_CALENDER VALUES
(1,1,1,7),(2,1,2,6), -- Aisha

(3,2,3,null),(4,2,2,7), -- Anya
(5,3,1,5),(6,3,2,6), -- Benjamin
(7,4,3,null),(8,4,2,3), -- Berta
(9,5,1,6),(10,5,2,5), -- Albert
(11,6,3,null),(12,6,2,6), -- Eske
(13,7,1,4),(14,7,2,7), -- Olaf
(15,8,3,null),(16,8,2,7), -- Salma
(17,9,1,7),(18,9,2,7), -- Theis
(19,10,3,null),(20,10,2,5) -- Janet
;

SELECT * FROM STUDENT_CALENDER;

SELECT
    SC.STUDENT_CALENDER_ID,
    STU.NAME,
    STU.LAST_NAME,
    C.NAME,
    GS.GRADE,
    SEM.YEAR,
    SEM.SEASON,
    T.NAME


FROM
    STUDENT_CALENDER SC
    JOIN STUDENT STU ON STU.STUDENT_ID = SC.STUDENT_ID
    JOIN COURSE_CALENDER CC ON CC.COURCE_CALENDER_ID = SC.COURCE_CALENDER_ID
    JOIN COURSE C ON CC.COURCE_ID = C.COURSE_ID
    LEFT JOIN GRADE_SCALE GS ON GS.GRADE_ID = SC.GRADE_SCALE_ID
    JOIN SEMESTER SEM ON CC.SEMESTER_ID = SEM.SEMESTER_ID
    JOIN TEACHER T ON CC.TEACHER_ID = T.TEACHER_ID

-------- To print out all students with NULL
-- WHERE
--    SC.GRADE_SCALE_ID IS NULL
;











