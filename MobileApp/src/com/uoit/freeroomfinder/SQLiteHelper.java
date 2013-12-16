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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteHelper A helper class for interacting with the internal SQLite database.
 * 
 * @author Jonathan Gillett
 * @author Daniel Smullen
 * @author Joseph Heron
 * 
 */
public class SQLiteHelper extends SQLiteOpenHelper
{

    /**
     * Stores the database name for the app.
     */
    private static final String DATABASE_NAME = "free_rooms.db";

    /**
     * Stores the database version string.
     */
    private static final int DATABASE_VERSION = 2;

    /**
     * Internal database table names.
     */
    public static final String USER_TABLE_NAME = "user";
    public static final String BOOKING_TABLE_NAME = "bookings";

    private static final String EXISTS_CLAUSE = "IF NOT EXISTS";

    /**
     * Keys for persistently stored values (user names and passwords for database access).
     */
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    /**
     * Keys for persistently stored values (sending booking data between fragments).
     */
    public static final String KEY_ROOM_NAME = "room_name";
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_BOOK_DATE = "book_date";

    /**
     * The SQL query to create the user data table.
     */
    private static final String USER_TABLE_CREATE = "CREATE TABLE " + EXISTS_CLAUSE + " "
            + USER_TABLE_NAME + " (" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," + " " + KEY_USERNAME + " TEXT,"
            + " " + KEY_PASSWORD + " TEXT);";

    /**
     * The SQL query to create the booking data table.
     */
    private static final String BOOKING_TABLE_CREATE = "CREATE TABLE " + EXISTS_CLAUSE + " "
            + BOOKING_TABLE_NAME + " (" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," + " " + KEY_ROOM_NAME
            + " TEXT," + " " + KEY_START_TIME + " INTEGER," + " " + KEY_END_TIME + " INTEGER,"
            + " " + KEY_BOOK_DATE + " INTEGER);";

    /**
     * Default constructor.
     * 
     * @param context The context for the activity.
     */
    public SQLiteHelper(Context context)
    {
        //Default implementation. Calls the superclass constructor.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Execute the creation statements for the tables.
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(BOOKING_TABLE_CREATE);

    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Delete the old tables and recreate everything on upgrade.
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE_NAME);
        onCreate(db);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onOpen(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        if (!db.isReadOnly())
        {
            // Make it so that foreign keys are used.
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
