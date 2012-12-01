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


<div>
	<br/>
	<h1> Welcome to the Free Room Finder! </h1>
	<br/>
	<br/>
	
	<form class="form-horizontal">
		<h2> Please sign in... </h2> <br />
		
		<!-- Username label and textbox -->
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
				<button type="submit" id="LoginButton" name="LoginButton" class="btn">Sign in</button>
			</div>
		</div>
		
		<br />
		<br />
		
		<!-- 'Not a member...' and register button' -->
		<div class="control-group">
			<div class="controls">
				<h3>Not a Member? ... </h3> <button type="submit" id="RegisterButton" name="RegisterButton" class="btn btn-large">Register!</button>
			</div>
		</div>	
		
	</form>
</div>





	





