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

DROP PROCEDURE IF EXISTS albums_by_artist;
DELIMITER //
CREATE PROCEDURE
albums_by_artist(IN search_name VARCHAR(45))
BEGIN
	SELECT
		album_name, album_id
	FROM
		album join artists_of_albums using (album_id)
	WHERE
		artist_name = search_name;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS modify_artist;
DELIMITER //
CREATE PROCEDURE
modify_artist(IN old_artist_name VARCHAR(45), 
	IN new_artist_name VARCHAR(45))
BEGIN
	UPDATE artist SET artist_name = new_artist_name
    WHERE artist_name = old_artist_name;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS delete_artist;
DELIMITER //
CREATE PROCEDURE
delete_artist(IN given_artist_name VARCHAR(45))
BEGIN
	DELETE FROM
		artist
	WHERE artist_name = given_artist_name;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS basic_album_info;
DELIMITER //
CREATE PROCEDURE
basic_album_info(IN given_album_id VARCHAR(45))
BEGIN
	SELECT
		album_name, release_date, album_cover
	FROM
		album
	WHERE
		album_id = given_album_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_artists_of_album;
DELIMITER //
CREATE PROCEDURE
p_artists_of_album(IN given_album_id VARCHAR(45))
BEGIN
	SELECT
		artist_name
	FROM
		artists_of_albums
	WHERE
		album_id = given_album_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_genres_of_album;
DELIMITER //
CREATE PROCEDURE
p_genres_of_album(IN given_album_id VARCHAR(45))
BEGIN
	SELECT
		genre_name
	FROM
		genre_of_album
	WHERE
		album_id = given_album_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_tracks_of_album;
DELIMITER //
CREATE PROCEDURE
p_tracks_of_album(IN given_album_id VARCHAR(45))
BEGIN
	SELECT
		track_name, song_id
	FROM
		general_song
	WHERE
		album_id = given_album_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_tracks_of_album;
DELIMITER //
CREATE PROCEDURE
p_tracks_of_album(IN given_album_id VARCHAR(45))
BEGIN
	SELECT
		track_name, song_id
	FROM
		general_song
	WHERE
		album_id = given_album_id;
END //
DELIMITER ;

-- Store, price, format
DROP PROCEDURE IF EXISTS p_album_store_info;
DELIMITER //
CREATE PROCEDURE
p_album_store_info(IN given_album_id VARCHAR(45))
BEGIN
	SELECT
		store_name, album_price, format_name
	FROM
		store_catalog JOIN format_of_album USING (distribution_id)
	WHERE
		album_id = given_album_id;
END //
DELIMITER ;