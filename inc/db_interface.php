<?php
/*
 *  Free Room Finder Website
 *
 *
 *  Authors -- Crow's Foot Group
 *  -------------------------------------------------------
 *
 *  Jonathan Gillett
 *  Joseph Heron
 *  Amit Jain
 *  Wesley Unwin
 *  Anthony Jihn
 *
 *
 *  License
 *  -------------------------------------------------------
 *
 *  Copyright (C) 2012 Crow's Foot Group
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Contains a collection of the functions that directly interact with the database
 * to provide a convenient database abstraction layer, in the future support could
 * be added to support other databases. At the moment the implementations are
 * specific to MySQL (5.1 is the version tested) and prepared statements are
 * used for all queries to provide a layer of protection against SQL injection.
 * 
 * TODO More work perhaps to add OOP features for scoping instead of just an
 * aggregation of many functions, a hierarchy of classes would be nice
 */


/*
 * Thought these should be defined. They can be moved if needed they will however
 * be used throughout this file.
 */
$faculty_table = "faculties";
$time_table = "times";
$date_table = "dates";
$semester_table = "semesters";
$class_type_table = "class_type";
$campus_table = "campus";
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


/**
 * A function which returns the access account number and full name of a user who
 * exists in the users table.
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the database
 * @param string $username The username of the user
 */
function get_user($mysqli_conn, $username)
{
	global $user_table;
	
	$user = array(	'access_account' => 0,
					'first_name' => '',
					'last_name' => '');

	$user_match = '';
	
	/* Get the users information from the database if it exists */
	if ($stmt = $mysqli_conn->prepare(	"SELECT username
                                        	FROM ".$user_table.
										" WHERE username LIKE ?"))
	{
		/* bind parameters for markers */
		$stmt->bind_param('s', $username);

		/* execute query */
		$stmt->execute();

		/* bind result variables */
		$stmt->bind_result($user_match);

		/* fetch value */
		$stmt->fetch();

		/* close statement */
		$stmt->close();
	}

	/* If username found, get primary key (access account), and name of the user */
	if (strcasecmp($username, $user_match) === 0)
	{
		if ($stmt = $mysqli_conn->prepare(	"SELECT userId, first_name, last_name
                                            	FROM ".$user_table.
											" WHERE username LIKE ?"))
		{
			/* bind parameters for markers */
			$stmt->bind_param('s', $username);

			/* execute query */
			$stmt->execute();

			/* bind result variables */
			$stmt->bind_result($user['access_account'], $user['first_name'], $user['last_name']);

			/* fetch value */
			$stmt->fetch();

			/* close statement */
			$stmt->close();
		}
	}

	return $user;
}


/**
 * Get all of the campuses where rooms are available.
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 *
 * @return array $campuses An array containing all of the campuses available
 */
