William Silva, Nathan Paradis
Professor Durant
CS3200
7 December 2015

##########################################

Requirements:
Java 1.7
MySQL database

This has been tested on Windows. Your directions may be different if you are
on a different operating system.

Running:
To run the Java front end, simply double-click on "run_front_end".
Alternatively, you can run the command: "java -jar musicDatabaseFrontEnd.jar"

NOTE: All audio samples have been replaced with a small mp3 playing a single tone
because we do not want to illegally redistribute music.

##########################################

Files:
musicDatabaseFrontEnd.jar - This file is the compiled main program written in Java. 
This connects to a MySQL database and allows the user to interact with it through
a series of menus. The user can search the database for songs, artists, and albums.
Additionally, the user can add their own entities to the database such as songs,
artists, albums, genres, stores, etc.

frontend_config - This file contains the configuration for the main program. A user
can configure it like so:
<USER NAME>
<PASSWORD>
<MACHINE>
<PORT NUMBER>
<MYSQL DATABASE NAME>

src - Folder that contains all Java source files used to compile the main program
as well as any third-party library JARs that were used.

resources - Because we didn't want to store any binaries in our database, all
binaries are stored in this folder. The database simply stores the filename and the
front end grabs the file from here.