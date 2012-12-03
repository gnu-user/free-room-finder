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

require_once "../inc/free_room_auth.php";
require_once "../inc/db_interface.php";
require_once "../inc/validate.php";
require_once "../inc/verify.php";
require 'Slim/Slim.php';

/*
 * The REST web service api which provides access to statistical and other
 * useful data, which can be retrieved from the database using a GET operation
 */

$app = new Slim();

/* Gets a list of the busiest profs on campus with the number of students they have */
$app->get('/busyprofs', 'getBusyProfs');
$app->get('/busyprofs/:num', 'getBusyProfsNum');

/* Gets the total number of registered students per semester per year */
$app->get('/totalregistered', 'getTotalRegistered');

/* Gets the total number of registered students per faculty per semester per year */
$app->get('/totalregisteredfaculty', 'getTotalRegisteredFaculty');

$app->run();

/* Gets a list of the busiest profs on campus with the number of students they have */
function getBusyProfs()
{
	global $db_user, $db_pass, $db_name;
	
	/* Connect to the database */
	$mysqli_conn = new mysqli("localhost", $db_user, $db_pass, $db_name);
	
	/* check connection */
	if (mysqli_connect_errno()) {
		printf("Connect failed: %s\n", mysqli_connect_error());
		exit();
	}
	
	$busy_profs = get_busy_prof($mysqli_conn);
	
	/* Encode the results as JSON */
	echo '{"busyProfs": ';
	echo json_encode($busy_profs);
	echo '}}';
}

/* Gets only the specified number of busiest profs */
function getBusyProfsNum($num)
{
	global $db_user, $db_pass, $db_name;

	/* Connect to the database */
	$mysqli_conn = new mysqli("localhost", $db_user, $db_pass, $db_name);

	/* check connection */
	if (mysqli_connect_errno()) {
		printf("Connect failed: %s\n", mysqli_connect_error());
		exit();
	}

	$busy_profs = get_busy_prof_num($mysqli_conn, $num);

	/* Encode the results as JSON */
	echo '{"busyProfs": ';
	echo json_encode($busy_profs);
	echo '}}';
}


/* Gets the total number of registered students per semester per year */
function getTotalRegistered()
{
	global $db_user, $db_pass, $db_name;
	
	/* Connect to the database */
	$mysqli_conn = new mysqli("localhost", $db_user, $db_pass, $db_name);
	
	/* check connection */
	if (mysqli_connect_errno()) {
		printf("Connect failed: %s\n", mysqli_connect_error());
		exit();
	}
	
	$total_registered = get_total_registered($mysqli_conn);
	
	/* Encode the results as JSON */
	echo '{"totalRegistered": ';
	echo json_encode($total_registered);
	echo '}}';
}

/* Gets the total number of registered students per faculty per semester per year */
function getTotalRegisteredFaculty()
{
	global $db_user, $db_pass, $db_name;

	/* Connect to the database */
	$mysqli_conn = new mysqli("localhost", $db_user, $db_pass, $db_name);

	/* check connection */
	if (mysqli_connect_errno()) {
		printf("Connect failed: %s\n", mysqli_connect_error());
		exit();
	}

	$total_reg_fac = get_total_reg_fac($mysqli_conn);

	/* Encode the results as JSON */
	echo '{"totalRegisteredFaculty": ';
	echo json_encode($total_reg_fac);
	echo '}}';
}
?>