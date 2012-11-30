<?php

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