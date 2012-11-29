<?php

/**
 * Contains a collection of the functions that directly interact with the database
 * to provide a convenient database abstraction layer, in the future support could
 * be added to support other databases. At the moment the implementations are
 * specific to MySQL (5.1 is the version tested) and prepared statements are
 * used for all queries to provide a layer of protection against SQL injection.
 * 
 * TODO More work perhaps to add OOP features for scoping instead of just an
 * aggregation of many functions, a hierarchy of classes would be nice
 */


/**
 * Thought these should be defined. They can be moved if needed they will however
 * be used throughout this file.
 */
$faculty_table = "faculties";
$time_table = "times";
$date_table = "dates";
$semester_table = "semesters";
$class_type_table = "class_type";
$campus_table = "campus"
$user_table = "users";
$room_table = "rooms";
$occupy_table = "occupied";
$room_request_table = "room_requests";
$professor_table = "professors";
$course_table = "courses";
$offering_table = "offerings";

$current = "current";
$next = "next";
$year = "year";
$semester = "semesters";

//require_once 'election.php';

/**
 * Get all of the campuses where rooms are available.
 *
 * @param mysqli $mysqli_free_room The mysqli connection object for the ucsc elections DB
 *
 * @return array $campuses An array containing all of the campuses available
 */
function get_all_campus($mysqli_free_room)
{

    $campuses = array();
    /* Get the campuses */
    if ($stmt = $mysqli_elections->prepare("SELECT name
                                                FROM " . $campus_table ))
    {
        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($campus);

        $stmt->fetch();

        while ($stmt->fetch())
        {
            $campuses[] = $campus;
        }

        /* close statement */
        $stmt->close();
    }
    return $campuses;
}

/**
 * Get the current term and the next term (in year, semester)
 *
 * @param mysqli $mysqli_free_room The mysqli connection object for the ucsc elections DB
 *
 * @return $term An array containing the current year and semester as well as the next year and semester
 */
function get_year($mysqli_free_room)
{

    /*
     * Need to calculate the current semester based on the date
     * Sept - Dec == Fall
     * Jan - April == Winter
     * May - June == Spring
     * July - August == Summer
     */
    $semesters = array();
    $term = array( $current => array($year => 0, $semester => 0), 
                   $next    => array($year => 0, $semester => 0));

    /* The current election year */
    $year = date('Y');

    /* The next year */
    $new_year = $year;
    /*if(cur_semester == fall)
    {
        $new_year = $year+1;
    }
    */

    /* Get the candidate for the current position from the database */
    if ($stmt = $mysqli_elections->prepare("SELECT year, semester
                                                FROM " . $semester_table . 
                                                "WHERE (year = ? AND 
                                                semester LIKE ? ) OR
                                                (year = ? AND 
                                                semester LIKE ?
                                                ORDER BY
                                                year)" ))
    {
        /* bind parameters for markers */
        $stmt->bind_param('ssss', $year, $semesters[0], new_year, $semesters[1]);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($term);

        $stmt->fetch();

        //TODO make so that it assigns it to the write one...
        $terms[$current][$year] = $term[0];
        $terms[$current][$semeter] = $term[1];

        $stmt->fetch();
        $terms[$next][$year] = $term[0];
        $terms[$next][$semeter] = $term[1];

        /* close statement */
        $stmt->close();
    }

    /*
     * Return a 2D array contain:
     * { 
     *   { cur_year, cur_semester}, 
     *   {year, next_semester}
     * }
     */
    return $terms;
}

/**
 * Get all rooms that are taken on a certain day. 
 * 
 * This can be used to get the slots available on the day as well as if a certain time slot is 
 * available.
 *
 * @param mysqli $mysqli_free_room The mysqli connection object for the ucsc elections DB
 * @param $day is the week day desired, the first letter of the week day name, with Thursday = 'R'
 * @param $term the term desired **NOTE term = { year, semester}
 * @param $campus the campus desired, the full campus name
 * 
 * @return $rooms An array containing the all the rooms that are taken from a start time to an
 * end time, start date to end date with the number of people taking the room given the day,
 * term and the campus.
 */
function get_rooms_taken($mysqli_free_room, $day, $term, $campus)
{

/*
     * Need to make a function that calcs the current week offset based on the date
     * first week of the semester is week 1 and so on...
     */
    //temporarly set to 1 as default
    $week_alt = 1;
    $rooms = array( array("room"       => ""
                          "starttime"  => ""
                          "endtime"    => ""
                          "startdate"  => ""
                          "enddate"    => ""
                          "num_people" => ""));
    

    /* Get the candidate for the current position from the database */
    if ($stmt = $mysqli_elections->prepare("SELECT r.name,
                                                st.time AS start_time,
                                                et.time AS end_time,
                                                sd.date AS start_date,
                                                ed.date AS end_date,
                                                oc.num_people
                                                FROM " . $offering_table . " AS o
                                                INNER JOIN " . $semester_table . " AS s
                                                ON o.semesterId = s.semesterId 
                                                INNER JOIN " . $date_table . " AS sd 
                                                ON o.start_date = sd.dateId
                                                INNER JOIN " . $date_table . " AS ed
                                                ON ON o.end_date = ed.dateId
                                                INNER JOIN " . $time_table . " AS st
                                                ON o.start_time = st.timeId
                                                INNER JOIN " . $time_table . " AS et
                                                ON o.end_time = et.timeId
                                                INNER JOIN " . $room_table . " AS r 
                                                ON o.roomId = r.roomId
                                                INNER JOIN " . $campus_table . " AS c 
                                                ON r.campusId = c.campusId 
                                                LEFT JOIN " . $occupy_table . " AS oc
                                                ON r.roomId = oc.roomId
                                                WHERE 
                                                o.day LIKE ? AND
                                                s.year = ? AND 
                                                c.name LIKE ? AND
                                                s.semester LIKE ? AND
                                                ( 
                                                    o.week_alt IS NULL OR
                                                    o.week_alt = ?
                                                ) 
                                                ORDER BY r.name" ))
    {
        /* bind parameters for markers */
        $stmt->bind_param('ssss', $day, $term[0], $campus, $term[1]);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($room);

        while ($stmt->fetch())
        {
            //TODO verify that this works
            $rooms[] = $room;
        }

        /* close statement */
        $stmt->close();
    }

    /*
     * Return a 2D array contain:
     * { 
     *	 {room_name,
     *    start_time,
     *    end_time,
     *    start_date,
     *    end_date,
     *    num_people}
     * }
     */
    return $rooms;
}

/**
 * Get all of rooms on a given campus
 *
 * @param mysqli $mysqli_free_room The mysqli connection object for the ucsc elections DB
 * @param $campus the campus desired, the full campus name
 *
 * @return $rooms all of the rooms given a campus
 */
function get_rooms($mysqli_free_room, $campus)
{

    $week_alt = 1;
    $rooms = array();
    
    /* Retrieve all of the rooms on the given campus */
    if ($stmt = $mysqli_elections->prepare("SELECT r.name,
                                                FROM " . $room_table . " AS r 
                                                ON o.roomId = r.roomId
                                                INNER JOIN " . $campus_table . " AS c 
                                                ON r.campusId = c.campusId 
                                                WHERE 
                                                c.name LIKE ?
                                                ORDER BY r.name, c.name" ))
    {
        /* bind parameters for markers */
        $stmt->bind_param('s', $campus);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($room);


        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $rooms[] = $room;
        }

        /* close statement */
        $stmt->close();
    }

    /*
     * Return a 2D array contain:
     * { 
     *	 {room_name,
     *    start_time,
     *    end_time,
     *    start_date,
     *    end_date,
     *    num_people}
     * }
     */
    return $rooms;
}

/**
 * Get all of the rooms the user has requested
 *
 * @param mysqli $mysqli_free_room The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user currently logged in.
 * 
 * @return $rooms The room, campus, start time, end time, date, number of people booked,
 * total number of people the expected in a room that the given user has booked
 */
function get_users_rooms($mysqli_free_room, $username)
{

    $rooms = array( array("room"         => ""
                          "campus"       => ""
                          "starttime"    => ""
                          "endtime"      => ""
                          "date"         => ""
                          "num_people"   => ""
                          "total_people" => ""));
    
    /* Get the candidate for the current position from the database */
    if ($stmt = $mysqli_elections->prepare("SELECT r.name AS room_name,
                                                c.name AS campus, 
                                                st.time AS start_time,
                                                et.time AS end_time,
                                                oc.date AS date, 
                                                rr.num_people AS num_people,
                                                oc.num_people AS total_num_people
                                                FROM " . $users . " AS u
                                                INNER JOIN " . $room_request_table . " AS rr
                                                ON u.userId = rr.userId
                                                INNER JOIN " . $occupy_table . " AS oc 
                                                ON rr.occupyId = oc.occupyId
                                                INNER JOIN " . $time_table . " AS st
                                                ON oc.start_time = st.timeId
                                                INNER JOIN " . $time_table . " AS et
                                                ON oc.end_time = et.timeId
                                                INNER JOIN " . $room_table . " AS r 
                                                ON oc.roomId = r.roomId
                                                INNER JOIN " . $campus_table . " AS c 
                                                ON r.campusId = c.campusId
                                                WHERE 
                                                u.username LIKE username
                                                ORDER BY oc.date" ))
    {
        /* bind parameters for markers */
        $stmt->bind_param('s', $username);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($room);

        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $rooms[] = $room;
        }

        /* close statement */
        $stmt->close();
    }

   /*
    * Return a 2D array contain:
    * { 
    *   { room_name,
    *     campus, 
    *     start_time,
    *     end_time,
    *     date, 
    *     num_people,
    *     total_num_people}
    * }
    */
    return $rooms;
}

/**
 * Get all of the rooms the user has requested
 *
 * @param mysqli $mysqli_free_room The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user currently logged in.
 * 
 * @return $rooms The room and total number of people expected for that room given the
 * room, start time, end time and the date.
 */
function get_total_occupied($mysqli_free_room, $room, $start_time, $end_time, $day)
{
    $rooms = = array( array("room"         => ""
                            "total_people" => ""));
    
    /* Get the total occupied in a room given a start and end time and the day from the database */
    if ($stmt = $mysqli_elections->prepare("SELECT r.name AS room_name,
                                                SUM(oc.num_people) AS total_num_people
                                                FROM " . $occupy_table . " AS oc
                                                INNER JOIN " . $time_table . " AS st
                                                ON oc.start_time = st.timeId
                                                INNER JOIN " . $time_table . " AS et 
                                                ON oc.end_time = et.timeId
                                                INNER JOIN " . $room_table . " AS r 
                                                ON oc.roomId = r.roomIdd
                                                WHERE 
                                                r.name LIKE ? AND
                                                st.time = ? AND
                                                et.time = ? AND
                                                (
                                                    (DAYNAME(oc.date) LIKE ? + '%' AND
                                                    DAYNAME(oc.date) NOT LIKE 'Thu%') OR
                                                    DAYNAME(oc.date) NOT LIKE ? + '%' AND
                                                    DAYNAME(oc.date) NOT LIKE 'Thu%'))
                                                GROUP BY
                                                r.name" ))
    {
        /* bind parameters for markers */
        $stmt->bind_param('sssss', $room, $start_time, $end_time, $day, $day);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($room);

        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $rooms[] = $room;
        }

        /* close statement */
        $stmt->close();
    }

   /*
    * Return a 2D array contain:
    * { 
    *   { room_name,
    *     total_num_people}
    * }
    */
    return $rooms;
}

/**
 * Get all of the rooms the user has requested
 *
 * @param mysqli $mysqli_free_room The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user currently logged in.
 * 
 * @return $rooms The room and total number of people expected for that room for all rooms
 */
function get_total_occupied($mysqli_free_room)
{
    $rooms = = array( array("room"         => ""
                            "total_people" => ""));
    
    /* Get the total occupied in a a room from the database */
    if ($stmt = $mysqli_elections->prepare("SELECT r.name AS room_name,
                                                SUM(oc.num_people) AS total_num_people
                                                FROM " . $occupy_table . " AS oc
                                                INNER JOIN " . $room_table . " AS r 
                                                ON oc.roomId = r.roomIdd
                                                GROUP BY 
                                                r.name
                                                ORDER BY
                                                total_num_people DESC" ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($room);

        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $rooms[] = $room;
        }

        /* close statement */
        $stmt->close();
    }

   /*
    * Return a 2D array contain:
    * { 
    *   { room_name,
    *     total_num_people}
    * }
    */
    return $rooms;
}

/**
 * Get all of the rooms the user has requested
 *
 * @param mysqli $mysqli_free_room The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user currently logged in.
 * 
 * @return $rooms The total registered in courses per semester, per year.
 * Note that in order to avoid double counting registered people per course Tutorials and
 * Labs were not included in the summation.
 */
function get_total_registered($mysqli_free_room)
{
    $rooms = array( array("registered"   => ""
                          "year"         => ""
                          "semester"     => ""));
    
    /* Get the total registered per semester per year from the database */
    if ($stmt = $mysqli_elections->prepare("SELECT SUM(o.registered) AS total_registered,
                                                s.year,
                                                s.semester
                                                FROM " . $offering_table . " AS o
                                                INNER JOIN " . $class_type_table . " AS ct
                                                ON o.typeId = ct.typeId
                                                INNER JOIN " . $semester_table . " AS s 
                                                ON o.semesterId = s.semesterId
                                                LEFT JOIN " . $room_table . " AS r
                                                ON o.roomId = r.roomId
                                                GROUP BY
                                                s.year,
                                                s.semester
                                                WHERE
                                                ct.acr <> 'LAB' AND
                                                ct.acr <> 'TUT'" ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($room);

        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $rooms[] = $room;
        }

        /* close statement */
        $stmt->close();
    }

   /*
    * Return a 2D array contain:
    * { 
    *   { total_registered,
    *     year,
    *     semester}
    * }
    */
    return $rooms;
}

/**
 * Get the total registered for each faculty over the years and semesters
 *
 * @param mysqli $mysqli_free_room The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user currently logged in.
 * 
 * @return $reg The total registered per faculty, per semester, per year. 
 * Note that in order to avoid double counting registered people per course Tutorials and
 * Labs were not included in the summation. 
 */
function get_total_reg_fac($mysqli_free_room)
{
    $reg = array( array("registered"   => ""
                        "faculty"      => ""
                        "year"         => ""
                        "semester"     => ""));
    
    /* Get the total registered per faculty per semester per year from the database */
    if ($stmt = $mysqli_elections->prepare("SELECT SUM(o.registered) AS total_registered,
                                                f.faculty,
                                                s.year,
                                                s.semester
                                                FROM " . $offering_table . " AS o
                                                INNER JOIN " . $faculty_table . " AS f
                                                ON o.facultyId = f.facultyId
                                                INNER JOIN " . $class_type_table . " AS ct
                                                ON o.typeId = ct.typeId
                                                INNER JOIN " . $semester_table . " AS s 
                                                ON o.semesterId = s.semesterId
                                                LEFT JOIN " . $room_table . " AS r
                                                ON o.roomId = r.roomId
                                                WHERE
                                                ct.acr <> 'LAB' AND
                                                ct.acr <> 'TUT'
                                                GROUP BY
                                                f.faculty,
                                                s.year,
                                                s.semester
                                                ORDER BY
                                                total_students DESC" ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($register);

        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $reg[] = $register;
        }

        /* close statement */
        $stmt->close();
    }

   /*
    * Return a 2D array contain:
    * { 
    *   { total_registered,
    *     year,
    *     semester}
    * }
    */
    return $reg;
}

/**
 * Busiest Professors
 *
 * @param mysqli $mysqli_free_room The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user currently logged in.
 * 
 * @return $prof The professors and number of students registered in courses taught by the
 * professor order by the number of students so that the first element will contain the 
 * professor who is "the busiest"
 */
function get_busy_prof($mysqli_free_room)
{
    $prof = array( array("professor"   => ""
                         "student_num" => ""));
    
    /* Get the total occupied in a a room from the database */
    if ($stmt = $mysqli_elections->prepare("SELECT p.name,
                                                SUM(o.registered) AS total_students
                                                FROM " . $offering_table . " AS o
                                                INNER JOIN " . $professor_table . " AS o
                                                ON o.profId = p.profId
                                                GROUP BY
                                                p.name
                                                ORDER BY 
                                                total_students DESC" ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($professor);

        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $prof[] = $professor;
        }

        /* close statement */
        $stmt->close();
    }

   /*
    * Return a 2D array contain:
    * { 
    *   { total_registered,
    *     year,
    *     semester}
    * }
    */
    return $prof;
}