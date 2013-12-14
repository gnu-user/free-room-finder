package com.uoit.freeroomfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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

		//TODO ensure that there arnt to many rooms
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
	
	public void deleteBookings() {
		// Delete the oldest bookings until there are less then the maximum preference
		/*context.getContentResolver().delete(DatabaseProvider
				.BOOKING_CONTENT_URI, null, null);*/
	}

	
	public Rooms getBooking(long id) {
		Cursor cur = context.getContentResolver().query(DatabaseProvider.BOOKING_CONTENT_URI, 
				new String[] { SQLiteHelper.KEY_USERNAME,
				SQLiteHelper.KEY_PASSWORD }, SQLiteHelper.KEY_ID + " = " + id, null, null);

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
	
}
