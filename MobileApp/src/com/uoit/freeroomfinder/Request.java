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
import java.util.List;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Request An abstract class for formulating requests to the external web data.
 * 
 * @author Joseph Heron
 * @author Jonathan Gillett
 * @author Daniel Smullen
 * 
 */
public abstract class Request
{
    /**
     * The URL for the back-end API.
     */
    private static final String API_URL = "http://cs-club.ca/free-room-website/api";

    /**
     * Credentials A static class representing the JSON encoded login credentials.
     */
    private static class Credentials
    {
        /**
         * Stores whether the credentials are valid or not.
         */
        boolean credentials = false;
    }

    /**
     * Room A static class representing the JSON encoded rooms.
     */
    private static class Room
    {
        /**
         * The room name and number.
         */
        String room;
        
        /**
         * The availability start time.
         */
        String starttime;
        
        /**
         * The availability end time.
         */
        String endtime;
    }

    /**
     * LoginCredentials A REST API interface for getting login credentials
     */
    private static interface LoginCredentials
    {
        // Provide the formatted login credentials query string.
        @GET("/login/{username}/{password}")
        Credentials loginCredentials(@Path("username") String username,
                @Path("password") String password);
    }

    /**
     * AvailableRooms A REST API interface for searching for rooms
     */
    private static interface AvailableRooms
    {
        // Provide the formatted query string for the available rooms.
        @GET("/availablerooms/{time}/{date}/{campus}/{duration}")
        List<Room> availableRooms(@Path("time") String time, @Path("date") String date,
                @Path("campus") String campus, @Path("duration") int duration);
    }

    /**
     * validateCredentials Validates the login credentials of the user.
     * 
     * @param username The user's user name.
     * @param password The user's password.
     * @return Returns true if the credentials are valid, false otherwise.
     */
    public static boolean validateCredentials(String username, String password)
    {
        Credentials credentials = new Credentials();
        RestAdapter restAdapter = new RestAdapter.Builder().setServer(API_URL).build();

        try
        {
            /**
             * Create an instance of the REST API interface
             */
            LoginCredentials loginCredentials = restAdapter.create(LoginCredentials.class);

            /**
             * Validate the credentials
             */
            credentials = loginCredentials.loginCredentials(username, password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return credentials.credentials;
    }

    /**
     * searchRooms A simplified interface to search for the rooms using the REST API.
     * 
     * @param time The specified time that a room is needed.
     * @param date The specified date that a room is needed.
     * @param campus The campus the room is located in.
     * @param duration The length of time the room is needed for.
     * 
     * @return Returns the list of available rooms that match the query.
     */
    public static ArrayList<Rooms> searchRooms(String time, String date, String campus, int duration)
    {
        ArrayList<Rooms> results = new ArrayList<Rooms>();

        /* Create a REST adapter which points the Free Room finder API */
        RestAdapter restAdapter = new RestAdapter.Builder().setServer(API_URL).build();

        try
        {
            /* Create an instance of the REST API interface */
            AvailableRooms availableRooms = restAdapter.create(AvailableRooms.class);

            /* Search for available rooms */
            List<Room> rooms = availableRooms.availableRooms(time, date, campus, duration);
            for (Room room : rooms)
            {
                results.add(new Rooms(room.room, DateTimeUtility.parseFullTime(room.starttime)
                        .getTime(), DateTimeUtility.parseFullTime(room.endtime).getTime(),
                        DateTimeUtility.parseDate(date).getTime()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return results;
    }
}
