
<html>
	<head>
		<title> Welcome to the free-room-finder! </title>
	</head>
	
	<body>
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
	</body>
	
</html>





	





