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

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.preference.PreferenceManager;

/**
 * DatabaseInterface Class - Provides methods for accessing external data on the FRF database.
 * 
 * @author Joseph Heron
 * @author Jonathan Gillett
 * @author Daniel Smullen
 * 
 */
public class DatabaseInterface
{
    /**
     * Stores the context for the database access.
     */
    private Context context;

    /**
     * Default constructor
     * 
     * @param context
     *            The context the db is in.
     */
    public DatabaseInterface(Context context)
    {
        this.context = context;
    }

    /**
     * insertUser Inserts the specified user's credentials into the database. Calling this will
     * delete any other user in the database first.
     * 
     * @param user
     *            The user's credentials to add.
     */
    public void insertUser(User user)
    {
        deleteUser();
        ContentValues cv = new ContentValues();

        cv.put(SQLiteHelper.KEY_USERNAME, user.getUsername());
        cv.put(SQLiteHelper.KEY_PASSWORD, user.getPassword());

        context.getContentResolver().insert(DatabaseProvider.USER_CONTENT_URI, cv);
    }

    /**
     * deleteUser Delete all users in the local database.
     */
    public void deleteUser()
    {
        context.getContentResolver().delete(DatabaseProvider.USER_CONTENT_URI, null, null);
    }

    /**
     * getUser Get the current user from the local database.
     * 
     * @return user The current user's credentials. Returns null if there's no user.
     */
    public User getUser()
    {
        Cursor cur = context.getContentResolver().query(DatabaseProvider.USER_CONTENT_URI,
                new String[] { SQLiteHelper.KEY_USERNAME, SQLiteHelper.KEY_PASSWORD }, null, null,
                null);

        User user = null;

        if (cur.moveToFirst())
        {
            user = new User(cur.getString(cur.getColumnIndex(SQLiteHelper.KEY_USERNAME)),
                    cur.getString(cur.getColumnIndex(SQLiteHelper.KEY_PASSWORD)));
        }
        cur.close();
        return user;
    }

    /**
     * insertBooking Inserts a room booking into the local database. Trims the existing bookings
     * first.
     * 
     * @param room
     *            The room booking to book.
     */
    public void insertBooking(Rooms room)
    {
        deleteBookings();
        ContentValues cv = new ContentValues();

        cv.put(SQLiteHelper.KEY_ROOM_NAME, room.getRoom());
        cv.put(SQLiteHelper.KEY_START_TIME, room.getStartTime());
        cv.put(SQLiteHelper.KEY_END_TIME, room.getEndTime());
        cv.put(SQLiteHelper.KEY_BOOK_DATE, room.getDate());

        context.getContentResolver().insert(DatabaseProvider.BOOKING_CONTENT_URI, cv);
    }

    /**
     * deleteBooking Delete a room booking from the local database.
     * 
     * @param id
     *            The id of the room booking to delete.
     */
    public void deleteBooking(long id)
    {
        context.getContentResolver().delete(DatabaseProvider.BOOKING_CONTENT_URI,
                SQLiteHelper.KEY_ID + " = " + id, null);
    }

    /**
     * deleteAllBooking Clear all of the room bookings from the local database.
     */
    public void deleteAllBooking()
    {
        context.getContentResolver().delete(DatabaseProvider.BOOKING_CONTENT_URI, null, null);
    }

    /**
     * deleteBookings Trim the limited set of room bookings. This is done by deleting enough of the
     * older room bookings to match the maximum number of room bookings specified in the settings.
     */
    public void deleteBookings()
    {

        int maxValue = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context)
                .getString("max_rooms", "50"));
        int difference = getBookedRowCount() - maxValue;

        if (difference > 0)
        {
            String innerQuery = SQLiteHelper.KEY_ID + " = (SELECT " + SQLiteHelper.KEY_ID
                    + " FROM " + SQLiteHelper.BOOKING_TABLE_NAME + " ORDER BY "
                    + SQLiteHelper.KEY_BOOK_DATE + " DESC LIMIT " + difference + ")";

            // Start the deleting
            // Delete the oldest bookings until there are less then the maximum preference
            context.getContentResolver().delete(DatabaseProvider.BOOKING_CONTENT_URI, innerQuery,
                    null);
        }
    }

    /**
     * getBookedRowCount Get the current number of bookings from the local database.
     * 
     * @return value The current number of bookings stored in the database.
     */
    private int getBookedRowCount()
    {

        Cursor cur = context.getContentResolver().query(DatabaseProvider.BOOKING_CONTENT_URI,
                new String[] { "COUNT(" + SQLiteHelper.KEY_ID + ")" }, null, null, null);

        int value = -1;

        if (cur.moveToFirst())
        {
            value = cur.getInt(0);
        }
        cur.close();

        return value;
    }

    /**
     * getBooking Get a specific room booking from the local database.
     * 
     * @param id
     *            The id of the booking to retrieve from the local database.
     * 
     * @return booking The room booking. Returns null if the booking does not exist.
     */
    public Rooms getBooking(long id)
    {
        Cursor cur = context.getContentResolver().query(
                DatabaseProvider.BOOKING_CONTENT_URI,
                new String[] { SQLiteHelper.KEY_ROOM_NAME, SQLiteHelper.KEY_START_TIME,
                        SQLiteHelper.KEY_END_TIME, SQLiteHelper.KEY_BOOK_DATE },
                SQLiteHelper.KEY_ID + " = " + id, null, null);

        Rooms booking = null;

        if (cur.moveToFirst())
        {
            booking = new Rooms(cur.getString(cur.getColumnIndex(SQLiteHelper.KEY_ROOM_NAME)),
                    cur.getLong(cur.getColumnIndex(SQLiteHelper.KEY_START_TIME)), cur.getLong(cur
                            .getColumnIndex(SQLiteHelper.KEY_END_TIME)), cur.getLong(cur
                            .getColumnIndex(SQLiteHelper.KEY_BOOK_DATE)));
        }
        cur.close();
        return booking;
    }

    /**
     * getBooking Get all the of room bookings from the local database.
     * 
     * @return booking Returns an ArrayList of Rooms, containing the list of room bookings.
     */
    public ArrayList<Rooms> getBooking()
    {
        Cursor cur = context.getContentResolver().query(
                DatabaseProvider.BOOKING_CONTENT_URI,
                new String[] { SQLiteHelper.KEY_ID, SQLiteHelper.KEY_ROOM_NAME, 
        		SQLiteHelper.KEY_START_TIME, SQLiteHelper.KEY_END_TIME,
        		SQLiteHelper.KEY_BOOK_DATE }, null, null, null);

        ArrayList<Rooms> booking = new ArrayList<Rooms>();

        if (cur.moveToFirst())
        {

            do
            {
                booking.add(new Rooms(
					cur.getLong(cur.getColumnIndex(SQLiteHelper.KEY_ID)),
					cur.getString(cur.getColumnIndex(SQLiteHelper.KEY_ROOM_NAME)),
					cur.getLong(cur.getColumnIndex(SQLiteHelper.KEY_START_TIME)),
					cur.getLong(cur.getColumnIndex(SQLiteHelper.KEY_END_TIME)),
					cur.getLong(cur.getColumnIndex(SQLiteHelper.KEY_BOOK_DATE))));
            } while (cur.moveToNext());
        }
        cur.close();
        return booking;
    }

    /**
     * deleteAll Delete all the data in the local database.
     */
    public void deleteAll()
    {
        deleteAllBooking();
        deleteUser();
    }
}
