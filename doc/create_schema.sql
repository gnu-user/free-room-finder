/*
 * Script to create all the relation schemas for the free-room-finder database
 */

/*
faculties
-----------
    - facultyId primary key (integer)
    - code        //facility code
    - name        //Name is stored in faculties file in webct folder
*/
CREATE TABLE faculties
(
    facultyId   INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    code        VARCHAR(4) NOT NULL,
    name        VARCHAR(64) NOT NULL,
    PRIMARY KEY(facultyId)
);



/*
times (contains Distinct values)
---------------------------------
    - timeId primary key NOT NULL
    - time TIME(hh:mm:ss) UNIQUE NOT NULL
*/
CREATE TABLE times
(
    timeId  INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    time    TIME UNIQUE NOT NULL,
    PRIMARY KEY(timeId)
);



/*
dates (contains Distinct values)
--------------------------------
    - dateId primary key NOT NULL
    - date DATE UNIQUE NOT NULL
*/
CREATE TABLE dates
(   
    dateId  INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    date    DATE UNIQUE NOT NULL,
    PRIMARY KEY(dateId)
);



/*
semesters table
---------------
    - semesterId primary key NOT NULL
    - year YEAR(4) NOT NULL     (ie. 2012) 
    - semester VARCHAR(32) NOT NULL     (ie. Winter, Spring/Summer, Summer)
*/
CREATE TABLE semesters
(
    semesterId  INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    year        YEAR(4) NOT NULL,
    semester    VARCHAR(32) NOT NULL,
    PRIMARY KEY(semesterId)
);



/*
class_type table
----------------
    - typeId primary key NOT NULL
    - class acr (ie. acronym LEC, TUT)
    - class type (ie. Lecture, Tutorial)
*/
CREATE TABLE class_type
(
    typeId  INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    acr     CHAR(3),
    type    VARCHAR(32),
    PRIMARY KEY(typeId)
);



/*
campus table
------------
    - campusId  primary key NOT NULL
    - acr acr (ie. UON),
    - name (ie. North Oshawa Campus)
*/
CREATE TABLE campus
(
    campusId    INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    acr         CHAR(3),
    name        VARCHAR(64),
    PRIMARY KEY(campusId)
);



/*
users
-----
    - userId primary key NOT NULL
    - username VARCHAR(32) NOT NULL
    - password VARCHAR(64) NOT NULL
*/
CREATE TABLE users
(
    userId      INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    first_name  VARCHAR(32),
    last_name   VARCHAR(32),
    student_id  BLOB,
    email       VARCHAR(64),
    username    VARCHAR(32),
    password    BLOB,
    reg_date    DATE,
    last_access DATE,
    PRIMARY KEY (userId)
);



/*
rooms
-------
    - roomId primary key NOT NULL
    - name VARCHAR(64) NOT NULL
    - campusId INTEGER UNSIGNED NOT NULL
    - room_capacity (INTEGER) NOT NULL
    - room info such as
    - laptop recharge support (Boolean)
    - Ethernet ports (Boolean)
*/
CREATE TABLE rooms
(
    roomId          INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    name            VARCHAR(16) NOT NULL,
    campusId        INTEGER UNSIGNED NOT NULL,
    room_capacity   INTEGER UNSIGNED NOT NULL,
    power_outlet    BOOLEAN,
    ethernet_ports  BOOLEAN,
    PRIMARY KEY(roomId),
    FOREIGN KEY(campusId) REFERENCES campus(campusId)
        ON DELETE CASCADE    ON UPDATE CASCADE
);



/*
occupied
---------
    - occupyId primary key NOT NULL
    - date (including time (from-to), day, month, year)
    - might be worth while to have the date separate of the time
    - room_reference foreign key NOT NULL
    - number_of_people INTEGER NOT NULL
*/
CREATE TABLE occupied
(
    occupyId    INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    roomId      INTEGER UNSIGNED NOT NULL,
    num_people  INTEGER UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY(occupyId),
    FOREIGN KEY(roomId) REFERENCES rooms(roomId)
        ON DELETE CASCADE    ON UPDATE CASCADE
);



