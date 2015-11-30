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
