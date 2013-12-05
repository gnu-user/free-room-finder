package com.uoit.freeroomfinder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "free_rooms.db";
	
	private static final int DATABASE_VERSION = 1;
	
	/* Table Name */
	public static final String USER_TABLE_NAME = "user";
	
    private static final String EXISTS_CLAUSE = "IF NOT EXISTS";
    
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
	
    private static final String USER_TABLE_CREATE =
    		"CREATE TABLE " + EXISTS_CLAUSE + " " + USER_TABLE_NAME + 
    		" (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," +
    		" " + KEY_USERNAME + " TEXT," +
    		" " + KEY_PASSWORD + " TEXT);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USER_TABLE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
		onCreate(db);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);		
		if (!db.isReadOnly())
		{
			//Make so that foreign keys are used
			//Not necessary for this schema but very useful since it can be easily forgotten.
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}
}
