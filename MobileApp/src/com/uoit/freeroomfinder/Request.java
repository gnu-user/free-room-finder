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

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;


public abstract class Request
{
    /* Formatter for the time from the REST api */
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat tf = new SimpleDateFormat("hh:mm:ss");
    
	private static final String API_URL = "http://cs-club.ca/free-room-website/api";

	
	/* Static class representing the JSON encoded login credentials */
    private static class Credentials
    {
        boolean credentials = false;
    }
    	
	/* Static class representing the JSON encoded rooms */
	private static class Room
	{
	    String room;
	    String starttime;
	    String endtime;
	}
	
	/* REST API interface for getting login credentials */
	private static interface LoginCredentials
	{
	    @GET("/login/{username}/{password}")
	    Credentials loginCredentials(
	        @Path("username") String username,
	        @Path("password") String password
        );
	}
	

	/* REST API interface for searching for rooms */
	private static interface AvailableRooms
	{
	    @GET("/availablerooms/{time}/{date}/{campus}/{duration}")
	    List<Room> availableRooms(
	        @Path("time") String time,
	        @Path("date") String date,
	        @Path("campus") String campus,
	        @Path("duration") int duration
	    );
	}
	
	
	/**
	 * Validates the login credentials of the user.
	 * 
	 * @param username The user's username
	 * @param password The user's password
	 * @return TRUE if the credentials are valid, FALSE otherwise
	 */
	public static boolean validateCredentials(String username, String password)
	{
	    Credentials credentials = new Credentials();
	    
	    RestAdapter restAdapter = new RestAdapter.Builder()
	        .setServer(API_URL)
	        .build();
	    
	    try
	    {
	        /* Create an instance of the REST API interface */
	        LoginCredentials loginCredentials = restAdapter.create(LoginCredentials.class);
	        
	        /* Validate the credentials */   
	        credentials = loginCredentials.loginCredentials(username, password);        
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    
	    return credentials.credentials;
	}
	
	
	/**
     * Simplified interface to search for the rooms using the REST API.
     * 
	 * @param time The time that a room is needed
	 * @param date The date that a room is needed
	 * @param campus The campus the room is needed on
	 * @param duration The length of time the room is needed for
	 * 
	 * @return The list of available rooms
	 */
	public static ArrayList<Rooms> searchRooms(String time, String date, String campus, int duration)
	{	    
	    ArrayList<Rooms> results = new ArrayList<Rooms>();
	    
	    /* Create a REST adapter which points the Free Room finder API */
	    RestAdapter restAdapter = new RestAdapter.Builder()
	        .setServer(API_URL)
	        .build();
	    	    
	    try
	    {
	        /* Create an instance of the REST API interface */
	        AvailableRooms availableRooms = restAdapter.create(AvailableRooms.class);
	        
	        /* Search for available rooms */
	        List<Room> rooms = availableRooms.availableRooms(time, date, campus, duration);
	        for (Room room : rooms)
	        {
	            results.add(new Rooms(
	                    room.room, 
	                    tf.parse(room.starttime).getTime(), 
	                    tf.parse(room.endtime).getTime()
                ));
	        }
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }   
   
	    return results;
	}
}
