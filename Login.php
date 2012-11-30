<?php
/*									NOTE: Still being worked on by Wes
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

//include 'templates/header.php';            //WILL RE-ENABLE HEADER AND FOOTER ONCE THEY'RE ACTUALLY DONE

//include 'templates/footer.php';


?>


<html>
	<head>
		<title> Welcome to the free-room-finder! </title>
	</head>
	
	<body>
		<form action="RequestARoom.php" method="POST">            <!-- TO DO: replace RequestARoom.php with actual name of page -->
			<h1> Please Log in... </h1> <br/>
			UserName: <input type = "text" id="UserNameTextInput"              /> <br/>
			Password: <input type = "password" id="PasswordTextInput"          /> <br/>
			<input id="RememberMeCheckbox" type="checkbox"/> Remember me          <br/>
			<input id="LoginButton" class="btn btn-primary btn-large" type="submit" value="Login" /> <br/>
		</form>	
		
		<br/>
		
		<form action="Register.php" method="POST">				<!-- TO DO: replace Register.php with actual name of page -->
			Not a member? ...  <br/>
			<input id="Register" class="btn btn-primary btn-large" type="submit" value="Register!" />  <br/>
		</form>
	</body>
	
</html>








	





