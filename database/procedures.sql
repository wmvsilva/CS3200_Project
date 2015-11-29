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

-- Track Name, Track Number, Album Name, Album id, Lyric filepath,
-- Sample filepath
DROP PROCEDURE IF EXISTS p_base_song_info;
DELIMITER //
CREATE PROCEDURE
p_base_song_info(IN given_song_id INT)
BEGIN
	SELECT
		track_name, track_number, album_name, album_id, lyrics, audio_sample
	FROM
		general_song JOIN album USING (album_id)
	WHERE
		song_id = given_song_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_song_artists;
DELIMITER //
CREATE PROCEDURE
p_song_artists(IN given_song_id INT)
BEGIN
	SELECT
		artist_name
	FROM
		artists_of_songs
	WHERE
		song_id = given_song_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_song_ft_artists;
DELIMITER //
CREATE PROCEDURE
p_song_ft_artists(IN given_song_id INT)
BEGIN
	SELECT
		featured_artist_name
	FROM
		featured_artists_of_songs
	WHERE
		song_id = given_song_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_song_genres;
DELIMITER //
CREATE PROCEDURE
p_song_genres(IN given_song_id INT)
BEGIN
	SELECT
		genre_name
	FROM
		genre_of_song
	WHERE
		song_id = given_song_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_track_name;
DELIMITER //
CREATE PROCEDURE
p_modify_track_name(IN given_song_id INT,
						IN new_track_name VARCHAR(45))
BEGIN
	UPDATE general_song SET track_name = new_track_name
    WHERE song_id = given_song_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_track_number;
DELIMITER //
CREATE PROCEDURE
p_modify_track_number(IN given_song_id INT,
						IN new_track_number INT)
BEGIN
	UPDATE general_song SET track_number = new_track_number
    WHERE song_id = given_song_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_song_album;
DELIMITER //
CREATE PROCEDURE
p_modify_song_album(IN given_song_id INT,
						IN new_album_name INT, IN release_date_of_album DATE)
BEGIN
    DECLARE new_album_id INT;
    SET new_album_id = 
		(SELECT
			album_id
		FROM
			album
		WHERE
			album_name = new_album_name
				AND
			release_date = release_date_of_album);
	UPDATE general_song SET album_id = new_album_id
    WHERE song_id = given_song_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_release_dates_of_album;
DELIMITER //
CREATE PROCEDURE
p_release_dates_of_album(IN given_album_name VARCHAR(45))
BEGIN
    SELECT
		release_date
	FROM
		album
	WHERE
		album_name = given_album_name;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_song_artist;
DELIMITER //
CREATE PROCEDURE
p_modify_song_artist(IN given_track_id INT,
						IN old_artist_name VARCHAR(45),
                        IN new_artist_name VARCHAR(45))
BEGIN
	UPDATE artists_of_songs SET artist_name = new_artist_name
    WHERE artist_name = old_artist_name
		AND song_id = given_track_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_song_ft_artist;
DELIMITER //
CREATE PROCEDURE
p_modify_song_ft_artist(IN given_track_id INT,
						IN old_ft_artist_name VARCHAR(45),
                        IN new_ft_artist_name VARCHAR(45))
BEGIN
	UPDATE featured_artists_of_songs SET featured_artist_name = new_ft_artist_name
    WHERE featured_artist_name = old_ft_artist_name
		AND song_id = given_track_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS p_modify_song_genre;
DELIMITER //
CREATE PROCEDURE
p_modify_song_genre(IN given_track_id INT,
						IN old_genre_name VARCHAR(45),
                        IN new_genre_name VARCHAR(45))
BEGIN
	UPDATE genre_of_song SET genre_name = new_genre_name
    WHERE genre_name = old_genre_name
		AND song_id = given_track_id;
END //
DELIMITER ;
