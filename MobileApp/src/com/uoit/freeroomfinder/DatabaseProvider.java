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

/**
 * DatabaseProvider A ContentProvider child class which provides a content provider to facilitate
 * access to the internal database.
 * 
 * @author Joseph Heron
 * @author Jonathan Gillett
 * @author Daniel Smullen
 * 
 */
public class DatabaseProvider extends ContentProvider
{
    /**
     * The fully qualified name of the authority.
     */
    public static final String AUTHORITY = "com.uoit.freeroomfinder.provider";
    /**
     * The name extension for the user database.
     */
    public static final String BASE_PATH_USER = "user";
    /**
     * The name extension for the booking database.
     */
    public static final String BASE_PATH_BOOKING = "booking";

    /**
     * The URI for the user-related database content.
     */
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
            + SQLiteHelper.USER_TABLE_NAME);
    /**
     * The URI for the booking-related database content.
     */
    public static final Uri BOOKING_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
            + SQLiteHelper.BOOKING_TABLE_NAME);

    /**
     * Stores the valid rows associated with a user.
     */
    HashSet<String> validUserRows = new HashSet<String>(Arrays.asList(new String[] {
            SQLiteHelper.KEY_ID, SQLiteHelper.KEY_USERNAME, SQLiteHelper.KEY_PASSWORD }));
    /**
     * Stores the valid rows associated with bookings.
     */
    HashSet<String> validBookingRows = new HashSet<String>(Arrays.asList(new String[] {
            SQLiteHelper.KEY_ID, "COUNT(" + SQLiteHelper.KEY_ID + ")", SQLiteHelper.KEY_ROOM_NAME,
            SQLiteHelper.KEY_START_TIME, SQLiteHelper.KEY_END_TIME, SQLiteHelper.KEY_BOOK_DATE }));

    /**
     * The content type identifier for user related data.
     */
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/users";
    
    /**
     * The content type identifier for a single user.
     */
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/user";

    /**
     * A helper object for accessing the SQLite database.
     */
    private SQLiteHelper db;
    
    /**
     * A helper object for storing the SQLite database instance.
     */
    private SQLiteDatabase dba;

    /**
     * A constant used to match user-related database entries.
     */
    public static final int USER_MATCH = 1;
    
    /**
     * A constant used to match booking-related database entries.
     */
    public static final int BOOKING_MATCH = 2;

    /**
     * A URI matcher object to associate URIs with pertinent data types.
     */
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static
    {
        sURIMatcher.addURI(AUTHORITY, SQLiteHelper.USER_TABLE_NAME, USER_MATCH);
        sURIMatcher.addURI(AUTHORITY, SQLiteHelper.BOOKING_TABLE_NAME, BOOKING_MATCH);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String,
     * java.lang.String[])
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {

        int choose = sURIMatcher.match(uri);
        
        // Delete either user data, or booking data.
        switch (choose)
        {
        case USER_MATCH:
            // Delete user data.
            dba = db.getWritableDatabase();
            int count = dba.delete(SQLiteHelper.USER_TABLE_NAME, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        case BOOKING_MATCH:
            // Delete booking data.
            dba = db.getWritableDatabase();
            count = dba.delete(SQLiteHelper.BOOKING_TABLE_NAME, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#getType(android.net.Uri)
     */
    @Override
    public String getType(Uri uri)
    {
        // Not implemented.
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
     */
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        int choose = sURIMatcher.match(uri);
        long id = -1;
        
        // Insert either booking data, or user data.
        switch (choose)
        {
        case USER_MATCH:
            // Insert user data.
            dba = db.getWritableDatabase();
            id = dba.insert(SQLiteHelper.USER_TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse(BASE_PATH_USER + "/" + id);

        case BOOKING_MATCH:
            // Insert bookng data.
            dba = db.getWritableDatabase();
            id = dba.insert(SQLiteHelper.BOOKING_TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse(BASE_PATH_BOOKING + "/" + id);

        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#onCreate()
     */
    @Override
    public boolean onCreate()
    {
        // Default implementation.
        db = new SQLiteHelper(getContext());
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[],
     * java.lang.String, java.lang.String[], java.lang.String)
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder)
    {
        // Create proper SQL syntax for our database.
        SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();

        // If the query ends in a specific record number, we're
        // being asked for a specific record, so set the
        // WHERE clause in our query.
        Cursor c = null;

        int choose = sURIMatcher.match(uri);
        
        // Query for either user data or booking data.
        switch (choose)
        {
        case USER_MATCH:
            // Query for user data.
            checkColumns(projection, SQLiteHelper.USER_TABLE_NAME);

            // Set the table we're querying.
            qBuilder.setTables(SQLiteHelper.USER_TABLE_NAME);

            c = qBuilder.query(db.getWritableDatabase(), projection, selection, selectionArgs, "",
                    "", sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;

        case BOOKING_MATCH:
            // Query for booking data.
            checkColumns(projection, SQLiteHelper.BOOKING_TABLE_NAME);

            // Set the table we're querying.
            qBuilder.setTables(SQLiteHelper.BOOKING_TABLE_NAME);

            c = qBuilder.query(db.getWritableDatabase(), projection, selection, selectionArgs, "",
                    "", sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;

        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues,
     * java.lang.String, java.lang.String[])
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        int choose = sURIMatcher.match(uri);
        int count = -1;
        
        // Update either user data or booking data.
        switch (choose)        
        {
        case USER_MATCH:
            // Update user data rows.
            dba = db.getWritableDatabase();
            count = dba.update(SQLiteHelper.USER_TABLE_NAME, values, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
            
        case BOOKING_MATCH:
            // Update booking data rows.
            dba = db.getWritableDatabase();
            count = dba.update(SQLiteHelper.BOOKING_TABLE_NAME, values, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return count;

        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    /**
     * checkColumns Ensure that the columns given are valid columns in the database. Otherwise throw
     * an error.
     * 
     * @param columns The columns given as the projection.
     */
    public void checkColumns(String[] columns, String table)
    {
        if (columns != null)
        {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(columns));
            if (table.equals(SQLiteHelper.USER_TABLE_NAME))
            {
                if (!validUserRows.containsAll(requestedColumns))
                {
                    throw new IllegalArgumentException("Unknown columns in projection");
                }
            }
            else if (table.equals(SQLiteHelper.BOOKING_TABLE_NAME))
            {
                if (!validBookingRows.containsAll(requestedColumns))
                {
                    throw new IllegalArgumentException("Unknown columns in projection");
                }
            }
            else
            {
                throw new IllegalArgumentException("Unknown table");
            }
        }
    }
}
