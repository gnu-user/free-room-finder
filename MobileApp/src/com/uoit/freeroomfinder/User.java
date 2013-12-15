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
