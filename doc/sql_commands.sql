#Queries
#-------

#Need to retrieve:

#Get all campuses:

SELECT
    name
FROM 
    campus;


#current semester and next semester (optional) and current year and next year (if needed and optional)
SELECT
    year, 
    semester
FROM 
    semesters
WHERE
    year = {cur_year} AND
    semester = {cur_semester} OR
    year = {cur_year + 1} AND
    semester = {cur_semester + 1};


/*
    Rooms that are available with classes in them
    FOR THE WEBSITE, the date should automatically pick the semester
    With semesters date range clearly defined. Also, limit the calendar to only show up
    to a certain date.
*/
/* NOT USED BECAUSE IT DOESNT WORK
SELECT 
    r.name,
    st.time,
    {duration},
    oc.num_people
FROM
    offerings AS o INNER JOIN semesters AS s
    ON o.semesterId = s.semesterId 
    INNER JOIN dates AS sd
    ON o.start_date = sd.dateId
    INNER JOIN dates AS ed
    ON o.end_date = ed.dateId
    INNER JOIN times AS st 
    ON o.start_time = st.timeId
    INNER JOIN times AS et
    ON o.end_time = et.timeId
    INNER JOIN rooms AS r
    ON o.roomId = r.roomId
    INNER JOIN campus AS c
    ON r.campusId = c.campusId 
    LEFT JOIN occupied AS oc
    ON r.roomId = oc.roomId
WHERE
    o.day = {given_date} AND
    s.year = {given_year} AND 
    c.name = {given_campus} AND
    s.semester = {given_semester} AND
    (
        o.week_alt IS NULL OR
        o.week_alt = {given_week_alt}) AND
    (
        sd.date >= {given_date} OR
        ed.date <= {given_date}) AND
    (
        et.time >= {req_start_time} OR
        st.time <= {req_end_time})
*/

/*
    query to test to see if there is a slot available
    NOT USED
*/
SELECT
    * 
FROM
    offerings AS o,
    rooms AS r,
    times AS t1,
    times AS t2
WHERE
    o.roomId = r.roomId AND
    o.start_time = t1.timeId AND
    o.end_time = t2.timeId AND
    day = 'W' AND
    r.name = "UA3120"
ORDER BY
    t1.time;

/*NOT USED
SELECT 
    r.name,
    st.time,
    2,
    oc.num_people
FROM
    offerings AS o INNER JOIN semesters AS s
    ON o.semesterId = s.semesterId 
    INNER JOIN dates AS sd
    ON o.start_date = sd.dateId
    INNER JOIN dates AS ed
    ON o.end_date = ed.dateId
    INNER JOIN times AS st 
    ON o.start_time = st.timeId
    INNER JOIN times AS et
    ON o.end_time = et.timeId
    INNER JOIN rooms AS r
    ON o.roomId = r.roomId
    INNER JOIN campus AS c
    ON r.campusId = c.campusId 
    LEFT JOIN occupied AS oc
    ON r.roomId = oc.roomId
WHERE
    o.day =  'W' AND
    s.year = '2012' AND 
    c.name LIKE'North Oshawa Campus' AND
    s.semester LIKE "Fall" AND
    (
        o.week_alt IS NULL OR
        o.week_alt = 1) AND
    (
        sd.date >= '2012:21:11' OR
        ed.date <= '2012:21:11') AND
    (
        et.time >= '14:00:00' OR
        st.time <= '16:00:00');
*/

/*
    Get the room, start time, end time, start date, end date and the total number of
    people occupying the room 
    This will retrieve every room on a given day, given semester, given year, given week
    alt.
*/
SELECT 
    r.name,
    st.time AS start_time,
    et.time AS end_time,
    sd.date AS start_date,
    ed.date AS end_date
FROM 
    offerings AS o INNER JOIN semesters AS s
    ON o.semesterId = s.semesterId 
    INNER JOIN dates AS sd
    ON o.start_date = sd.dateId
    INNER JOIN dates AS ed
    ON o.end_date = ed.dateId
    INNER JOIN times AS st 
    ON o.start_time = st.timeId
    INNER JOIN times AS et
    ON o.end_time = et.timeId
    INNER JOIN rooms AS r
    ON o.roomId = r.roomId
    INNER JOIN campus AS c
    ON r.campusId = c.campusId 
