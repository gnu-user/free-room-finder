#Queries
#-------

#Need to retrieve:

#Get all campuses:

SELECT
    arc,
    name
FROM 
    campuses;


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

SELECT
    *x
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

SELECT 
    r.name,
    st.time,
    et.time,
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
    o.day LIKE {given_date} AND
    s.year = {given_year} AND 
    c.name LIKE {given_campus} AND
    s.semester LIKE {given_semester} AND
    (
        o.week_alt IS NULL OR
        o.week_alt = {given_week_alt}) AND
    (
        sd.date >= {given_date} OR
        ed.date <= {given_date})
ORDER BY 
    r.name;

SELECT 
    r.name,
    st.time AS start_time,
    et.time AS end_time,
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
        o.week_alt = 1) AND
    (
        sd.date >= '2012:21:11' OR
        ed.date <= '2012:21:11')
ORDER BY 
    r.name;

/*
TODO FIX FIND the room that is name QSH4 and QSH5, there is a bug with ENGBNNN (it returns NGBNNN)
    same query except it is a right join 
*/
SELECT
    r.name
FROM
    rooms AS r INNER JOIN
    campus AS c ON
    r.campusId = c.campusId
WHERE
    c.name LIKE'North Oshawa Campus'
ORDER BY 
    r.name;
/*
 Need to check if given a room that slot is available
*/