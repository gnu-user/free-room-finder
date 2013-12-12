package com.uoit.freeroomfinder;

import java.util.regex.Pattern;

public class User {

	private String username;
	private String password;

	public User() {
		username = "";
		password = "";
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean validUsername() {

		if (username != null
				&& Pattern.matches("^[a-zA-Z][a-zA-Z0-9_]{6,31}$", username)) {
			return true;
		}

		return false;
	}

	public boolean validPassword() {
		if (password != null
				&& Pattern.matches(
						"^[a-zA-Z0-9\\!\\$\\%\\^\\&\\*\\(\\)\\_\\?]{6,31}$",
						password)) {
			return true;
		}

		return false;
	}

	public boolean equals(User otherUser) {

		if (this.password.equals(otherUser.password)
				&& this.username.equals(otherUser.username)) {
			return true;
		}

		return false;

	}

}
