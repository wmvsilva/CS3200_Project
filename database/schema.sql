/*
William Silva, Nathaniel Paradis
Professor Durant
CS 3200
7 December 2015

MySQL Database Schema for Final Project
*/

CREATE DATABASE  IF NOT EXISTS `music` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `music`;


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

DROP TABLE IF EXISTS `artist`;
CREATE TABLE artist
(
	artist_name VARCHAR(70),
    
    CONSTRAINT
		pk_artist_name
    PRIMARY KEY ( artist_name)
    
) ENGINE = INNODB;

DROP TABLE IF EXISTS `music_format`;
CREATE TABLE music_format
(
	format_name VARCHAR(70),
    
    CONSTRAINT
		pk_format_name
    PRIMARY KEY ( format_name)
    
) ENGINE = INNODB;