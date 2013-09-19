<?php
/*
 *  Free Room Finder Website
 *
 *  Copyright (C) 2013 Wesley Unwin, Jonathan Gillett, and Joseph Heron
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
	<div class="page-header">
		<h1> Welcome to the Free Room Finder! </h1>
	</div>
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
		<div class="well span8">
			<form class="form-horizontal" action="login.php" method="post">
				<h2> Please sign in</h2>
				<br />
				<!-- Username label and textbox -->
				<fieldset>
						<div class="control-group">
							<label class="control-label">User name:</label>				
							<div class="controls">
								<input type="text" id="login_username" name="login_username" maxlength="31" pattern="^[A-Za-z][A-Za-z0-9]*(?:_[A-Za-z0-9]+)*$" placeholder="Username">      		
							</div>
						</div>
						<!-- Password label and textbox -->
						<div class="control-group">
							<label class="control-label" for="inputPassword">Password</label>
							<div class="controls">
								<input type="password" id="login_password" name="login_password" maxlength="31" pattern="^[a-zA-Z0-9\!\$\%\^\&amp;\*\(\)\_\?]{6,31}$" placeholder="Password">
							</div>
						</div>
						<!-- Remember me label and checkbox -->
						<div class="control-group">
							<div class="controls">
								<label class="checkbox">
									<input type="checkbox" id="login_remember" name="login_remember" checked="checked" value="1" > Remember me
								</label>
								<button type="submit" id="login_button" name="login_button" class="btn">Sign in</button>
								<p>
									<a target="_blank" href="http://www.cs-club.ca/reset">Forgot/Reset Password</a>
								</p>
							</div>
						</div>						
				</fieldset>
			</form>
			<br />
			<form class="form-horizontal" action="https://www.cs-club.ca/register/">
				<!-- 'Not a member...' and register button' -->
				<h2>Not a Member?</h2>
				<br />
				<div class="control-group">
					<div class="controls">	
						<button type="submit" id="register_button" name="register_button" class="btn btn-large">Register!</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</section>




	





