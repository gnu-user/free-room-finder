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

		context.getContentResolver().insert(DatabaseProvider.CONTENT_URI, cv);
	}

	public void deleteUser() {
		// Delete all rows
		context.getContentResolver().delete(DatabaseProvider.CONTENT_URI, null,
				null);
	}

	public User getUser() {
		Cursor cur = context.getContentResolver().query(DatabaseProvider.CONTENT_URI, 
				new String[] { SQLiteHelper.KEY_USERNAME,
				SQLiteHelper.KEY_USERNAME }, null, null, null);

		User user = null;

		if (cur.moveToFirst()) {
			user = new User(
					cur.getString(cur.getColumnIndex(SQLiteHelper.KEY_USERNAME)),
					cur.getString(cur.getColumnIndex(SQLiteHelper.KEY_PASSWORD)));
		}
		cur.close();
		return user;
	}

}
