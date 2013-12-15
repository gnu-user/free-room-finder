/**
 * Free Room Finder (FRF)
 * Tired of rooms on campus always being in use? Fear no more the FRF is here.
 *
 * Copyright (C) 2013 Joseph Heron, Jonathan Gillett, and Daniel Smullen
 * All rights reserved.
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.uoit.freeroomfinder;

import java.util.regex.Pattern;

public class User {

	private String username;
	private String password;

	/**
	 * Create an empty user
	 */
	public User() {
		username = "";
		password = "";
	}

	/**
	 * Create a new user
	 * @param username the username
	 * @param password the password
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Get the username
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the password
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the password
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Ensure the username is valid
	 * @return Whether the user name is valid or not.
	 */
	public boolean validUsername() {

		if (username != null
				&& Pattern.matches("^[a-zA-Z][a-zA-Z0-9_]{6,31}$", username)) {
			return true;
		}

		return false;
	}

	/**
	 * Ensure the password is valid
	 * @return Whether the password is valid or not.
	 */
	public boolean validPassword() {
		if (password != null
				&& Pattern.matches(
						"^[a-zA-Z0-9\\!\\$\\%\\^\\&\\*\\(\\)\\_\\?]{6,31}$",
						password)) {
			return true;
		}

		return false;
	}

	/**
	 * Check if the given User equals the user.
	 * @param otherUser Another user
	 * @return Whether the two users are equal.
	 */
	public boolean equals(User otherUser) {

		if (this.password.equals(otherUser.password)
				&& this.username.equals(otherUser.username)) {
			return true;
		}

		return false;

	}

}