function get_all_campus($mysqli_conn)
{
    global $campus_table;
    
    $campuses = array();
    /* Get the campuses */
    if ($stmt = $mysqli_conn->prepare("SELECT name
                                       FROM " . $campus_table))
    {
        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($campus);

        while ($stmt->fetch())
        {
            $campuses[] = $campus;
        }

        /* close statement */
        $stmt->close();
    }
    return $campuses;
}

/** TODO fix to work
 * Get the current term and the next term (in year, semester)
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 *
 * @return $term An array containing the current year and semester as well as the next year and semester
 */
function get_year($mysqli_conn)
{
    global $semester_table, $current, $next, $year, $semester;

    /*
     * Need to calculate the current semester based on the date
     * Sept - Dec == Fall
     * Jan - April == Winter
     * May - June == Spring
     * July - August == Summer
     */
    $semesters = array();
    $term = array(	$current => array($year => 0, $semester => 0), 
					          $next    => array($year => 0, $semester => 0));

    /* The current election year */
    $cur_year = date('Y');

    /* The next year */
    $new_year = $year;
    /*if(cur_semester == fall)
    {
        $new_year = $year+1;
    }
    */

    /* Get the candidate for the current position from the database */
    if ($stmt = $mysqli_conn->prepare("SELECT year, semester
                                                FROM " . $semester_table . 
                                                " WHERE (year = ? AND 
                                                semester LIKE ? ) OR
                                                (year = ? AND 
                                                semester LIKE ?
                                                ORDER BY
                                                year)" ))
    {
        /* bind parameters for markers */
        $stmt->bind_param('ssss', $cur_year, $semesters[0], $new_year, $semesters[1]);

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

/** TODO fix
 * Get the rooms that are open given the duration, day, term and campus
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $duration the length of time the user wishes to find free
 * @param $day is the week day desired, the first letter of the week day name, with Thursday = 'R'
 * @param $term the term desired **NOTE term = { year, semester}
 * @param $campus the campus desired, the full campus name
 *
 * @return $available the room, start, and end time that are available given the day,
 * term and campus.
 */
function get_room_open_dur($mysqli_conn, $duration, $day, $term, $campus)
{
    global $room_table;
    $rooms = get_rooms_taken($mysqli_conn, $day, $term, $campus);
    $first_class = array("room"       => $rooms[0]["room"],
                         "starttime"  => "",
                         "endtime"    => "08:00:00",
                         "startdate"  => "",
                         "enddate"    => "");
    $last_class = array("room"       => "",
                         "starttime"  => "23:00:00",
                         "endtime"    => "",
                         "startdate"  => "",
                         "enddate"    => "");
    $available = array( array("room"       => "",
                              "starttime"  => "",
                              "endtime"    => ""));

    array_unshift($rooms, $first_class);
    //array_push($rooms, $last_class);

    $free = TRUE;
    $prev_room = $rooms[0]["room"];
    $index = 0;
    $num_rooms = sizeof($rooms);

    for($i = 0; $i < $num_rooms-1; $i++)
    {
      if($rooms[$i]["endtime"] != "")
      {
        $i++;
        $prev_room = $rooms[$i+1]["room"];
      }
    	if($rooms[$i+1]["room"] != $prev_room)
      {
          $first_class["room"] = $prev_room;
          $last_class["room"] = $room[$i+1]["room"];
          array_splice($rooms, $i, 0, $last_class);
          array_splice($rooms, $i+1, 0, $first_class);          
          $num_rooms+=2;
          $i--;
      }
      else
      {

          if(($rooms[$i+1]["starttime"] - $rooms[$i]["endtime"]) > $duration
            && $rooms[$i]["room"] === $rooms[$i]["room"])
            {
                /* Size is large enough for the requested gap */
                $available[$index] = array("room"       => $rooms[$i]["room"],
                                      "starttime"  => $rooms[$i]["endtime"],
                                      "endtime"    => $rooms[$i+1]["starttime"]);
                $index++;
            }
      }
    }
    return $available;
}

/**
 * TODO document
 */
function get_room_open($mysqli_conn, $start_time, $end_time, $day, $term, $campus)
{
    $rooms = get_rooms_taken($mysqli_conn, $day, $term, $campus);

    $free = TRUE;
    $prev_room = $rooms[0]["room"];
    $possible_rooms = array( array("room" => ""));
    $index = 0;
    foreach($rooms as $room)
    {
        if($room["room"] != $prev_room)
        {
        	if($free)
        	{
        		$possible_rooms[$index]["room"] = $prev_room;
        		$index++;
        	}
        	$free = TRUE;
        	$prev_room = $room["room"];
        }
        /*if((!($start_time < $room["endtime"] && $end_time < $room["endtime"]
          || $start_time > $room["starttime"] && $end_time > $room["starttime"]))&& $free)*/
        if((($start_time > $room["starttime"] && $start_time < $room["endtime"])
        		|| ($end_time > $room["starttime"] && $end_time < $room["endtime"]))&& $free)
        {
          /* Room is not free */
          $free = FALSE;
          
        }
    }
    return $possible_rooms;
}

/**
 * Get all rooms that are taken on a certain day. 
 * 
 * This can be used to get the slots available on the day as well as if a certain time slot is 
 * available.
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $day is the week day desired, the first letter of the week day name, with Thursday = 'R'
 * @param $term the term desired **NOTE term = { year, semester}
 * @param $campus the campus desired, the full campus name
 * 
 * @return $rooms An array containing the all the rooms that are taken from a start time to an
 * end time, start date to end date given the day, term and the campus.
 */
function get_rooms_taken($mysqli_conn, $day, $term, $campus)
{
    global $offering_table, $semester_table, $date_table, $time_table, $room_table, $campus_table;
    /*
     * Need to make a function that calcs the current week offset based on the date
     * first week of the semester is week 1 and so on...
     */
    //temporarly set to 1 as default
    $week_alt = 1;
    $rooms = array( array("room"       => "",
                          "starttime"  => "",
                          "endtime"    => "",
                          "startdate"  => "",
                          "enddate"    => ""));


    /* Get the rooms that are taken from the database */
    if ($stmt = $mysqli_conn->prepare("SELECT r.name,
                                                st.time AS start_time,
                                                et.time AS end_time,
                                                sd.date AS start_date,
                                                ed.date AS end_date
                                                FROM " . $offering_table . " AS o
                                                INNER JOIN " . $semester_table . " AS s
                                                ON o.semesterId = s.semesterId 
                                                INNER JOIN " . $date_table . " AS sd 
                                                ON o.start_date = sd.dateId
                                                INNER JOIN " . $date_table . " AS ed
                                                ON o.end_date = ed.dateId
                                                INNER JOIN " . $time_table . " AS st
                                                ON o.start_time = st.timeId
                                                INNER JOIN " . $time_table . " AS et
                                                ON o.end_time = et.timeId
                                                INNER JOIN " . $room_table . " AS r 
                                                ON o.roomId = r.roomId
                                                INNER JOIN " . $campus_table . " AS c 
                                                ON r.campusId = c.campusId 
                                                WHERE 
                                                o.day LIKE ? AND
                                                s.year = ? AND 
                                                c.name LIKE ? AND
                                                s.semester LIKE ? AND
                                                ( 
                                                    o.week_alt IS NULL OR
                                                    o.week_alt = ?
                                                ) 
                                                ORDER BY
                                                r.name" ))
    {
        /* bind parameters for markers */
        $stmt->bind_param('ssssd', $day, $term['year'], $campus, $term['semester'], $week_alt);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($room, $start_t, $end_t, $start_d, $end_d);
		    $i = 0;
        while ($stmt->fetch())
        {
            //TODO verify that this works
            $rooms[$i]["room"] = $room;
            $rooms[$i]["starttime"] = $start_t;
            $rooms[$i]["endtime"] = $end_t;
            $rooms[$i]["startdate"] = $start_d;
            $rooms[$i]["enddate"] = $end_d;
            $i++;
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
     *    end_date,}
     * }
     */
    return $rooms;
}

/**
 * Get all of rooms on a given campus
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $campus the campus desired, the full campus name
 *
 * @return $rooms all of the rooms given a campus
 */
function get_rooms($mysqli_conn, $campus)
{
    global $room_table, $campus_table;
    $week_alt = 1;
    $rooms = array();
    
    /* Retrieve all of the rooms on the given campus */
    if ($stmt = $mysqli_conn->prepare("SELECT r.name
                                                FROM " . $room_table . " AS r 
                                                INNER JOIN " . $campus_table . " AS c 
                                                ON r.campusId = c.campusId 
                                                WHERE c.name LIKE ? 
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

/** TODO test
 * Get all of the rooms the user has requested
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user currently logged in.
 * 
 * @return $rooms The room, campus, start time, end time, date, number of people booked,
 * total number of people the expected in a room that the given user has booked
 */
function get_users_rooms($mysqli_conn, $username)
{
    global $time_table, $date_table, $campus_table, $user_table, $room_table, $room_request_table;
    $rooms = array( array("room"         => "",
                          "campus"       => "",
                          "starttime"    => "",
                          "endtime"      => "",
                          "date"         => "",
                          "num_people"   => "",
                          "request_id"   => "",
                          "total_people" => ""));
    
    /* Get the candidate for the current position from the database */
    if ($stmt = $mysqli_conn->prepare("SELECT r.name AS room_name,
                                                c.name AS campus, 
                                                st.time AS start_time,
                                                et.time AS end_time,
                                                oc.date AS date, 
                                                rr.num_people AS num_people,
                                                rr.requestId,
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
                                                u.username LIKE ?
                                                ORDER BY oc.date" ))
    {
        /* bind parameters for markers */
        $stmt->bind_param('s', $username);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($room, $campus, $start_t, $end_t, $date, $num_people, $request_id, $total_num_people);
        $i = 0;
        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $rooms[$i]["room"] = $room;
            $rooms[$i]["campus"] = $campus;
            $rooms[$i]["starttime"] = $start_t;
            $rooms[$i]["endtime"] = $end_t;
            $rooms[$i]["date"] = $date;
            $rooms[$i]["num_people"] = $num_people;
            $rooms[$i]["request_id"] = $request_id;
            $rooms[$i]["total_people"] = $total_num_people;
            $i++;
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
    *     requestId,
    *     total_num_people}
    * }
    */
    return $rooms;
}

/** TODO test
 * Get all of the rooms the user has requested
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param 
 * 
 * @return $rooms The room and total number of people expected for that room given the
 * room, start time, end time and the date.
 */
function get_total_occupied($mysqli_conn, $room, $start_time, $end_time, $day)
{
    global $occupy_table, $time_table, $room_table;
    $rooms = array( array("room"         => "",
                            "total_people" => ""));
    
    /* Get the total occupied in a room given a start and end time and the day from the database */
    if ($stmt = $mysqli_conn->prepare("SELECT r.name AS room_name,
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
        $stmt->bind_result($room, $total_num);
        $i = 0;
        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $rooms[$i]["room"] = $room;
            $rooms[$i]["total_people"] = $total_num;
            $i++;
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

/** TODO test
 * Get all of the rooms the user has requested
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user currently logged in.
 * 
 * @return $rooms The room and total number of people expected for that room for all rooms
 */
function get_all_total_occupied($mysqli_conn)
{
    global $occupy_table, $room_table;
    $rooms = array( array("room"         => "",
                            "total_people" => ""));
    
    /* Get the total occupied in a a room from the database */
    if ($stmt = $mysqli_conn->prepare("SELECT r.name AS room_name, 
                                                SUM(oc.num_people) AS total_num_people 
                                                FROM " . $occupy_table . " AS oc 
                                                INNER JOIN " . $room_table . " AS r 
                                                ON oc.roomId = r.roomIdd 
                                                GROUP BY r.name 
                                                ORDER BY total_num_people DESC" ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($room, $total_num);

        $i = 0;
        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $rooms[$i]["room"] = $room;
            $rooms[$i]["total_people"] = $total_num;
            $i++;
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
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user currently logged in.
 * 
 * @return $rooms The total registered in courses per semester, per year.
 * Note that in order to avoid double counting registered people per course Tutorials and
 * Labs were not included in the summation.
 */
function get_total_registered($mysqli_conn)
{
    global $offering_table, $class_type_table, $semester_table, $room_table;
    $reg = array( array("registered"   => "",
                          "year"         => "",
                          "semester"     => ""));
    
    /* Get the total registered per semester per year from the database */
    if ($stmt = $mysqli_conn->prepare("SELECT SUM(o.registered) AS total_registered, 
                                                s.year, 
                                                s.semester 
                                                FROM " . $offering_table . " AS o 
                                                INNER JOIN " . $class_type_table . " AS ct 
                                                ON o.typeId = ct.typeId 
                                                INNER JOIN " . $semester_table . " AS s  
                                                ON o.semesterId = s.semesterId 
                                                LEFT JOIN " . $room_table . " AS r 
                                                ON o.roomId = r.roomId 
                                                WHERE 
                                                ct.acr <> 'LAB' AND 
                                                ct.acr <> 'TUT'
    											                      GROUP BY s.year,
                                                s.semester " ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($register, $year, $semester);

        $i = 0;
        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $reg[$i]["registered"] = $register;
            $reg[$i]["year"] = $year;
            $reg[$i]["semester"] = $semester;
            $i++;
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
 * Get the total registered for each faculty over the years and semesters
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user currently logged in.
 * 
 * @return $reg The total registered per faculty, per semester, per year. 
 * Note that in order to avoid double counting registered people per course Tutorials and
 * Labs were not included in the summation. 
 */
function get_total_reg_fac($mysqli_conn)
{
    global $offering_table, $course_table, $faculty_table, $class_type_table, $semester_table, $room_table;
    $reg = array( array("registered"   => "",
                        "faculty"      => "",
                        "year"         => "",
                        "semester"     => ""));
    
    /* Get the total registered per faculty per semester per year from the database */
    if ($stmt = $mysqli_conn->prepare("SELECT SUM(o.registered) AS total_registered, 
                                                f.name, 
                                                s.year, 
                                                s.semester 
                                                FROM " . $offering_table . " AS o 
                                                INNER JOIN " . $course_table . " AS c 
                                                ON o.courseId = c.courseId 
                                                INNER JOIN " . $faculty_table . " AS f 
                                                ON c.facultyId = f.facultyId 
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
                                                f.name, 
                                                s.year, 
                                                s.semester 
                                                ORDER BY 
                                                total_registered DESC" ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($register, $faculty, $year, $semester);

        $i = 0;
        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $reg[$i]["registered"] = $register;
            $reg[$i]["faculty"] = $faculty;
            $reg[$i]["year"] = $year;
            $reg[$i]["semester"] = $semester;
            $i++;
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
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user currently logged in.
 * 
 * @return $prof The professors and number of students registered in courses taught by the
 * professor order by the number of students so that the first element will contain the 
 * professor who is "the busiest"
 */
function get_busy_prof($mysqli_conn)
{
    global $offering_table, $professor_table;
    $prof = array( array("professor"   => "",
                         "student_num" => ""));
    
    /* Get the total occupied in a a room from the database */
    if ($stmt = $mysqli_conn->prepare("SELECT p.name, 
                                                SUM(o.registered) AS total_students 
                                                FROM " . $offering_table . " AS o 
                                                INNER JOIN " . $professor_table . " AS p
                                                ON o.profId = p.profId 
                                                GROUP BY 
                                                p.name 
                                                ORDER BY 
                                                total_students DESC" ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($professor, $student_num);

		    $i = 0;
        while ($stmt->fetch())
        {
            //TODO verify that this is valid
            $prof[$i]["professor"] = $professor;
            $prof[$i]["student_num"] = $student_num;
            $i++;
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

/**
 * Remove the given room requests
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $ids
 * array(array( request_id => ""
 *              occupy_id  => "") ) 
 * 
 * @return $deleted true if successful, false otherwise
 */
function remove_requested($mysqli_conn, $username, $ids)
{
    global $room_request_table;
    $num = 0;
    $check = True;
    $num_people = 0;
    foreach($ids as $index => $id)
    {
        $num_people = get_room_request_num($mysqli_conn, $id["request_id"]);

        if ($stmt = $mysqli_conn->prepare("DELETE FROM " . $room_request_table .
                                               " WHERE requestId = ?" ))
        {

            /* bind parameters for markers */
            $stmt->bind_param('d', $id["request_id"]);

            /* execute query */
            $check = $stmt->execute();

            if(!$check)
            {
                return False;
            }
            /* close statement */
            $stmt->close();
        }

        if(!update_occupied($mysqli_conn, $username, $id["occupy_id"], $num_people, False))
        {
            return False;
        }
    }
    return True;
}

/**
 * Update the total number of people in a room
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $occupy_id
 * @param $addition idicates whether the user's requests are being added or taken away from
 * the running total
 * 
 * @return $deleted true if successful, false otherwise
 */
/* TODO change the function to only take the num_people and user passes neg number to take away num_people */
function update_occupied($mysqli_conn, $username, $occupy_id, $num_people, $addition)
{
    global $occupy_table;
    if(!$addition)
    {
        $num_people *= -1;
    }
    $check = False;
    if ($stmt = $mysqli_conn->prepare("UPDATE " . $occupy_table . 
                                           " SET num_people = num_people + ? WHERE occupyId = ?" ))
    {

        /* bind parameters for markers */
        $stmt->bind_param('dd', $num_people, $occupy_id);

        /* execute query */
        $check = $stmt->execute();

        /* close statement */
        $stmt->close();
    }
    return $check;

}

/** TODO test
 * Add a room request to the database
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user making the request
 * @param $room the room the user is requesting
 * @param $start_time the start time the room is requested for
 * @param $end_time the end time of the room is requested for
 * @param $date the date that user requested the room for
 * @param $num_people the total number of people to attend the request
 * 
 * @return $success true if successful, false otherwise
 */
function add_request_occupied($mysqli_conn, $username, $room, $start_time, $end_time, $date, $num_people)
{

    /*
      1. Query the Rooms table for the room's Id given name
      2. Query the Username table for Id given username
      3. Query the time table for the given time (x2)
      4. Query the occupy table if the value already exists
        a) if exists call 
          i)update occupied
          ii) insert the room request
          iii) need to make sure that the room request has not been made already
          iv) return success
        b) else
          i) insert occupied row given info
          ii) insert room request given info
          iii) return success
    */
    $user_id = get_user_id($mysqli_conn, $username);
    $room_id = get_room_id($mysqli_conn, $room);
    $start_id = get_time_id($mysqli_conn, $start_time);
    $end_id = get_time_id($mysqli_conn, $end_time);
    $occupy_id = get_occupied($mysqli_conn, $room, $start_time, $end_time, $date);

    if($occupy_id["occupy_id"] === 0)
    {
        /* No previous occupied entry matching the given time, date and room */
        /* Insert the occupied value */
        if(add_occupied($mysqli_conn, $room_id, $start_id, $end_id, $date, $num_people))
        {
        	$occupy_id["occupy_id"] = get_occupied($mysqli_conn, $room, $start_time, $end_time, $date);
        }
        else
       {
        	return False;
        }
    }
    else
    {
        
        /* Update the occupied value */
        update_occupied($mysqli_conn, $username, $occupy_id["occupy_id"], $num_people, True);
    }

    /* Add a check if the room request has not already been made */
    if(!get_room_request_id($mysqli_conn, $occupy_id["occupy_id"], $user_id, $num_people))
    {
        add_room_request($mysqli_conn, $occupy_id["occupy_id"], $user_id, $num_people);
        return True;
    }

    return False;
}

/**
 * Get the user's id given their username
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $username the username of the user making the request
 * 
 * @return $user_id the user id of the given username
 */
function get_user_id($mysqli_conn, $username)
{
    global $user_table;
    $user_id = 0;
    /* Return user_id */
    if ($stmt = $mysqli_conn->prepare("SELECT userId FROM "
                                               . $user_table . 
                                               " WHERE username LIKE ?" ))
    {

        /* bind parameters for markers */
        $stmt->bind_param('s', $username);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($user_id);

        $stmt->fetch();

        /* close statement */
        $stmt->close();
    }
    return $user_id;
}

/**
 * Get the room's id given the room name
 *
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 * @param $room the room name 
 * 
 * @return $user_id the user id of the given username
 */
function get_room_id($mysqli_conn, $room)
{
    global $room_table;
    $room_id = 0;
    /* Return room_id */
    if ($stmt = $mysqli_conn->prepare("SELECT roomId FROM "
                                               . $room_table . 
                                               " WHERE name LIKE ?" ))
    {

        /* bind parameters for markers */
        $stmt->bind_param('s', $room);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($room_id);

        $stmt->fetch();

        /* close statement */
        $stmt->close();
    }
    return $room_id;
}

/**
 * TODO document
 */
function get_time_id($mysqli_conn, $time)
{
    global $time_table;
    $time_id = 0;
    /* Return time id */
    if ($stmt = $mysqli_conn->prepare("SELECT timeId FROM "
                                               . $time_table . 
                                               " WHERE time = ?" ))
    {

        /* bind parameters for markers */
        $stmt->bind_param('s', $time);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($time_id);

        $stmt->fetch();

        /* close statement */
        $stmt->close();
    }
    return $time_id;
}

/**
 * TODO document
 */
function get_occupied($mysqli_conn, $room, $start_time, $end_time, $date)
{
    global $occupy_table, $time_table, $room_table;
    $occupied = array("occupy_id"  => 0,
                      "num_people" => 0);
    /* Get the occupied # people or Id, current use is to determine if exists */
    if ($stmt = $mysqli_conn->prepare("SELECT oc.occupyId, oc.num_people FROM "
                                               . $occupy_table . " AS oc 
                                               INNER JOIN " . $time_table . " AS st 
                                               ON oc.start_time = st.timeId 
                                               INNER JOIN " . $time_table . " AS et 
                                               ON oc.end_time = et.timeId 
                                               INNER JOIN " . $room_table . " AS r 
                                               ON oc.roomId = r.roomId 
                                               WHERE 
                                               st.time = ? AND 
                                               et.time = ? AND 
                                               r.name LIKE ? AND 
                                               date = ?" ))
    {

        /* bind parameters for markers */
        $stmt->bind_param('ssss', $start_time, $end_time, $room, $date);

        /* execute query */
        $stmt->execute();

        //TODO FIX
        /* bind result variables */
        $stmt->bind_result($occupy, $num_people);

        $stmt->fetch();
        
        $occupied["occupy_id"] = $occupy;
        $occupied["num_people"] = $num_people;

        /* close statement */
        $stmt->close();
    }
    return $occupied;
}

/**
 * TODO document
 */
function add_occupied($mysqli_conn, $room_id, $start_id, $end_id, $date, $num_people)
{
    global $occupy_table;
    $check = 0;
    if ($stmt = $mysqli_conn->prepare("INSERT INTO " . $occupy_table . 
                                           " (roomId, start_time, end_time, 
                                            date, num_people) VALUES 
                                            (?, ?, ?, ?, ?)" ))
    {

        /* bind parameters for markers */
        $stmt->bind_param('dddsd', $room_id, $start_id, $end_id, $date, $num_people);

        /* execute query */
        $check = $stmt->execute();

        /* close statement */
        $stmt->close();
    }
    return $check;
}

/**
 * TODO document
 */
function add_room_request($mysqli_conn, $occupy_id, $user_id, $num_people)
{
    global $room_request_table;
    $check = 0;
    if ($stmt = $mysqli_conn->prepare("INSERT INTO " . $room_request_table . 
                                           " (userId, occupyId, num_people) VALUES 
                                            (?, ?, ?)" ))
    {

        /* bind parameters for markers */
        $stmt->bind_param('ddd', $user_id, $occupy_id, $num_people);

        /* execute query */
        $check = $stmt->execute();

        /* close statement */
        $stmt->close();
    }
    return $check;
}

/**
 * TODO document
 */
function get_room_request_id($mysqli_conn, $occupy_id, $user_id, $num_people)
{
    global $room_request_table;
    $request_id = 0;
    /* Get the occupied # people or Id, current use is to determine if exists */
    if ($stmt = $mysqli_conn->prepare("SELECT requestId FROM "
                                               . $room_request_table .
                                               " WHERE 
                                               occupyId = ? AND 
                                               userId = ? AND 
                                               num_people = ?" ))
    {

        /* bind parameters for markers */
        $stmt->bind_param('ddd', $occupy_id, $user_id, $num_people);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($value);

        $stmt->fetch();

        $request_id = $value;

        /* close statement */
        $stmt->close();
    }
    return $request_id;
}

/**
 * TODO document
 */
function get_room_request_num($mysqli_conn, $request_id)
{
    global $room_request_table;
    $num_people = 0;
    /* Get the occupied # people or Id, current use is to determine if exists */
    if ($stmt = $mysqli_conn->prepare("SELECT num_people FROM "
                                               . $room_request_table .
                                               " WHERE requestId = ?" ))
    {

        /* bind parameters for markers */
        $stmt->bind_param('d', $request_id);

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($value);

        $stmt->fetch();

        $num_people = $value;

        /* close statement */
        $stmt->close();
    }
    return $num_people;
}

/**
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 *
 * @return $courses the courses and number of courses
 */
function get_course_count($mysqli_conn)
{
    global $offering_table, $faculty_table, $professor_table, $course_table;
    $num_people = 0;
    $courses = array(array( "course"     => "",
                            "num_course" => ""));
    /* Get the occupied # people or Id, current use is to determine if exists */
    if ($stmt = $mysqli_conn->prepare("SELECT
                                       c.name,
                                       COUNT(o.courseId) AS number_of_courses
                                       FROM " . $offering_table . " AS o, "
                                       . $faculty_table . " AS f, "
                                       . $professor_table . " AS p, "
                                       . $course_table . " AS c 
                                       WHERE
                                       p.profId = o.profId AND
                                       f.facultyId = c.facultyId AND
                                       c.courseId = o.courseId AND
                                       f.code = ANY
                                       (SELECT
                                              f2.code
                                        FROM
                                              faculties AS f2
                                        WHERE 
                                              f2.code LIKE '%H%' OR
                                              f2.code LIKE '%T%')
                                        GROUP BY
                                           c.name" ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($name, $num);

        while($stmt->fetch())
        {
            $courses["course"] = $name;
            $courses["num_course"] = $num;
        }

        /* close statement */
        $stmt->close();
    }
    return $courses;
}

/**
 * Returns all course names that start at the specified time (where that time is
 * whether any two courses from the same faculty share the same start time
 * regardless of day).
 * 
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 *
 * @return $courses the courses' names
 */
function get_same_start_time($mysqli_conn)
{
    global $offering_table, $faculty_table, $course_table;
    $num_people = 0;
    $courses = array(array( "course"     => ""));
    /* Get the occupied # people or Id, current use is to determine if exists */
    if ($stmt = $mysqli_conn->prepare("SELECT
                                        c.name
                                    FROM "
                                        . $course_table . " AS c, " 
                                        . $offering_table . " AS o, "
                                        . $faculty_table . " AS f 
                                    WHERE
                                        c.facultyId = f.facultyId AND
                                        c.courseId = o.courseId AND
                                        o.start_time = ANY 
                                        (SELECT
                                            o2.start_time
                                        FROM
                                            offerings AS o2,
                                            courses AS c2,
                                            faculties AS f2
                                        WHERE
                                            c2.facultyId = f2.facultyId AND
                                            c2.courseId = o2.courseId AND
                                            f2.facultyId <> f.facultyId)" ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($name);

        while($stmt->fetch())
        {
            $courses["course"] = $name;
        }

        /* close statement */
        $stmt->close();
    }
    return $courses;
}

/**
 * Display all of the rooms on campus with the day and room capacity. 
 * 
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 *
 * @return $courses the course_id, day, name and room_capacity
 */
function get_all_class_rooms($mysqli_conn)
{
    global $offering_table, $room_table;
    $num_people = 0;
    $courses = array(array( "course_id"     => "",
                            "day"           => "",
                            "name"          => "",
                            "room_cap"      => ""));
    /* Get the occupied # people or Id, current use is to determine if exists */
    if ($stmt = $mysqli_conn->prepare("SELECT
                                       o.courseId AS course_name,
                                       o.day,
                                       r.name AS room_name,
                                       r.room_capacity
                                    FROM "
                                       . $room_table . " AS r RIGHT JOIN "
                                       . $offering_table . " AS o ON  
                                       r.roomId = o.roomId)
                                    UNION
                                    (SELECT
                                       o.courseId AS course_name,
                                       o.day,
                                       r.name AS room_name,
                                       r.room_capacity
                                    FROM "
                                       . $room_table . " AS r LEFT JOIN "
                                       . $offering_table . " AS o ON 
                                       r.roomId = o.roomId)" ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($id, $day, $name, $cap);

        while($stmt->fetch())
        {
            $courses["course_id"] = $id;
            $courses["day"] = $day;
            $courses["name"] = $name;
            $courses["room_cap"] = $cap;
        }

        /* close statement */
        $stmt->close();
    }
    return $courses;
}

/**
 * Display all of the rooms on campus with the day and room capacity. 
 * 
 * @param mysqli $mysqli_conn The mysqli connection object for the ucsc elections DB
 *
 * @return $prof the professor name
 */
function get_profs($mysqli_conn)
{
    global $offering_table, $faculty_table, $course_table, $professor_table;
    $num_people = 0;
    $prof = array(array( "name"     => ""));
    /* Get the occupied # people or Id, current use is to determine if exists */
    if ($stmt = $mysqli_conn->prepare("SELECT
                                        p.name
                                      FROM "
                                        . $professor_table . " AS p, "
                                        . $offering_table . " AS o, "
                                        . $course_table . " AS c, "
                                        . $faculty_table . " AS f 
                                      WHERE
                                        p.profId = o.profId AND
                                        c.courseId = o.courseId AND
                                        f.facultyId = c.facultyId AND
                                        f.code LIKE 'MATH')
                                      UNION
                                      (SELECT
                                        p1.name
                                      FROM "
                                        . $professor_table . " AS p1, " 
                                        . $offering_table . " AS o1, " 
                                        . $course_table . " AS c1, " 
                                        . $faculty_table . " AS f1
                                      WHERE
                                        p1.profId = o1.profId AND
                                        c1.courseId = o1.courseId AND
                                        f1.facultyId = c1.facultyId AND
                                        f1.code LIKE 'ENGR')" ))
    {

        /* execute query */
        $stmt->execute();

        /* bind result variables */
        $stmt->bind_result($name);

        while($stmt->fetch())
        {
            $prof["name"] = $name;
        }

        /* close statement */
        $stmt->close();
    }
    return $prof;
}

?>

