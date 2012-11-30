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
 * The page that will allow the user to enter the information about the room they
 * wish to look for.
 * This can be either the start time and duration, or the start time and end time,
 * or just the duration. Also the date and campus will need to be entered as well.
 * 
 * 1. If the user is not logged or their session is invalid
 * 
 * 		a) Redirect the user to the login page
 * 
 * 2. If the user has a valid login 
 * 
 * 		a) Show the form for entering a room request.
 *
 * 3. If the user has submitted data (post data)
 * 
 * 		a) validate the post data
 * 			i) return an error if the submitted data is invalid
 * 
 * 		b) parse the post data and redirect to the results page
 * 
 */



?>