/*
MIGHT want to adjust this table to move columns
start_time, end_time, and date to the occupied table, 
Reason:
There will be multiple room_requests entries for the same date and time
therefore each room_request will have a duplicated date and time linking
to the same occupied entry. Also changing this would not effect currently
scraped database since these tables have yet to be filled and really only
have to do with user interation.

room_requests
-------------
    - user_id foreign key NOT NULL
    - occupyId foreign key NOT NULL
    - number_of_people INTEGER NOT NULL
*/
CREATE TABLE room_requests
(
    userId      INTEGER UNSIGNED NOT NULL,
    occupyId    INTEGER UNSIGNED NOT NULL,
    start_time  INTEGER UNSIGNED NOT NULL,
    end_time    INTEGER UNSIGNED NOT NULL,
    date        DATE    NOT NULL,
    num_people  INTEGER UNSIGNED NOT NULL DEFAULT 1,
    FOREIGN KEY(userId) REFERENCES users(userId)
        ON DELETE CASCADE    ON UPDATE CASCADE,
    FOREIGN KEY(occupyId) REFERENCES occupied(occupyId)
        ON DELETE CASCADE    ON UPDATE CASCADE,
    FOREIGN KEY(start_time) REFERENCES times(timeId)
        ON DELETE CASCADE    ON UPDATE CASCADE,
    FOREIGN KEY(end_time) REFERENCES times(timeId)
        ON DELETE CASCADE    ON UPDATE CASCADE
);

/* NOTE, PER Semester (delete entries a few days after the end of each semester) */



/*
professors
-----------
    - profId primary key NOT NULL
    - name VARCHAR(32)
    - facultyId foreign key   prof might not be easily linked
*/
CREATE TABLE professors
(
    profId  INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    name    VARCHAR(32),
    PRIMARY KEY(profId)
);



/*
courses
---------
    - courseId primary key NOT NULL,
    - course_code VARCHAR(4) NOT NULL
    - name VARCHAR(64) NOT NULL
    - facultyId foreign key
*/
CREATE TABLE courses
(
    courseId    INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    name        VARCHAR(64) NOT NULL,
    course_code VARCHAR(4) NOT NULL,
    level       VARCHAR(32) NOT NULL,
    facultyId   INTEGER UNSIGNED,
    PRIMARY KEY(courseId),
    FOREIGN KEY(facultyId) REFERENCES faculties(facultyId)
        ON DELETE SET NULL ON UPDATE CASCADE
);



/*
offerings
-----------
    - courseId foreign key NOT NULL
    - crn         VARCHAR(8)  NOT NULL,
    - section     VARCHAR(3)  NOT NULL,
    - typeId      INTEGER     UNSIGNED    NOT NULL,
    - registered INTEGER NOT NULL
    - day CHAR(1) NOT NULL
    - week_alt BOOLEAN DEFAULT NULL, identifies whether the class alternates
      weekly, default, NULL indicates that the class occurs each week, TRUE,
      indicates it occurs for week 1, FALSE, indicates it occurs for week 2
    - profId foreign key        (IF NULL Prof == TBA/UNKNOWN)
    - roomId foreign key        (COULD BE AN ONLINE COURSE)
    - campusId foreign key INTEGER UNSIGNED
    - start_time  INTEGER     UNSIGNED,
    - end_time INTEGER     UNSIGNED,
    - start_date foreign key NOT NULL
    - end_date foreign key NOT NULL
    - semesterId      INTEGER     UNSIGNED    NOT NULL,
*/
CREATE TABLE offerings
(
    courseId    INTEGER     UNSIGNED    NOT NULL,
    crn         VARCHAR(8)  NOT NULL,
    section     VARCHAR(3)  NOT NULL,
    typeId      INTEGER     UNSIGNED    NOT NULL,
    registered  INTEGER     UNSIGNED    NOT NULL,
    day         CHAR(1),
    week_alt    BOOLEAN     DEFAULT NULL,
    profId      INTEGER     UNSIGNED,
    roomId      INTEGER     UNSIGNED,
    start_time  INTEGER     UNSIGNED,
    end_time    INTEGER     UNSIGNED,
    start_date  INTEGER     UNSIGNED,
    end_date    INTEGER     UNSIGNED,
    semesterId  INTEGER     UNSIGNED    NOT NULL,
    FOREIGN KEY(courseId) REFERENCES courses(courseId)
        ON DELETE CASCADE    ON UPDATE CASCADE,
    FOREIGN KEY(typeId) REFERENCES class_type(typeId)
        ON DELETE CASCADE    ON UPDATE CASCADE,
    FOREIGN KEY(profId) REFERENCES professors(profId)
        ON DELETE SET NULL    ON UPDATE CASCADE,
    FOREIGN KEY(roomId) REFERENCES rooms(roomId)    
        ON DELETE SET NULL    ON UPDATE CASCADE,
    FOREIGN KEY(start_time) REFERENCES times(timeId)
        ON DELETE CASCADE    ON UPDATE CASCADE,
    FOREIGN KEY(end_time) REFERENCES times(timeId)
        ON DELETE CASCADE    ON UPDATE CASCADE,
    FOREIGN KEY(start_date) REFERENCES dates(dateId)
        ON DELETE CASCADE    ON UPDATE CASCADE,
    FOREIGN KEY(end_date) REFERENCES dates(dateId)
        ON DELETE CASCADE    ON UPDATE CASCADE,
    FOREIGN KEY(semesterId) REFERENCES semesters(semesterId)
        ON DELETE CASCADE    ON UPDATE CASCADE
);
