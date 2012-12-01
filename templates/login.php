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
 * The default template for login page
 */
?>
<div class="container">
	<form action="RequestARoom.php" method="POST" class="form-signin">            <!-- TO DO: replace RequestARoom.php with actual name of page -->
		<h1> Please Log in... </h1> <br/>
		UserName: <input id="login_username" class="input-large login-form" required type="text" maxlength="31" pattern="^[A-Za-z][A-Za-z0-9]*(?:_[A-Za-z0-9]+)*$" name="login_username" placeholder="Username"             /> <br/>
		Password: <input id="login_password" class="input-large login-form" required type="password" maxlength="31" pattern="^[a-zA-Z0-9\!\$\%\^\&amp;\*\(\)\_\?]{6,31}$" name="login_password" placeholder="Password"      /> <br/>
		<input id="login_remember" class="login-checkbox" type="checkbox" name="login_remember" checked="checked" value="1"/> Remember me                                                                                      <br/>
		<input id="LoginButton" class="btn btn-primary btn-large" type="submit" value="Login" /> <br/>
	</form>	
	
	<br/>
	<!-- The register checkbox -->
	<form action="Register.php" method="POST" class="form-signin">				<!-- TO DO: replace Register.php with actual name of page -->
		Not a member? ...  <br/>
		<input id="Register" class="btn btn-primary btn-large" type="submit" value="Register!" />  <br/>
	</form>
</div>




	





