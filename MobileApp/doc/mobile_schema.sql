
CREATE TABLE USER_TABLE_NAME  
(
    KEY_ID              INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, 
    KEY_USERNAME        TEXT, 
    KEY_PASSWORD        TEXT
);


CREATE TABLE bookings
(
    booking_id         INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
    room_name          TEXT,
    start_time         INTEGER,
    end_time           INTEGER,
    book_date          INTEGER
);