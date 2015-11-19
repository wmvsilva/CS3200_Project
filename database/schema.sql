/*
William Silva, Nathaniel Paradis
Professor Durant
CS 3200
7 December 2015

MySQL Database Schema for Final Project
*/

DROP DATABASE IF EXISTS music;
CREATE DATABASE  IF NOT EXISTS music /*!40100 DEFAULT CHARACTER SET utf8 */;
USE music;

######################################
######################################
# Entities
######################################
######################################

/*
 * album table contains music album attributes
 */
DROP TABLE IF EXISTS album;
CREATE TABLE album
(
	# album_id is a generated primary key. It is used rather than the composite
    # key of album_name and release_date to simplify foreign key references to
    # albums
	album_id INT NOT NULL AUTO_INCREMENT,
    # The name of the album
	album_name VARCHAR(70) NOT NULL,
    # The date that the album was originally released
    release_date DATE NOT NULL,
    # A filepath that leads to an image file of the album's cover art
    album_cover VARCHAR(512) NULL,
    
    # Auto_incremented field is used as primary key
    CONSTRAINT
		pk_album_album_id
	PRIMARY KEY ( album_id ),
    
    # The album's name and release date should uniquely identify it
    CONSTRAINT
		uk_album_album_name_release_date
	UNIQUE KEY ( album_name, release_date )
    
) ENGINE = INNODB;

/*
 * general_song table acts as a superclass for the single_song 
 * table. It contains all general information about a
 * song.
 */
