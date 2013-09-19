<?php
/*
 *  Free Room Finder Website
 *
 *  Copyright (C) 2013 Amit Jain, Anthony Jihn, Jonathan Gillett, Joseph Heron, and Wesley Unwin
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

require_once 'inc/db_interface.php';
require_once 'inc/free_room_auth.php';
require_once 'inc/validate.php';
require_once 'inc/verify.php';

session_start();


/* Connect to the database */
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
	
	/* Redirect the user to the room request page */
	/* Redirect the user to the request page */
	header('Location: room_request.php');
}

/* Invalid cookie or session data/etc.. */
else
{
	/* Redirect the user to the login page */
	header('Location: login.php');
}
?>