package com.uoit.freeroomfinder;

import java.util.Date;

import android.text.format.Time;

public class Request {
	
	private long time;
	private int duration;
	private Date date;
	private int campus;
	
	public Request(long time, int duration, Date date, int campus)
	{
		this.time = time;
		this.duration = duration;
		this.date = date;
		this.campus = campus;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
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
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
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

}
