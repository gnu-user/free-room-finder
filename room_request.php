<?php
/*
 *  Free Room Finder Website
 *
 *  Copyright (C) 2013 Jonathan Gillett and Joseph Heron
 *  All rights reserved.
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

session_start();

 /*
 * The page that will allow the user to enter the information about the room they
 * wish to look for.
 * This can be either the start time and duration, or the start time and end time,
 * or just the duration. Also the date and campus will need to be entered as well.
 * 
 * 1. If the user is not logged in or their session is invalid
 * 
 * 		a) Redirect the user to the login page
 * 
 * 2. If the user has a valid login 
 * 
 * 		a) Show the form for entering a room request.
 *
 */

/* Connect to the database */
$mysqli_conn = new mysqli("localhost", $db_user, $db_pass, $db_name);
$mysqli_login = new mysqli("localhost", $db_user, $db_pass, $db_login);

/* check connection */
if (mysqli_connect_errno()) {
	printf("Connect failed: %s\n", mysqli_connect_error());
	exit();
}

/* 1. If the user is not logged in or their session is invalid */
if (verify_login_cookie($mysqli_login, $SESSION_KEY) === false
	&& (!isset($_SESSION['login'])
	|| verify_login_session($mysqli_login, $_SESSION['login'], $SESSION_KEY) === false))
{

	//echo '<p>'.!isset($_SESSION['login']).'</p>';
	/* Redirect the user to the login page */
	header('Location: login.php');
}

/* User has a valid login cookie set / has logged into the site with valid account */
elseif (verify_login_cookie($mysqli_login, $SESSION_KEY)
		|| verify_login_session($mysqli_login, $_SESSION['login'], $SESSION_KEY))
{
	/* FIX, forgot to account for when user has login cookie set but there is no session
	 * data, have to retrieve username from cookie and then set the session data
	*/
	if (verify_login_cookie($mysqli_login, $SESSION_KEY))
	{
		/* Get the login cookie data */
		$login_cookie = htmlspecialchars($_COOKIE['login']);

		/* Get the username from login cookie data and set session info */
		$username = username_from_session($mysqli_login, $login_cookie, $SESSION_KEY);
		set_session_data($mysqli_login, $username, $SESSION_KEY);
	}
}

/* Invalid cookie or session data/etc.. */
else
{
	/* Redirect the user to the login page */
	header('Location: login.php');
}


/* Valid user has logged in, display request a room page */
include 'templates/header-user.php';

/* Get the list of all campuses, which are displayed in the request form */
$campuses = get_all_campus($mysqli_conn);

include 'templates/request.php';

/* Include the footer */
include 'templates/footer.php';
?>