WHERE
    o.day LIKE {given_date} AND
    s.year = {given_year} AND 
    c.name LIKE {given_campus} AND
    s.semester LIKE {given_semester} AND
    (
        o.week_alt IS NULL OR
        o.week_alt = {given_week_alt})
ORDER BY 
    r.name;

/* A workable example of the above query
SELECT 
    r.name,
    st.time AS start_time,
    et.time AS end_time,
    sd.date AS start_date,
    ed.date AS end_date,
    oc.num_people
FROM 
    offerings AS o INNER JOIN semesters AS s
    ON o.semesterId = s.semesterId 
    INNER JOIN dates AS sd
    ON o.start_date = sd.dateId
    INNER JOIN dates AS ed
    ON o.end_date = ed.dateId
    INNER JOIN times AS st 
    ON o.start_time = st.timeId
    INNER JOIN times AS et
    ON o.end_time = et.timeId
    LEFT JOIN rooms AS r
    ON o.roomId = r.roomId
    INNER JOIN campus AS c
    ON r.campusId = c.campusId 
    LEFT JOIN occupied AS oc
    ON r.roomId = oc.roomId
WHERE
    o.day LIKE 'W' AND
    s.year = '2012' AND 
    c.name LIKE'North Oshawa Campus' AND
    s.semester LIKE "Fall" AND
    (
        o.week_alt IS NULL OR
        o.week_alt = 1)
ORDER BY 
    r.name;
*/

/*
    All rooms on a certain campus
*/
SELECT
    r.name
FROM
    rooms AS r INNER JOIN
    campus AS c ON
    r.campusId = c.campusId
WHERE
    c.name LIKE {given_campus}
ORDER BY 
    r.name, c.name;

/*
    Get all of the rooms, start_time, end_time, date, num_people, and total num_people
    given a user name.
*/
SELECT
    r.name,
    c.name AS campus,
    st.time AS start_time,
    et.time AS end_time,
    oc.date,
    rr.num_people AS num_people
    oc.num_people AS total_num_people
FROM
    users AS u INNER JOIN
    room_requests AS rr ON
    u.userId = rr.userId
    INNER JOIN occupied AS oc
    ON rr.occupyId = oc.occupyId
    INNER JOIN times AS st
    ON oc.start_time = st.timeId
    INNER JOIN times AS et
    ON oc.end_time = et.timeId
    INNER JOIN rooms AS r 
    ON oc.roomId = r.roomId
    INNER JOIN campus AS c
    ON r.campusId = c.campusId
WHERE
    u.username LIKE {given_username}
ORDER BY
    oc.date;


/*
    gets the user name given the user name
*/
SELECT
    username
FROM
    users
WHERE
    username LIKE {given_username}

/*
    Use for logging
*/
SELECT
    AES_DECRYPT(password, ?) 
FROM
    users
WHERE 
    username LIKE ?

/*
insert room request
*/

/*
1. cur total num of people
2. every entry of occupied given current room, current day, current time, total num people in that room
wants array
3. Busiest rooms/least available rooms (like 5 entries)
4. Most requested rooms limited to 5
5. Total capacity growth over years plotted with Total registered growth over years
6. Most popular building based on requests
7. Growth of faculties (reg and cap) per faculty
8. Prof with greatest # of students
*/


/*
2. every entry of occupied given current room, current day, current time, total num people in that room
wants array

Has to have that stupid had to handle thursday == R
*/
SELECT
    r.name AS room_number,
    SUM(oc.num_people) AS total_num_people
FROM
    occupied AS oc
    INNER JOIN times AS st
    ON oc.start_time = st.timeId
    INNER JOIN times AS et
    ON oc.end_time = et.timeId
    INNER JOIN rooms AS r 
    ON oc.roomId = r.roomId
WHERE
    r.name LIKE {given_room} AND
    st.time = {given_time} AND
    et.time = {given_time} AND
    (
        (DAYNAME(oc.date) LIKE {given_day} + '%' AND
        DAYNAME(oc.date) NOT LIKE 'Thu%') OR
        DAYNAME(oc.date) NOT LIKE {given_day} + '%' AND
        DAYNAME(oc.date) NOT LIKE 'Thu%'
GROUP BY
    r.name));


