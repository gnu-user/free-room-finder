package com.uoit.freeroomfinder;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import android.util.Log;


public class Request {
	
	private String time;
	private int duration;
	private String date;
	private int campus;
	
	private static final String API_URL = "http://cs-club.ca/free-room-website/api";
	
	static class Profs
	{
	    String professor;
	    String studentNum;
	}
	
	
	interface BusyProfs
	{
	    @GET("/busyprofs/{count}")
	    List<Profs> profStudents(
	        @Path("count") int count
	    );
	}
	
	//Pass the date and time as formated strings
	public Request(String time, int duration, String date, int campus)
	{
		this.time = time;
		this.duration = duration;
		this.date = date;
		this.campus = campus;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the campus
	 */
	public int getCampus() {
		return campus;
	}

	/**
	 * @param campus the campus to set
	 */
	public void setCampus(int campus) {
		this.campus = campus;
	}

	
	public void getBusyProfs(int count)
	{	    
	    // Create a very simple REST adapter which points the GitHub API endpoint.
	    RestAdapter restAdapter = new RestAdapter.Builder()
	        .setServer(API_URL)
	        .build();
	    
	    // Create an instance of our GitHub API interface.
	    BusyProfs busyProfs = restAdapter.create(BusyProfs.class);

	    // Fetch and print a list of the contributors to this library.
	    List<Profs> profs = busyProfs.profStudents(count);
	    for (Profs prof : profs)
	    {
	        Log.v("REST", prof.professor + " (" + prof.studentNum + ")");
	    }
	}
}
