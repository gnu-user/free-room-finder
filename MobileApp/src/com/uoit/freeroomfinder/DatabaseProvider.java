package com.uoit.freeroomfinder;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class DatabaseProvider extends ContentProvider {
	public static final String AUTHORITY = "com.uoit.freeroomfinder.provider";
	public static final String BASE_PATH_USER = "user";
	public static final String BASE_PATH_BOOKING = "user";
	
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + SQLiteHelper.USER_TABLE_NAME);
	HashSet<String> validUserRows = new HashSet<String>(Arrays.asList(new String[] {
			SQLiteHelper.KEY_ID, SQLiteHelper.KEY_USERNAME, SQLiteHelper.KEY_PASSWORD }));
	HashSet<String> validBookingRows = new HashSet<String>(Arrays.asList(new String[] {
			SQLiteHelper.KEY_ID, SQLiteHelper.KEY_USERNAME, SQLiteHelper.KEY_PASSWORD }));
	

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/users";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/user";

	private SQLiteHelper db;
	private SQLiteDatabase dba;
	
	public static final int USER_MATCH = 1;
	public static final int BOOKING_MATCH = 2;
	
	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, SQLiteHelper.USER_TABLE_NAME, USER_MATCH);
		sURIMatcher.addURI(AUTHORITY, SQLiteHelper.BOOKING_TABLE_NAME, BOOKING_MATCH);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		int choose = sURIMatcher.match(uri);

		switch (choose) {
		case USER_MATCH:
			dba = db.getWritableDatabase();
			int count = dba.delete(SQLiteHelper.USER_TABLE_NAME, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
			return count;
		case BOOKING_MATCH:
			dba = db.getWritableDatabase();
			count = dba.delete(SQLiteHelper.BOOKING_TABLE_NAME, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
			return count;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int choose = sURIMatcher.match(uri);
		long id = -1;
		switch (choose) {
		case USER_MATCH:
			dba = db.getWritableDatabase();
			id = dba.insert(SQLiteHelper.USER_TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_USER + "/" + id);
			
		case BOOKING_MATCH:
			dba = db.getWritableDatabase();
			id = dba.insert(SQLiteHelper.BOOKING_TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_BOOKING + "/" + id);
		
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public boolean onCreate() {
		db = new SQLiteHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		// proper SQL syntax for us.
		SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();

		
		// If the query ends in a specific record number, we're
		// being asked for a specific record, so set the
		// WHERE clause in our query.
		Cursor c = null;
		
		int choose = sURIMatcher.match(uri);
		switch (choose) {
		case USER_MATCH:
			
			checkColumns(projection, SQLiteHelper.USER_TABLE_NAME);

			// Set the table we're querying.
			qBuilder.setTables(SQLiteHelper.USER_TABLE_NAME);
		
			c = qBuilder.query(db.getWritableDatabase(), projection,
					selection, selectionArgs, "", "", sortOrder);
			c.setNotificationUri(getContext().getContentResolver(), uri);
			return c;
			
		case BOOKING_MATCH:
			checkColumns(projection, SQLiteHelper.BOOKING_TABLE_NAME);

			// Set the table we're querying.
			qBuilder.setTables(SQLiteHelper.BOOKING_TABLE_NAME);
		
			c = qBuilder.query(db.getWritableDatabase(), projection,
					selection, selectionArgs, "", "", sortOrder);
			c.setNotificationUri(getContext().getContentResolver(), uri);
			return c;
		
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int choose = sURIMatcher.match(uri);
		int count = -1;
		switch (choose) {
		case USER_MATCH:
			dba = db.getWritableDatabase();
			count = dba.update(SQLiteHelper.USER_TABLE_NAME, values, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
			return count;
		case BOOKING_MATCH:
			dba = db.getWritableDatabase();
			count = dba.update(SQLiteHelper.BOOKING_TABLE_NAME, values, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
			return count;
			
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}
	
	/**
	 * Ensure that the columns given are valid columns in the database. Otherwise throw an error.
	 * @param columns The columns given as the projection.
	 */
	public void checkColumns(String[] columns, String table) {
		if (columns != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(columns));
			if(table.equals(SQLiteHelper.USER_TABLE_NAME))
			{
				if (!validUserRows.containsAll(requestedColumns)) {
					throw new IllegalArgumentException(
							"Unknown columns in projection");
				}
			}
			else if(table.equals(SQLiteHelper.USER_TABLE_NAME))
			{
				if (!validBookingRows.containsAll(requestedColumns)) {
					throw new IllegalArgumentException(
							"Unknown columns in projection");
				}
			}
			else
			{
				throw new IllegalArgumentException(
						"Unknown table");
			}
			
		}
	}
}