DROP TABLE IF EXISTS general_song;
CREATE TABLE general_song
(
	# Auto_generated field to be used as a primary key rather than
    # the composite key of track_name and album_id.
	song_id INT NOT NULL AUTO_INCREMENT,
    # The name of the song
	track_name VARCHAR(70) NOT NULL,
    # The entire lyrics of the song, including newlines
    lyrics TEXT,
    # Filepath to an audio file of a short sample of the song
    audio_sample VARCHAR(512),
    # The length of the song in seconds
    length_seconds SMALLINT UNSIGNED,
    # The associated album of this song
    album_id INT NOT NULL,
    # The number of the track within the album
    track_number TINYINT UNSIGNED,
    
    # Auto_incremented field is used as primary key
    CONSTRAINT
		pk_general_song_song_id
    PRIMARY KEY ( song_id ),
    
    # Each track within an album will have a unique name
	CONSTRAINT
		uk_general_song_track_name_album_id
	UNIQUE KEY ( track_name, album_id ),
    
    # album_id refers to the album table
    # If the album is updated or removed, the
    # song should also be updated/removed.
    CONSTRAINT
		fk_general_song_album_album_id
    FOREIGN KEY ( album_id)
		REFERENCES album(album_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
        
) ENGINE = INNODB;

/*
 * single_song table acts as a subclass for the general_song 
 * table. It contains all information that is specific to a song
 * released as a single.
 */
DROP TABLE IF EXISTS `single_song`;
CREATE TABLE single_song
(
	# Reference to the general_song table that provides more
    # general information
	song_id INT NOT NULL,
    # The date that the single was released
    release_date DATE,
    # File path to an image file of the cover art for this single
    cover_art VARCHAR(512),
    
    # Each row contains information pertaining to a unique song
    CONSTRAINT
		pk_single_song_song_id
	PRIMARY KEY ( song_id ),
    
    # References song_id in general_song
    # If the general song information is updated/removed,
    # then this should also be updated/removed.
    CONSTRAINT
		fk_single_song_general_song_track_name_album_id
	FOREIGN KEY ( song_id )
		REFERENCES general_song( song_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE
        
) ENGINE = INNODB;

/*
 * artist table contains information about a music artist who performs
 * songs. Currently, this is just limited to the name but a larger project
 * could include complete biographical information.
 */
DROP TABLE IF EXISTS `artist`;
CREATE TABLE artist
(
	# The stage name of the artist
	artist_name VARCHAR(70),
    
    # The stage name of the artist is assumed to be unique.
    CONSTRAINT
		pk_artist_name
    PRIMARY KEY ( artist_name)
    
) ENGINE = INNODB;

/*
 * genre table contains information about a musical genre. Currently, this
 * just contains the name of the genre but a larger project could include
 * genre history, related genres, etc.
 */
DROP TABLE IF EXISTS `genre`;
CREATE TABLE genre
(
	# The most common name of the genre
	genre_name VARCHAR(70) NOT NULL,
    
    # Each genre is represented once in the table
    CONSTRAINT
		pk_genre_name
    PRIMARY KEY ( genre_name )
    
) ENGINE = INNODB;

/*
 * music_format table contains information about the distribution format of
 * albums. Examples include digitial, CD, record, etc.
 */
DROP TABLE IF EXISTS `music_format`;
CREATE TABLE music_format
(
	# The most common name of the distribution format
	format_name VARCHAR(70) NOT NULL,
    
    # Each distribution format has a unique name
    CONSTRAINT
		pk_format_name
    PRIMARY KEY ( format_name)
    
) ENGINE = INNODB;

/*
 * store table contains information about stores that sell albums.
 * If this project were expanded, this table would definitely include
 * more fields such as store logo, link to store, etc.
 */
DROP TABLE IF EXISTS `store`;
CREATE TABLE store
(
	# The company name
	store_name VARCHAR(70) NOT NULL,
    
    # Each name of a company is unique
    CONSTRAINT
		pk_store_name
    PRIMARY KEY ( store_name)
    
) ENGINE = INNODB;

######################################
######################################
# Relationships
######################################
######################################

/*
 * artists_of_songs table contains which artists have authored
 * which songs. This is a many-to-many relationship. Songs
 * should have at least one artist here. Due to limitations of
 * MySQL (lack of deferred constraints), total participation
 * on the 'many' side of a relationship is not possible.
 * Therefore, it is up to the user/application to enforce this.
 */
DROP TABLE IF EXISTS `artists_of_songs`;
CREATE TABLE artists_of_songs
(
	# The id of a song
	song_id INT NOT NULL,
    # The name of the artist who authored the song
    artist_name VARCHAR(70),
    
    # Each song can have many artists and each artist can have
    # many songs
    CONSTRAINT
		pk_song_id_artist_name
    PRIMARY KEY ( song_id, artist_name ),
    
    # References song table
    # If songs are updated/deleted, information about
    # who wrote them should also be updated/deleted.
	CONSTRAINT
		fk_artists_of_songs_general_song
	FOREIGN KEY ( song_id )
		REFERENCES general_song( song_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    # References artist table
    # If an artist is updated, it should be updated here to.
    # If an artist is deleted, the fact that the artist made
    # this song is no longer relevant information and should
    # be deleted.
	CONSTRAINT
		fk_artists_of_songs_artist_artist_name
	FOREIGN KEY ( artist_name )
		REFERENCES artist( artist_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

/*
 * featured_artists_of_songs table contains which artists
 * have been featured in which songs. For instance, the song
 * 'Daft Punk - Get Lucky ft. Pharrell Williams' features
 * Pharrel Williams so this table would contain a reference
 * to Get Lucky and Pharrel Williams.
 */
DROP TABLE IF EXISTS `featured_artists_of_songs`;
CREATE TABLE featured_artists_of_songs
(
	# The song with a featured artist
	song_id INT NOT NULL,
    # Name of the featured artist
    featured_artist_name VARCHAR(70) NOT NULL,
    
    
    CONSTRAINT
		pk_song_id_artist_name
    PRIMARY KEY ( song_id, featured_artist_name ),
    
	CONSTRAINT
		fk_featured_artists_of_songs_general_song
	FOREIGN KEY ( song_id )
		REFERENCES general_song( song_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
      
	CONSTRAINT
		fk_featured_artists_of_songs_artist_artist_name
	FOREIGN KEY ( featured_artist_name )
		REFERENCES artist( artist_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

DROP TABLE IF EXISTS `genre_of_song`;
CREATE TABLE genre_of_song
(
	song_id INT NOT NULL,
    genre_name VARCHAR(70) NOT NULL,
    
    
    CONSTRAINT
		pk_song_id_genre_name
    PRIMARY KEY ( song_id, genre_name ),
    
	CONSTRAINT
		fk_genre_of_song_general_song
	FOREIGN KEY ( song_id )
		REFERENCES general_song( song_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
        
	CONSTRAINT
		fk_genre_of_song_genre_genre_name
	FOREIGN KEY ( genre_name )
		REFERENCES genre( genre_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

DROP TABLE IF EXISTS `artists_of_albums`;
CREATE TABLE artists_of_albums
(
	album_id INT NOT NULL,
    artist_name VARCHAR(70),
    
    
    CONSTRAINT
		pk_album_id_artist_name
    PRIMARY KEY ( album_id, artist_name ),
    
	CONSTRAINT
		fk_artists_of_albums_album_album_id
	FOREIGN KEY ( album_id )
		REFERENCES album( album_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
        
	CONSTRAINT
		fk_artists_of_albumss_artist_artist_name
	FOREIGN KEY ( artist_name )
		REFERENCES artist( artist_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

DROP TABLE IF EXISTS `genre_of_album`;
CREATE TABLE genre_of_album
(
	album_id INT NOT NULL,
    genre_name VARCHAR(70) NOT NULL,
    
    
    CONSTRAINT
		pk_album_id_genre_name
    PRIMARY KEY ( album_id, genre_name ),
    
	CONSTRAINT
		fk_genre_of_album_album_id
	FOREIGN KEY ( album_id )
		REFERENCES album( album_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
        
	CONSTRAINT
		fk_genre_of_album_genre_genre_name
	FOREIGN KEY ( genre_name )
		REFERENCES genre( genre_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

DROP TABLE IF EXISTS `format_of_album`;
CREATE TABLE format_of_album
(
	distribution_id INT NOT NULL AUTO_INCREMENT,
	album_id INT NOT NULL,
    format_name VARCHAR(70) NOT NULL,
    
    CONSTRAINT
		pk_format_of_album_distribution_id
    PRIMARY KEY ( distribution_id ),
    
	CONSTRAINT
		uk_format_of_album_album_id_format_name
	UNIQUE KEY ( album_id, format_name ),
    
	CONSTRAINT
		fk_format_of_album_album_id
	FOREIGN KEY ( album_id )
		REFERENCES album( album_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
        
	CONSTRAINT
		fk_format_of_album_format_format_name
	FOREIGN KEY ( format_name )
		REFERENCES music_format( format_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

DROP TABLE IF EXISTS `store_catalog`;
CREATE TABLE store_catalog
(
	store_name VARCHAR(70) NOT NULL,
	distribution_id INT NOT NULL,
    album_price DECIMAL(10, 2) NOT NULL,
    
    CONSTRAINT
		pk_store_catalog_store_name_distribution_id_album_price
    PRIMARY KEY ( store_name, distribution_id, album_price ),
    
	CONSTRAINT
		fk_store_catalog_store_store_name
	FOREIGN KEY ( store_name )
		REFERENCES store( store_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
        
	CONSTRAINT
		fk_store_catalog_format_of_album_distribution_id
	FOREIGN KEY ( distribution_id )
		REFERENCES format_of_album( distribution_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;
