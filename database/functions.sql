/*
William Silva, Nathaniel Paradis
Professor Durant
CS 3200
7 December 2015

MySQL Database Functions for Final Project
*/

DROP FUNCTION IF EXISTS f_is_song_single;
DELIMITER //
CREATE FUNCTION f_is_song_single(given_track_id INT) 
	RETURNS BOOL
BEGIN
	DECLARE is_exist BOOL;
    SET is_exist = 0;
    SELECT EXISTS(
		SELECT 
			* 
		FROM 
			single_song 
		WHERE song_id = given_track_id) 
			INTO is_exist ;
    RETURN is_exist;
END //
DELIMITER ;

DROP FUNCTION IF EXISTS f_does_artist_exist;
DELIMITER //
CREATE FUNCTION f_does_artist_exist(given_artist_name VARCHAR(45)) 
	RETURNS BOOL
BEGIN
	DECLARE is_exist BOOL;
    SET is_exist = 0;
    SELECT EXISTS(
		SELECT 
			* 
		FROM 
			artist
		WHERE artist_name = given_artist_name) 
			INTO is_exist ;
    RETURN is_exist;
END //
DELIMITER ;

DROP FUNCTION IF EXISTS f_does_genre_exist;
DELIMITER //
CREATE FUNCTION f_does_genre_exist(given_genre_name VARCHAR(45)) 
	RETURNS BOOL
BEGIN
	DECLARE is_exist BOOL;
    SET is_exist = 0;
    SELECT EXISTS(
		SELECT 
			* 
		FROM 
			genre
		WHERE genre_name = given_genre_name) 
			INTO is_exist ;
    RETURN is_exist;
END //
DELIMITER ;

-- f_does_store_exist
DROP FUNCTION IF EXISTS f_does_store_exist;
DELIMITER //
CREATE FUNCTION f_does_store_exist(given_store_name VARCHAR(45)) 
	RETURNS BOOL
BEGIN
	DECLARE is_exist BOOL;
    SET is_exist = 0;
    SELECT EXISTS(
		SELECT 
			* 
		FROM 
			store
		WHERE store_name = given_store_name) 
			INTO is_exist ;
    RETURN is_exist;
END //
DELIMITER ;

DROP FUNCTION IF EXISTS f_does_music_format_exist;
DELIMITER //
CREATE FUNCTION f_does_music_format_exist(given_format_name VARCHAR(45)) 
	RETURNS BOOL
BEGIN
	DECLARE is_exist BOOL;
    SET is_exist = 0;
    SELECT EXISTS(
		SELECT 
			* 
		FROM 
			music_format
		WHERE format_name = given_format_name) 
			INTO is_exist ;
    RETURN is_exist;
END //
DELIMITER ;
