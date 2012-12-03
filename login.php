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

session_start();

/* Connect to the database */
$mysqli_conn = new mysqli("localhost", $db_user, $db_pass, $db_name);

/* check connection */
if (mysqli_connect_errno()) {
	printf("Connect failed: %s\n", mysqli_connect_error());
	exit();
}

/* 0. Check if the user is logged in and has clicked the Sign Out button */
if (isset($_POST['signout']))
{
	session_unset();

	/* Overwrite the login cookie as NULL if it exists */
	if (isset($_COOKIE['login']))
	{
		setcookie('login', NULL, time()+1);
	}

	/* Refresh the page */
	header('Location: '.$_SERVER['REQUEST_URI']);
}

/* 1. User is not logged in and has submitted their login information */
if (verify_login_cookie($mysqli_conn, $SESSION_KEY) === false
	&& (!isset($_SESSION['login'])
			|| verify_login_session($mysqli_conn, $_SESSION['login'], $SESSION_KEY) === false)
	&& isset($_POST['login_username'])
	&& isset($_POST['login_password']))
{
	/* a) If the login information is valid and they entered the correct username/password  */
	if (validate_username($_POST['login_username']) && validate_password($_POST['login_password'])
			&& verify_login($mysqli_conn, $_POST['login_username'] , $_POST['login_password'], $AES_KEY))
	{
		set_session_data($mysqli_conn, $_POST['login_username'], $SESSION_KEY);

		if ($_POST['login_remember'] == 1)
		{
			set_login_cookie();
		}

		/* Redirect to the room_request page */
		header('Location: room_request.php');
	}
	/* b) The login information is invalid display the invalid login page */
	else
	{
		/* Invalid password entered, used by template to display error message */
		$invalid = true;
		
		include 'templates/header.php';
		include 'templates/login.php';
	}
}
/* 2. User is not logged in, display the login page template */
elseif (verify_login_cookie($mysqli_conn, $SESSION_KEY) === false
		&& (!isset($_SESSION['login'])
			|| verify_login_session($mysqli_conn, $_SESSION['login'], $SESSION_KEY) === false)
		&& !isset($_POST['login_username'])
		&& !isset($_POST['login_password']))
{
	/* Include the header and login templates */
	include 'templates/header.php';
	include 'templates/login.php';
}

/* User is already logged in */
else
{
	/* Redirect to room_request page */
	header('Location: room_request.php');
}

/* Include the footer */
include 'templates/footer.php';
?>