package com.uoit.freeroomfinder;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.preference.PreferenceManager;

public class DatabaseInterface {

	private Context context;

	public DatabaseInterface(Context context) {
		this.context = context;
	}

	public void insertUser(User user) {

		deleteUser();
		ContentValues cv = new ContentValues();

		cv.put(SQLiteHelper.KEY_USERNAME, user.getUsername());
		cv.put(SQLiteHelper.KEY_PASSWORD, user.getPassword());

		context.getContentResolver().insert(DatabaseProvider.USER_CONTENT_URI, cv);
	}

	public void deleteUser() {
		// Delete all rows
		context.getContentResolver().delete(DatabaseProvider.USER_CONTENT_URI, null, 
				null);
	}

	public User getUser() {
		Cursor cur = context.getContentResolver().query(DatabaseProvider.USER_CONTENT_URI, 
				new String[] { SQLiteHelper.KEY_USERNAME,
				SQLiteHelper.KEY_PASSWORD }, null, null, null);

		User user = null;

		if (cur.moveToFirst()) {
			user = new User(
					cur.getString(cur.getColumnIndex(SQLiteHelper.KEY_USERNAME)),
					cur.getString(cur.getColumnIndex(SQLiteHelper.KEY_PASSWORD)));
		}
		cur.close();
		return user;
	}
	
	public void insertBooking(Rooms room) {

		deleteBookings();
		ContentValues cv = new ContentValues();

		cv.put(SQLiteHelper.KEY_ROOM_NAME, room.getRoom());
		cv.put(SQLiteHelper.KEY_START_TIME, room.getStartTime());
		cv.put(SQLiteHelper.KEY_END_TIME, room.getEndTime());
		cv.put(SQLiteHelper.KEY_BOOK_DATE, room.getDate());

		context.getContentResolver().insert(DatabaseProvider.BOOKING_CONTENT_URI, cv);
	}

	
	public void deleteBooking(long id) {
		context.getContentResolver().delete(DatabaseProvider
				.BOOKING_CONTENT_URI, SQLiteHelper.KEY_ID + " = " + id, 
				null);
	}
	
	public void deleteAllBooking() {
		context.getContentResolver().delete(DatabaseProvider
				.BOOKING_CONTENT_URI, null, null);
	}
	
	public void deleteBookings() {
	
		int maxValue = Integer.valueOf(PreferenceManager
				.getDefaultSharedPreferences(context).getString("max_rooms", "50"));
		int difference = getBookedRowCount() - maxValue;
		
		if(difference > 0)
		{
			String innerQuery = SQLiteHelper.KEY_ID + " = (SELECT " + SQLiteHelper.KEY_ID + " FROM "
					+ SQLiteHelper.BOOKING_TABLE_NAME + " ORDER BY " + SQLiteHelper.KEY_BOOK_DATE
					+ " DESC LIMIT " + difference + ")";
			
			//Start the deleting!!!!
			// Delete the oldest bookings until there are less then the maximum preference
			context.getContentResolver().delete(DatabaseProvider
					.BOOKING_CONTENT_URI, innerQuery, null);
		}
	}
	
	private int getBookedRowCount()	{
		
		Cursor cur = context.getContentResolver().query(DatabaseProvider.BOOKING_CONTENT_URI, 
				new String[] { "COUNT("+SQLiteHelper.KEY_ID +")" },
				null, null, null);

		int value = -1;

		if (cur.moveToFirst()) {
			value = cur.getInt(0);
		}
		cur.close();
		
		return value;
	}

	
	public Rooms getBooking(long id) {
		Cursor cur = context.getContentResolver().query(DatabaseProvider.BOOKING_CONTENT_URI, 
				new String[] { SQLiteHelper.KEY_ROOM_NAME, SQLiteHelper.KEY_START_TIME,
				SQLiteHelper.KEY_END_TIME, SQLiteHelper.KEY_BOOK_DATE},
				SQLiteHelper.KEY_ID + " = " + id, null, null);

		Rooms booking = null;

		if (cur.moveToFirst()) {
			booking = new Rooms(
					cur.getString(cur.getColumnIndex(SQLiteHelper.KEY_ROOM_NAME)),
					cur.getLong(cur.getColumnIndex(SQLiteHelper.KEY_START_TIME)),
					cur.getLong(cur.getColumnIndex(SQLiteHelper.KEY_END_TIME)),
					cur.getLong(cur.getColumnIndex(SQLiteHelper.KEY_BOOK_DATE)));
		}
		cur.close();
		return booking;
	}
	
	public ArrayList<Rooms> getBooking() {
		Cursor cur = context.getContentResolver().query(DatabaseProvider.BOOKING_CONTENT_URI, 
				new String[] { SQLiteHelper.KEY_ROOM_NAME, SQLiteHelper.KEY_START_TIME,
				SQLiteHelper.KEY_END_TIME, SQLiteHelper.KEY_BOOK_DATE}, null, null, null);

		ArrayList<Rooms> booking = new ArrayList<Rooms>();

		if (cur.moveToFirst()) {
			
			do
			{
				booking.add(new Rooms(
					cur.getString(cur.getColumnIndex(SQLiteHelper.KEY_ROOM_NAME)),
					cur.getLong(cur.getColumnIndex(SQLiteHelper.KEY_START_TIME)),
					cur.getLong(cur.getColumnIndex(SQLiteHelper.KEY_END_TIME)),
					cur.getLong(cur.getColumnIndex(SQLiteHelper.KEY_BOOK_DATE))));
			}while(cur.moveToNext());
		}
		cur.close();
		return booking;
	}
	
	public void deleteAll() {
		deleteAllBooking();
		deleteUser();
	}
}
