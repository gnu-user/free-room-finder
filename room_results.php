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

require_once "inc/free_room_auth.php";
require_once "inc/db_interface.php";
require_once "inc/validate.php";
require_once "inc/verify.php";

/*
 * Display the rooms that fit the given post data
 * 
 * 1. If the user is not logged in or their session is invalid
 * 
 * 		a) Redirect the user to the login page
 *
 * 2. If the user has submitted data (post data)
 * 
 * 		a) validate the post data
 * 			i) return an error if the submitted data is invalid
 * 
 * 3. If data is invalid
 * 
 *		a) Display 'invalid query'
 *		b) Have a button to redirect to room_request.php
 *
 * 4. If data is valid
 * 
 * 		a) query the database with post data
 * 		b) Display the results in fancy way
 * 		c) have option to download results
 * 		d) link to statitics page
 *
 * 
 */

/* Connect to the database */
$mysqli_conn = new mysqli("localhost", $db_user, $db_pass, $db_name);

/* check connection */
if (mysqli_connect_errno()) {
	printf("Connect failed: %s\n", mysqli_connect_error());
	exit();
}


/* 1. If the user is not logged in or their session is invalid */
if (verify_login_cookie($mysqli_conn, $SESSION_KEY) === false
	&& (!isset($_SESSION['login'])
	|| verify_login_session($mysqli_conn, $_SESSION['login'], $SESSION_KEY) === false))
{
	/* Redirect the user to the login page */
	header('Location: login.php');
}

/* User has a valid login cookie set / has logged into the site with valid account */
elseif (verify_login_cookie($mysqli_conn, $SESSION_KEY)
		|| verify_login_session($mysqli_conn, $_SESSION['login'], $SESSION_KEY))
{
	/* FIX, forgot to account for when user has login cookie set but there is no session
	 * data, have to retrieve username from cookie and then set the session data
	*/
	if (verify_login_cookie($mysqli_conn, $SESSION_KEY))
	{
		/* Get the login cookie data */
		$login_cookie = htmlspecialchars($_COOKIE['login']);

		/* Get the username from login cookie data and set session info */
		$username = username_from_session($mysqli_conn, $login_cookie, $SESSION_KEY);
		set_session_data($mysqli_conn, $username, $SESSION_KEY);
	}
}

/* Invalid cookie or session data/etc.. */
else
{
	/* Redirect the user to the login page */
	header('Location: login.php');
}


/*
 * 2. If the user has submitted data (post data)
 *
 * 		a) validate the post data
 * 			i) return an error if the submitted data is invalid
 *
 * 		b) parse the post data and redirect to the results page
 */
if (isset($_POST['select_time'])
	&& isset($_POST['select_duration'])
	&& isset($_POST['select_date'])
	&& isset($_POST['select_campus'])
	&& isset($_POST['select_num_people']))
{
	/* 4. If data is valid
	 *
	 * 		a) query the database with post data
	 * 		b) Display the results in fancy way
	 * 		c) have option to download results
	 * 		d) link to statitics page
	 */
	$start_time = "10:10:00";
	$end_time = "11:00:00";
	$day = "R";
	$term = array("2012", "Fall");
	$campus = "North Oshawa Campus";
	
	/* Get the available rooms */
	//$available = get_room_open_dur($mysqli_conn, 2, $day, $term, $campus);
	$available = get_room_open($mysqli_conn, $start_time, $end_time, $day, $term, $campus);
	
	foreach( $available as $avail ) 
	{ 
		$b = $doc->createElement( "TimeSlot" ); 

		$name = $doc->createElement( "room" ); 
		$name->appendChild( $doc->createTextNode( $avail['room'] )); 
		$b->appendChild( $name ); 

		$campus = $doc->createElement( "campus" ); 
		$campus->appendChild( $doc->createTextNode( $avail['campus'] )); 
		$b->appendChild( $campus ); 

		$start_time = $doc->createElement( "starttime" ); 
		$start_time->appendChild( $doc->createTextNode( $avail['starttime'] )); 
		$b->appendChild( $start_time ); 

		$end_time = $doc->createElement( "endtime" ); 
		$end_time->appendChild( $doc->createTextNode( $avail['endtime'] )); 
		$b->appendChild( $end_time ); 

		$date = $doc->createElement( "date" ); 
		$date->appendChild( $doc->createTextNode( $avail['date'] )); 
		$b->appendChild( $date );

		$request_num_people = $doc->createElement( "request_num_people" ); 
		$request_num_people->appendChild( $doc->createTextNode( $avail['request_num_people'] )); 
		$b->appendChild( $request_num_people ); 

		$r->appendChild( $b ); 
	}
	echo $doc->saveXML(); 
	$doc->save("time_slots.xml");
}

/* Invalid post data display error message */
else
{
	/* Redirect the user to the request page */
	header('Location: room_request.php');
}


/* Display the rooms found */
include 'templates/header-user.php';
include 'templates/results.php';

/* Include the footer */
include 'templates/footer.php';
exit();
?>