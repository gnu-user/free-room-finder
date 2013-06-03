Free Room Finder Setup Instructions
====================================

Creating the Database
----------------------

1.  First connect to mysql and create a new database named free_room_finder

    ```sql
    CREATE DATABASE free_room_finder;
    ```

2.  Next, import the attached database dump, free_room_finder.sql, which contains
    a dump of our entire database, including the data entered in each table.  
    The database dump can be imported in MySQL to create our entire database 
    populated with data. To import the database using the command line you may
    use the following command.

    ```bash
    mysql -u<username> -p free_room_finder < free_room_finder.sql
    ```

Creating the Database from Scratch
------------------------------------

1.  Alternatively, if you would like to develop the db-generate script which parses
    course data, or just would like to have an empty database for development you
    can do so by simply creating the database schema.

2.  Create a new database named free_room_finder and then quit mysql.

    ```sql
    CREATE DATABASE free_room_finder;
    ```

3.  Next, import the database schema using the following command

    ```bash
    mysql -u<username> -p free_room_finder < create_schema.sql
    ```

4.  You can add a test account so that you can login using the following command.

    INSERT INTO users VALUES (NULL, 'Bobby', 'Tables', AES_ENCRYPT('100123456', 'test123'), 'bobby.tables@gmail.com', 'test', AES_ENCRYPT('test123', 'test123'), CURDATE(), CURDATE());

5.  You can login to the free room finder website using the username **test** and 
    password **test123**.


Configuring PHP 
----------------

1.  Edit the authorization file, inc/free_room_auth.php, and set your username,
    password, and database name. The following settings must be set in order to
    connect to the database

    ```php
    /* Database access */
    $db_user = '';
    $db_pass = '';
    $db_name = '';
    ```

2.  Next, configure the root URL for the statistics javascript file which uses the
    web service. You must edit the following variable in statistics.js and set
    the appropriate root URL.

    ```js
    /* The root URL for the RESTful services */
    var rootURL = "http://<your-root-URL>/api";
    ```

3.  Next, please ensure that you SESSIONS enabled for your php installation, the
    free room finder uses SESSIONS heavily to maintain SESSION state between
    pages. If the side is not acting properly then please check that you have
    sessions enabled, on Linux systems this configuration file is usually located
    under /etc/php.ini or /etc/apache2/php.ini

4.  Lastly, ensure that the directory you are executing the website in has write
    access, the generated XML files are stored by the website in it's etc/ folder
    if it does not have write access to this folder then the XML files cannot
    be generated and saved. Please see the attached zip file Generated_XML.zip
    for pre-generated XML files created by the free room finder.


Using the Free Room Finder
--------------------------

1.  Now that you have the database setup and the PHP configuration file configured
    you should be simply able to open up your browser to the locally hosted instance
    of the free room finder to start using the website, if you have any issues
    please review the instructions above. You should be directed to the login page
    for the website.

2.  Now that you are at the free room finder website you should be redirected to
    a login page, please login to the free room finder website using the following
    credentials

    **username: test**  
    **password: test123**  

3.  Lastly, to view the statistics page simply click your username (Billy Bob)
    at the top right and select the "View Statistics" entry to view the statistics
    page.


Important Files
---------------

The following is a list of some important files that are significant to the
project, such as web service API and the database interface.


REST Web Service API (api/index.php)  
    The web service which allows for users to query for statistics.  

Statistics (AJAX)  (js/statistics.js)  
    Used to generate the statistical plots given the information in the database.  

Generated XML Files  (etc/)  
    The location where the xml files reside and are generated to.  

Database Interface  (inc/db_interface.php)  
    This is used to query the database throughout the website all necessary queries   
    are contained in this file.  

Templates (Models)  (templates/)  
    A folder containing all of the templates that will be used by the website to 
    display necessary information.  

Room Results, XML Generation  (room_results.php)  
    This is the logic behind the show room results page. This uses the db interface 
    given the information given by the user to generate the correct results.  

Screen Scraping, Database Generation  (scripts/db-generate-room.py)  
    This is used to scrape the course scheduling data from school’s website.  

Website Rough Design  (doc/website_views.pdf)  
    The early rough design of the website sketched out on paper for full effect.  

Other Diagrams  (Doc folder)  
    Contains all necessary documentation involved in database design, database setup, 
    and necessary documentation for the project as well.  

Validation  (inc/verify.php)  
    A collection of helper functions that facilitate the site’s login, session and 
    cookies verification and validation.  

