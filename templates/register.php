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
 ?>
 
<section id="register">
	<div class="page-header">
		<h1>Register</h1>
	</div>
	<div class="row">
		<div class="span8">
			<form class="well form-horizontal" action="register.php" method="post" accept-charset="UTF-8">
				<fieldset>
					<!--  First & Last Name -->
					<div class="control-group">
						<label for="register_username" class="control-label">First Name:</label>				
						<div class="controls">
							<input id="first_name" name="first_name" required type="text" maxlength="31" pattern="^(([A-Za-z]+)|\s{1}[A-Za-z]+)+$" placeholder="First name..."/>    		
						</div>
					</div>
					<div class="control-group">
						<label for="register_username" class="control-label">Last Name:</label>				
						<div class="controls">
							<input id="last_name" name="last_name" required type="text" maxlength="31" pattern="^(([A-Za-z]+)|\s{1}[A-Za-z]+)+$" placeholder="Last name..."/>    		      		
						</div>
					</div>
					<!-- Enter your student number... -->
					<div class="control-group">
						<label for="register_studentnumber" class="control-label">Student Number:</label>				
						<div class="controls">
							<input type="text" id="student_number" name="student_number" required maxlength="9" pattern="^\d{9}$" placeholder="100123456..."/>      		
						</div>
					</div>			
					<!-- Choose a Username -->
					<div class="control-group">
						<label for="register_username" class="control-label">User Name:</label>				
						<div class="controls">
							<input type="text" id="username" name="username" required maxlength="31" pattern="^[A-Za-z][A-Za-z0-9]*(?:_[A-Za-z0-9]+)*$" placeholder="Username..."/>      		
						</div>
					</div>
					<!-- Choose a Password -->
					<div class="control-group">
						<label for="register_password" class="control-label">Password:</label>
						<div class="controls">
							<input type="password" id="password" name="password" required maxlength="31" pattern="^[a-zA-Z0-9\!\$\%\^\&amp;\*\(\)\_\?]{6,31}$" placeholder="Password..."/>
						</div>
					</div>
					<!-- Enter Email Address-->
					<div class="control-group">
						<label for="register_email" class="control-label" for="inputEmail">Email Address:</label>				
						<div class="controls">
							<input type="text" id="email" name="email" required maxlength="63" pattern="^.+@(.+\..+)+$" placeholder="something@email.com..."/>      		
						</div>
					</div>
					<!-- Sign Up -->
					<div class="control-group">
						<div class="controls">
							<button type="submit" id="register_newuser" name="register_newuser" class="btn">Sign up</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</section>