/*
William Silva, Nathaniel Paradis
Professor Durant
CS 3200
7 December 2015

MySQL Database Procedures for Final Project
*/

DROP PROCEDURE IF EXISTS search_artist;
DELIMITER //
CREATE PROCEDURE
search_artist(IN search_name VARCHAR(45))
BEGIN
	SELECT
		artist_name
	FROM
		artist
	WHERE
		artist_name LIKE CONCAT('%', search_name, '%');
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS search_album;
DELIMITER //
CREATE PROCEDURE
search_album(IN search_name VARCHAR(45))
BEGIN
	SELECT
		album_name, artist_name, album_id
	FROM
		album join artists_of_albums using (album_id)
	WHERE
		album_name LIKE CONCAT('%', search_name, '%');
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS search_song;
DELIMITER //
CREATE PROCEDURE
search_song(IN search_name VARCHAR(45))
BEGIN
	SELECT
		track_name, artist_name, song_id
	FROM
		general_song join artists_of_songs using (song_id)
	WHERE
		track_name LIKE CONCAT('%', search_name, '%');
END //
DELIMITER ;
