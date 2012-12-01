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
 ?>
 
<form class="form-horizontal">
		<br/>
		<h3> Sign up for a free room finder account... </h3>
		<br/>
		
		<!-- Choose a Username -->
		<div class="control-group">
			<label class="control-label" >Choose a user name:</label>				
			<div class="controls">
				<input type="text" id="register_username" name="register_username" maxlength="31" pattern="^[A-Za-z][A-Za-z0-9]*(?:_[A-Za-z0-9]+)*$" placeholder="Username">      		
			</div>
		</div>

		<!-- Enter your student number... -->
		<div class="control-group">
			<label class="control-label">Student number: </label>				
			<div class="controls">
				<input type="text" id="register_studentnumber" name="register_studentnumber" maxlength="9" pattern="^[A-Za-z][A-Za-z0-9]*(?:_[A-Za-z0-9]+)*$" placeholder="">      		
			</div>
		</div>
				
		<!-- Choose a Password -->
		<div class="control-group">
			<label class="control-label">Create a password: </label>
			<div class="controls">
				<input type="text" id="register_password" name="register_password" maxlength="31" pattern="^[a-zA-Z0-9\!\$\%\^\&amp;\*\(\)\_\?]{6,31}$" placeholder="Password">
			</div>
		</div>
 
		<!-- Enter Email Address-->
		<div class="control-group">
			<label class="control-label" for="inputEmail">Email Address:</label>				
			<div class="controls">
				<input type="text" id="register_email" name="register_email" maxlength="31" pattern="^[A-Za-z][A-Za-z0-9]*(?:_[A-Za-z0-9]+)*$" placeholder="">      		
			</div>
		</div>

		<!-- Sign Up -->
		<div class="control-group">
			<div class="controls">
				<button type="submit" class="btn">Sign up</button>
			</div>
		</div>
		
</form>



 
 
 
 
 
 
 
 
 
 
 </div>
 
 
 
 
 
 
 