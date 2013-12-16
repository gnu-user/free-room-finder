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

/*  
 *  DEPENDENCIES
 *  ------------
 *  
 *  For displaying a notification that the login information is invalid checks if
 *  $invalid isset, if so displays a notice
 *  
 */
 ?>

<section id="login">
		
		<div class="hero-unit">
			<h1>Free Room Finder</h1>
			<br/>
			<div class="row">
			<!--  Display an error if they entered invalid credentials -->
			<?php
				if (isset($invalid))
				{
					echo '<div class="alert alert-error span8">
		              		<button type="button" class="close" data-dismiss="alert">×</button>
		             	 	<strong>Invalid Credentials!</strong> Please enter a valid username and password.
		            	  </div>';
				}
	     	?>
	     	</div>
			
			  <p> Thank you for visiting the Computer Science Club free room finder. Please login to gain instant 
			  		access to available rooms all over campus. If you cannot remember your password please visit our 
			  		<a href="https://cs-club.ca/reset/" target="_blank">password reset page</a>.
			  </p>
			  <p>
			  		If you are currently not a club member and would like more information about the Computer
			  		Science Club then please click below to visit the Computer Science Club website. If you
			  		are a member who has been registered for at least one semester and for some reason cannot
			  		login then please visit our <a target="_blank" href="http://cs-club.ca/contact">Contact Page</a>
			  		and contact us about your issue.
			  </p>
		</div>
</section>




	





