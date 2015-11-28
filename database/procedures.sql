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



DROP PROCEDURE IF EXISTS p_modify_album_name;
DELIMITER //
CREATE PROCEDURE
p_modify_album_name(IN given_album_id VARCHAR(45), IN new_album_name VARCHAR(45))
BEGIN
	UPDATE album SET album_name = new_album_name
    WHERE album_id = given_album_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_album_release_date;
DELIMITER //
CREATE PROCEDURE
p_modify_album_release_date(IN given_album_id VARCHAR(45), 
								IN new_album_release_date VARCHAR(45))
BEGIN
	UPDATE album SET release_date = new_album_release_date
    WHERE album_id = given_album_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_album_artist;
DELIMITER //
CREATE PROCEDURE
p_modify_album_artist(IN given_album_id VARCHAR(45),
								IN old_album_artist VARCHAR(45),
								IN new_album_artist VARCHAR(45))
BEGIN
	UPDATE artists_of_albums SET artist_name = new_album_artist
    WHERE album_id = given_album_id
		AND artist_name = old_album_artist;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_album_genre;
DELIMITER //
CREATE PROCEDURE
p_modify_album_genre(IN given_album_id VARCHAR(45),
								IN old_genre_name VARCHAR(45),
								IN new_genre_name VARCHAR(45))
BEGIN
	UPDATE genre_of_album SET genre_name = new_genre_name
    WHERE album_id = given_album_id
		AND genre_name = old_genre_name;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_album_store_catalog;
DELIMITER //
CREATE PROCEDURE
p_modify_album_store_catalog(IN given_album_id VARCHAR(45),
								IN old_store_name VARCHAR(45),
								IN old_price DECIMAL(10, 2),
                                IN old_format VARCHAR(45),
                                IN new_store_name VARCHAR(45))
BEGIN
	DECLARE album_distribution_id INT;
    SET album_distribution_id = 
		(SELECT
			distribution_id
		FROM
			format_of_album
		WHERE
			album_id = given_album_id
				AND
			format_name = old_format);
	UPDATE store_catalog SET store_name = new_store_name
    WHERE store_name = old_store_name
		AND album_price = old_price
        AND distribution_id = album_distribution_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_album_price_catalog;
DELIMITER //
CREATE PROCEDURE
p_modify_album_price_catalog(IN given_album_id VARCHAR(45),
								IN old_store_name VARCHAR(45),
								IN old_price DECIMAL(10, 2),
                                IN old_format VARCHAR(45),
                                IN new_price DECIMAL(10, 2))
BEGIN
	DECLARE album_distribution_id INT;
    SET album_distribution_id = 
		(SELECT
			distribution_id
		FROM
			format_of_album
		WHERE
			album_id = given_album_id
				AND
			format_name = old_format);
	UPDATE store_catalog SET album_price = new_price
    WHERE store_name = old_store_name
		AND album_price = old_price
        AND distribution_id = album_distribution_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_album_format_catalog;
DELIMITER //
CREATE PROCEDURE
p_modify_album_format_catalog(IN given_album_id VARCHAR(45),
								IN old_store_name VARCHAR(45),
								IN old_price DECIMAL(10, 2),
                                IN old_format VARCHAR(45),
                                IN new_format VARCHAR(45))
BEGIN
	DECLARE old_album_distribution_id INT;
    DECLARE new_album_distribution_id INT;
    SET old_album_distribution_id = 
		(SELECT
			distribution_id
		FROM
			format_of_album
		WHERE
			album_id = given_album_id
				AND
			format_name = old_format);
    SET new_album_distribution_id = 
		(SELECT
			distribution_id
		FROM
			format_of_album
		WHERE
			album_id = given_album_id
				AND
			format_name = new_format);
	UPDATE store_catalog SET distribution_id = new_album_distribution_id
    WHERE store_name = old_store_name
		AND album_price = old_price
        AND distribution_id = old_album_distribution_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_delete_album;
DELIMITER //
CREATE PROCEDURE
p_delete_album(IN given_album_id VARCHAR(45))
BEGIN
	DELETE FROM
		album
	WHERE album_id = given_album_id;
END //
DELIMITER ;
