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


/**
 * The following is a configuration file containing all of the free room website
 * authentication information such as the database access and database names
 * as well as the AES encrypt/decrypt keys.
 * 
 * Basically, anything you wouldn't want everyone else to view and have access
 * to goes in this file, as it is not part of the open source code that is
 * publicly accessible.
 */

/* Database access */
$db_user = '';
$db_pass = '';
$db_name = 'free_room_finder';
$db_login = 'ucsc_accounts';

/* AES ENCRYPT/DECRYPT KEY */
$AES_KEY = 'test123';

/* SESSION ENCRYPT/DECRYPT KEY */
$SESSION_KEY = 'test123';
?>
