
CREATE TABLE user  
(
    id              INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, 
    username        TEXT, 
    password        TEXT
);


CREATE TABLE bookings
(
    id         INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
    room_name          TEXT,
    start_time         INTEGER,
    end_time           INTEGER,
    book_date          INTEGER
);

INSERT INTO bookings (room_name, start_time, end_time, book_date) VALUES("UA2010", 134242, 234232, 232424);
INSERT INTO bookings (room_name, start_time, end_time, book_date) VALUES("UA1010", 4435534, 435435, 2343242);

DELETE * FROM bookings WHERE id = (SELECT id FROM bookings ORDER BY book_date LIMIT 1);