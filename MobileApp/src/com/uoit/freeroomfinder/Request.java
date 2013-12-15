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


public class Request {
	
	private String time;
	private String date;
	private String campus;
	private int duration;

    /* Formatter for the time from the REST api */
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat tf = new SimpleDateFormat("hh:mm:ss");
    
	private static final String API_URL = "http://cs-club.ca/free-room-website/api";

	/* Static class representing the JSON encoded rooms */
	static class Room
	{
	    String room;
	    String starttime;
	    String endtime;
	}
	
	/* Rest API interface, used for making the GET requests */
	interface AvailableRooms
	{
	    @GET("/availablerooms/{time}/{date}/{campus}/{duration}")
	    List<Room> availableRooms(
	        @Path("time") String time,
	        @Path("date") String date,
	        @Path("campus") String campus,
	        @Path("duration") int duration
	    );
	}
	
	/* Pass the date and time as formated strings */
	public Request(String time, String date, String campus, int duration)
	{
		this.time = time;
		this.date = date;
		this.campus = campus;
        this.duration = duration;
	}

	/**
	 * Get the time
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Set the time
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Get the duration
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Set the duration
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Get the date
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Set the date
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Get the campus
	 * @return the campus
	 */
	public String getCampus() {
		return campus;
	}

	/**
	 * Set the campus
	 * @param campus the campus to set
	 */
	public void setCampus(String campus) {
		this.campus = campus;
	}

	
	/**
	 * Search for the rooms using the api
	 * @return The list of rooms.
	 */
	public ArrayList<Rooms> searchRooms()
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
