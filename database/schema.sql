/*
William Silva, Nathaniel Paradis
Professor Durant
CS 3200
7 December 2015

MySQL Database Schema for Final Project
*/

DROP DATABASE IF EXISTS `music`;
CREATE DATABASE  IF NOT EXISTS `music` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `music`;

######################################
# Entities
######################################

DROP TABLE IF EXISTS `album`;
CREATE TABLE album
(
	album_id INT NOT NULL AUTO_INCREMENT,
	album_name VARCHAR(70) NOT NULL,
    release_date DATE NOT NULL,
    album_cover VARCHAR(512) NULL,
    
    CONSTRAINT
		pk_album_album_id
	PRIMARY KEY ( album_id ),
    
    CONSTRAINT
		uk_album_album_name_release_date
	UNIQUE KEY ( album_name, release_date )
    
) ENGINE = INNODB;

DROP TABLE IF EXISTS `general_song`;
CREATE TABLE general_song
(
	song_id INT NOT NULL AUTO_INCREMENT,
	track_name VARCHAR(70) NOT NULL,
    lyrics TEXT,
    audio_sample VARCHAR(512),
    length_seconds SMALLINT UNSIGNED,
    album_id INT NOT NULL,
    
    CONSTRAINT
		pk_general_song_song_id
    PRIMARY KEY ( song_id ),
    
	CONSTRAINT
		uk_general_song_track_name_album_id
	UNIQUE KEY ( track_name, album_id ),
    
    CONSTRAINT
		fk_general_song_album_album_id
    FOREIGN KEY ( album_id)
		REFERENCES album(album_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
        
) ENGINE = INNODB;

DROP TABLE IF EXISTS `single_song`;
CREATE TABLE single_song
(
	song_id INT NOT NULL,
    release_date DATE,
    cover_art VARCHAR(512),
    
    CONSTRAINT
		pk_single_song_song_id
	PRIMARY KEY ( song_id ),
    
    CONSTRAINT
		fk_single_song_general_song_track_name_album_id
	FOREIGN KEY ( song_id )
		REFERENCES general_song( song_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE
        
) ENGINE = INNODB;

DROP TABLE IF EXISTS `album_song`;
CREATE TABLE album_song
(
	song_id INT NOT NULL,
    track_number TINYINT UNSIGNED,
    
    CONSTRAINT
		pk_album_song_song_id
	PRIMARY KEY ( song_id ),
    
    CONSTRAINT
		fk_album_song_general_song_song_id
	FOREIGN KEY ( song_id )
		REFERENCES general_song( song_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE
        
) ENGINE = INNODB;

DROP TABLE IF EXISTS `artist`;
CREATE TABLE artist
(
	artist_name VARCHAR(70),
    
    CONSTRAINT
		pk_artist_name
    PRIMARY KEY ( artist_name)
    
) ENGINE = INNODB;

DROP TABLE IF EXISTS `genre`;
CREATE TABLE genre
(
	genre_name VARCHAR(70) NOT NULL,
    
    CONSTRAINT
		pk_genre_name
    PRIMARY KEY ( genre_name )
    
) ENGINE = INNODB;

DROP TABLE IF EXISTS `music_format`;
CREATE TABLE music_format
(
	format_name VARCHAR(70) NOT NULL,
    
    CONSTRAINT
		pk_format_name
    PRIMARY KEY ( format_name)
    
) ENGINE = INNODB;

DROP TABLE IF EXISTS `store`;
CREATE TABLE store
(
	store_name VARCHAR(70) NOT NULL,
    
    CONSTRAINT
		pk_store_name
    PRIMARY KEY ( store_name)
    
) ENGINE = INNODB;

##################################
# Relationships
##################################

DROP TABLE IF EXISTS `artists_of_songs`;
CREATE TABLE artists_of_songs
(
	song_id INT NOT NULL,
    artist_name VARCHAR(70),
    
    
    CONSTRAINT
		pk_song_id_artist_name
    PRIMARY KEY ( song_id, artist_name ),
    
	CONSTRAINT
		fk_artists_of_songs_general_song
	FOREIGN KEY ( song_id )
		REFERENCES general_song( song_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
        
	CONSTRAINT
		fk_artists_of_songs_artist_artist_name
	FOREIGN KEY ( artist_name )
		REFERENCES artist( artist_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

DROP TABLE IF EXISTS `featured_artists_of_songs`;
CREATE TABLE featured_artists_of_songs
(
	song_id INT NOT NULL,
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
    
    CONSTRAINT
		pk_store_catalog_store_name_distribution_id
    PRIMARY KEY ( store_name, distribution_id ),
    
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
