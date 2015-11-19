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
	track_name VARCHAR(70) NOT NULL,
    lyrics TEXT,
    audio_sample VARCHAR(512),
    length_seconds SMALLINT UNSIGNED,
    album_id INT NOT NULL,
    
    CONSTRAINT
		pk_general_song_album_id_track_name
    PRIMARY KEY ( album_id, track_name ),
    
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
	track_name VARCHAR(70) NOT NULL,
    album_id INT NOT NULL,
    release_date DATE,
    cover_art VARCHAR(512),
    
    CONSTRAINT
		pk_single_song_track_name_album_id
	PRIMARY KEY ( track_name, album_id ),
    
    CONSTRAINT
		fk_single_song_general_song_track_name_album_id
	FOREIGN KEY ( album_id, track_name )
		REFERENCES general_song( album_id, track_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
        
) ENGINE = INNODB;

DROP TABLE IF EXISTS `album_song`;
CREATE TABLE album_song
(
	track_name VARCHAR(70) NOT NULL,
    album_id INT NOT NULL,
    track_number TINYINT UNSIGNED,
    
    CONSTRAINT
		pk_album_song_track_name_album_id
	PRIMARY KEY ( track_name, album_id ),
    
    CONSTRAINT
		fk_album_song_general_song_track_name_album_id
	FOREIGN KEY ( album_id, track_name )
		REFERENCES general_song( album_id, track_name )
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
	album_id INT NOT NULL,
    track_name VARCHAR(70) NOT NULL,
    artist_name VARCHAR(70),
    
    
    CONSTRAINT
		pk_album_id_track_name_artist_name
    PRIMARY KEY ( album_id, track_name, artist_name ),
    
	CONSTRAINT
		fk_artists_of_songs_song_album_id_track_name
	FOREIGN KEY ( album_id, track_name )
		REFERENCES general_song( album_id, track_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
        
	CONSTRAINT
		fk_artists_of_songs_artist_artist_name
	FOREIGN KEY ( artist_name )
		REFERENCES artist( artist_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

CREATE TRIGGER tg_artists_of_songs_total_song_participation
BEFORE INSERT ON general_song
FOR EACH ROW
  SET NEW.track_name = IF(
    (
      SELECT COUNT(*) FROM artists_of_songs WHERE track_name = NEW.track_name AND album_id = NEW.album_id
    ) != 0,
    NEW.track_name,
    NULL
  );
