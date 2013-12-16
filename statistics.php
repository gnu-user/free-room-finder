<?php
/*
 *  Free Room Finder
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

require_once "inc/auth.php";
require_once "inc/db_interface.php";
require_once "inc/validate.php";
require_once "inc/verify.php";
require_once "inc/utility.php";

session_start();

/*
 * Display different statical representations of the data obtained through scraping the school's course website.
 * 
 * User does not have to be logged into view this page and can redirect back to the 'main' page
 * This main page refers to (if logged out) login.php or (if logged in) room_request.php
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
	/* Redirect the user to the login page */
	header('Location: login.php');
}

/* User has a valid login cookie set / has logged into the site with valid account
 * and the POST data isset */
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


/* Display the statistics page */
include 'templates/header-user.php';

include 'templates/statistics.php';

/* Include the footer */
include 'templates/footer.php';



?>