William Silva, Nathan Paradis
Professor Durant
CS3200
7 December 2015

README for Music Database Project
##########################################
The final implementation of our project is nearly identical to what was presented
in the project report. There are a few small differences however.
Firstly, the final schema has been changed slightly from the ERD. We have removed
the "Album Track" entity and put the "Track Number" property into the "Song" entity.
Previously, we had both "Album Track" and "Song" as generalizations of "Song" but
we found that all songs are expected to belong to some album and have an album number.
Thus, there is no point to having an "Album Track" entity when all songs are Album
Tracks.
Additionally, the user-interaction flowchart has been slightly modified to fix
the areas that didn't make any sense. The user experience hasn't been changed but
they may see more or less information in certain areas. For instance, we do not keep
track of song prices but in original flowchart, we included the ability to see
song prices. This was a mistake and not present in the final implementation. Additionally,
the original flowchart doesn't allow for a user to modify more than one artist, 
featured artist, or genre for songs and albums. Clearly, we want the user to be
able to modify all fields of an entity so we have added this functionality in our final
product.

Requirements:
Java 1.7
MySQL database

This has been tested on Windows. Your directions may be different if you are
on a different operating system.

Running:
To run the Java front end, simply double-click on "run_front_end".
Alternatively, you can run the command: "java -jar musicDatabaseFrontEnd.jar"

Expected Directory Path for Files:
You should execute musicDatabaseFrontEnd.jar in the same directory that it exists
in. frontend_config.txt should be in that same directory. Additionally, the
resources directory should be in that same directory and contain the folders:
album_cover_art, audio_samples, lyrics, single_cover_art.

NOTE: All audio samples have been replaced with a small mp3 playing a single tone
because we do not want to illegally redistribute music.

##########################################

Files:
musicDatabaseFrontEnd.jar - This file is the compiled main program written in Java. 
This connects to a MySQL database and allows the user to interact with it through
a series of menus. The user can search the database for songs, artists, and albums.
Additionally, the user can add their own entities to the database such as songs,
artists, albums, genres, stores, etc.

frontend_config.txt - This file contains the configuration for the main program. A user
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