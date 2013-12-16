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

/**
 * User Stores attributes and provides methods for users.
 * 
 * @author Joseph Heron
 * @author Jonathan Gillett
 * @author Daniel Smullen
 */
public class User
{
    /**
     * Class attributes for storing the user's user name and password.
     */
    private String username;
    private String password;

    /**
     * Default constructor. Create an empty user.
     */
    public User()
    {
        username = "";
        password = "";
    }

    /**
     * Override constructor. Create a new user with the supplied parameters.
     * 
     * @param username The user name for the new user.
     * @param password The password for the new user.
     */
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    /**
     * getUsername Accessor method for the user's user name.
     * 
     * @return Returns the user's user name, as a string.
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * setUsername Mutator method for the user's user name.
     * 
     * @param username The user name to set.
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * getPassword Accessor method for the user's password.
     * 
     * @return Returns the password for the user, as a plain text string
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * setPassword Mutator method for the user's password.
     * 
     * @param password The password to set the user's password to.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * validUsername Ensures the user name is valid.
     * 
     * @return Returns true if the user name is valid.
     */
    public boolean validUsername()
    {
        // Match the user name to a regex to determine if it's valid. Escaped characters are not
        // allowed.
        if (username != null && Pattern.matches("^[a-zA-Z][a-zA-Z0-9_]{6,31}$", username))
        {
            return true;
        }

        return false;
    }

    /**
     * validPassword Ensures the supplied password is valid.
     * 
     * @return Returns true if the password is valid.
     */
    public boolean validPassword()
    {
        // Match the password to a regex to determine if it's valid. Escaped characters are not
        // allowed.
        if (password != null
                && Pattern.matches("^[a-zA-Z0-9\\!\\$\\%\\^\\&\\*\\(\\)\\_\\?]{6,31}$", password))
        {
            return true;
        }

        return false;
    }

    /**
     * equals Check if the given user is the same as another user by comparing their names and
     * passwords.
     * 
     * @param otherUser The user to check against.
     * 
     * @return Returns true if the two users are the same.
     */
    public boolean equals(User otherUser)
    {
        if (this.password.equals(otherUser.password) && this.username.equals(otherUser.username))
        {
            return true;
        }

        return false;
    }
}