/*
3. Busiest rooms/least available rooms (like 5 entries)
Finds the sum of the total num of people occupying a room each day
*/
/* Arent 3 and 4 the same?
4. Most requested rooms limited to 5
*/
SELECT
    r.name,
    SUM(oc.num_people) AS total_num_people
FROM
    occupied AS oc
    INNER JOIN rooms AS r 
    ON oc.roomId = r.roomId
GROUP BY
    r.name
ORDER BY
    total_num_people DESC;



/*
5. Total capacity growth over years plotted with Total registered growth over years
I cannot take the sum of the reg, and room_cap because if there is a web course it will
return null for cap thefore it must be done programmatically
Will return reg > 0 and cap null if it is a web course or something like that...
*/
SELECT
    SUM(o.registered) AS total_registered,
    s.year,
    s.semester
FROM 
    offerings AS o
    INNER JOIN class_type AS ct
    ON o.typeId = ct.typeId
    INNER JOIN semesters AS s
    ON o.semesterId = s.semesterId
    LEFT JOIN rooms AS r
    ON o.roomId = r.roomId
GROUP BY
    s.year,
    s.semester
WHERE
    ct.acr <> 'LAB' AND
    ct.acr <> 'TUT'


/*
6. Most popular building based on requests SAME AS 3,4
since i cant find a way to separte the rooms per building (would take regex to do it...)
*/
SELECT
    r.name,
    SUM(oc.num_people)
FROM
    occupied AS oc
    INNER JOIN rooms AS r 
    ON oc.roomId = r.roomId
GROUP BY
    r.name

/*SELECT SUBSTRING('UA1311', 1, 2);*/

/*
7. Growth of faculties (reg and cap) per faculty
not much different from 5 since i cant sum reg/cap if i was to do just reg, then i could...
*/
SELECT
    SUM(o.registered) AS total_registered,
    f.faculty,
    s.year,
    s.semester
FROM 
    offerings AS o
    INNER JOIN faculties AS f
    ON o.facultyId = f.facultyId
    INNER JOIN class_type AS ct
    ON o.classId = ct.classId
    INNER JOIN semesters AS s
    ON o.semesterId = s.semesterId
    LEFT JOIN rooms AS r
    ON o.roomId = r.roomId
WHERE
    ct.acr <> 'LAB' AND
    ct.acr <> 'TUT'
GROUP BY   
    f.faculty,
    s.year,
    s.semester
ORDER BY
    total_students DESC

/*
8. Prof with greatest # of students limited to 5
*/
SELECT
    p.name,
    SUM(o.registered) AS total_students
FROM
    offerings AS o 
    INNER JOIN professors AS p
    ON o.profId = p.profId
GROUP BY
    p.name
ORDER BY 
    total_students DESC

/*
9. Prof with the least # of students limited to 5
couldnt resist
*/

SELECT
    p.name
    SUM(o.registered) AS total_students,
FROM
    offerings AS o 
    INNER JOIN professors AS p
    ON o.profId = p.profId
GROUP BY
    p.name
ORDER BY 
    total_students;

/*
10. largest class enrolment
*/
SELECT
    c.name,
    SUM(o.registered) AS total_students,
    s.year,
    s.semester
FROM
    offerings AS o
    INNER JOIN courses AS c
    ON o.courseId = c.courseId
    INNER JOIN class_type AS ct
    ON o.typeId = ct.typeId
    INNER JOIN semesters AS s
    ON o.semesterId = s.semesterId
    LEFT JOIN rooms AS r
    ON o.roomId = r.roomId
WHERE
    ct.acr <> 'LAB' AND
    ct.acr <> 'TUT'
GROUP BY
    c.name,
    s.year,
    s.semester
ORDER BY
    total_students DESC;

/** 
 * A query that could be very inseresting but would be a fair bit of work:
 * Plot the growth of the University by # of rooms available each year on each campus
 * Could also extrapolate what year each building first "opened" (since no rooms from that building would have been free the previous years)

 */