/*
William Silva, Nathaniel Paradis
Professor Durant
CS 3200
7 December 2015

MySQL Database Procedures for Final Project
*/

/* Given some string, returns a set of artist names that
 * contain the given string
 */
DROP PROCEDURE IF EXISTS search_artist;
DELIMITER //
CREATE PROCEDURE
search_artist(IN search_name VARCHAR(70))
BEGIN
	SELECT
		artist_name
	FROM
		artist
	WHERE
		artist_name LIKE CONCAT('%', search_name, '%');
END //
DELIMITER ;

/* Given some string, returns a set of album names that
 * contain the given string.
 * The associated artists and albums IDs with those albums
 * are also returned.
 */
DROP PROCEDURE IF EXISTS search_album;
DELIMITER //
CREATE PROCEDURE
search_album(IN search_name VARCHAR(70))
BEGIN
	SELECT
		album_name, artist_name, album_id
	FROM
		album join artists_of_albums using (album_id)
	WHERE
		album_name LIKE CONCAT('%', search_name, '%');
END //
DELIMITER ;

/* Given some string, returns a set of song names that
 * contain the given string.
 * The associated artists and song IDs with those songs
 * are also returned.
 */
DROP PROCEDURE IF EXISTS search_song;
DELIMITER //
CREATE PROCEDURE
search_song(IN search_name VARCHAR(70))
BEGIN
	SELECT
		track_name, artist_name, song_id
	FROM
		general_song join artists_of_songs using (song_id)
	WHERE
		track_name LIKE CONCAT('%', search_name, '%');
END //
DELIMITER ;

/* Returns the album names and album ids produced by a given
 * artist.
 */
DROP PROCEDURE IF EXISTS albums_by_artist;
DELIMITER //
CREATE PROCEDURE
albums_by_artist(IN search_name VARCHAR(70))
BEGIN
	SELECT
		album_name, album_id
	FROM
		album join artists_of_albums using (album_id)
	WHERE
		artist_name = search_name;
END //
DELIMITER ;

/* Changes the artist with the given old name to have
 * the new name.
 */
DROP PROCEDURE IF EXISTS modify_artist;
DELIMITER //
CREATE PROCEDURE
modify_artist(IN old_artist_name VARCHAR(70), 
	IN new_artist_name VARCHAR(70))
BEGIN
	UPDATE artist SET artist_name = new_artist_name
    WHERE artist_name = old_artist_name;
END //
DELIMITER ;

/* Deletes the artist with the given name from the
 * database
 */
DROP PROCEDURE IF EXISTS delete_artist;
DELIMITER //
CREATE PROCEDURE
delete_artist(IN given_artist_name VARCHAR(70))
BEGIN
	DELETE FROM
		artist
	WHERE artist_name = given_artist_name;
END //
DELIMITER ;

/* Retrieves basic album information (album name,
 * release date, and album cover filename) for the
 * given album id.
 */
DROP PROCEDURE IF EXISTS basic_album_info;
DELIMITER //
CREATE PROCEDURE
basic_album_info(IN given_album_id VARCHAR(70))
BEGIN
	SELECT
		album_name, release_date, album_cover
	FROM
		album
	WHERE
		album_id = given_album_id;
END //
DELIMITER ;

/* Returns all artists that made a given album.
 */
DROP PROCEDURE IF EXISTS p_artists_of_album;
DELIMITER //
CREATE PROCEDURE
p_artists_of_album(IN given_album_id VARCHAR(70))
BEGIN
	SELECT
		artist_name
	FROM
		artists_of_albums
	WHERE
		album_id = given_album_id;
END //
DELIMITER ;

/* Returns all genres assigned to a given album.
 */
DROP PROCEDURE IF EXISTS p_genres_of_album;
DELIMITER //
CREATE PROCEDURE
p_genres_of_album(IN given_album_id VARCHAR(70))
BEGIN
	SELECT
		genre_name
	FROM
		genre_of_album
	WHERE
		album_id = given_album_id;
END //
DELIMITER ;

/* Returns the track names and track ids of all songs
 * within the given album.
 */
DROP PROCEDURE IF EXISTS p_tracks_of_album;
DELIMITER //
CREATE PROCEDURE
p_tracks_of_album(IN given_album_id VARCHAR(70))
BEGIN
	SELECT
		track_name, song_id
	FROM
		general_song
	WHERE
		album_id = given_album_id;
END //
DELIMITER ;

/* Returns purchase information for a given album including
 * the store where it is sold, the price, and the music format
 * that it is in.
 */
DROP PROCEDURE IF EXISTS p_album_store_info;
DELIMITER //
CREATE PROCEDURE
p_album_store_info(IN given_album_id VARCHAR(70))
BEGIN
	SELECT
		store_name, album_price, format_name
	FROM
		store_catalog JOIN format_of_album USING (distribution_id)
	WHERE
		album_id = given_album_id;
END //
DELIMITER ;

/* Modifies the given album to have the given album name.
 */
DROP PROCEDURE IF EXISTS p_modify_album_name;
DELIMITER //
CREATE PROCEDURE
p_modify_album_name(IN given_album_id VARCHAR(70), IN new_album_name VARCHAR(70))
BEGIN
	UPDATE album SET album_name = new_album_name
    WHERE album_id = given_album_id;
END //
DELIMITER ;

/* Modifies the given album to have the given release date.
 */
DROP PROCEDURE IF EXISTS p_modify_album_release_date;
DELIMITER //
CREATE PROCEDURE
p_modify_album_release_date(IN given_album_id VARCHAR(70), 
								IN new_album_release_date DATE)
BEGIN
	UPDATE album SET release_date = new_album_release_date
    WHERE album_id = given_album_id;
END //
DELIMITER ;

/* Modifies one of the artists of an album to be another artist.
 * The artist entity is not actually changed. The relationship is
 * just changed such that the album was created by another artist.
 */
DROP PROCEDURE IF EXISTS p_modify_album_artist;
DELIMITER //
CREATE PROCEDURE
p_modify_album_artist(IN given_album_id VARCHAR(70),
								IN old_album_artist VARCHAR(70),
								IN new_album_artist VARCHAR(70))
BEGIN
	UPDATE artists_of_albums SET artist_name = new_album_artist
    WHERE album_id = given_album_id
		AND artist_name = old_album_artist;
END //
DELIMITER ;

/* Given an album and a genre for that album, removes that genre
 * and adds the given new genre.
 */
DROP PROCEDURE IF EXISTS p_modify_album_genre;
DELIMITER //
CREATE PROCEDURE
p_modify_album_genre(IN given_album_id VARCHAR(70),
								IN old_genre_name VARCHAR(70),
								IN new_genre_name VARCHAR(70))
BEGIN
	UPDATE genre_of_album SET genre_name = new_genre_name
    WHERE album_id = given_album_id
		AND genre_name = old_genre_name;
END //
DELIMITER ;

/* Modifies purchase information for an album by changing what
 * store you can purchase the album with the given price and
 * given format.
 */
DROP PROCEDURE IF EXISTS p_modify_album_store_catalog;
DELIMITER //
CREATE PROCEDURE
p_modify_album_store_catalog(IN given_album_id VARCHAR(70),
								IN old_store_name VARCHAR(70),
								IN old_price DECIMAL(10, 2),
                                IN old_format VARCHAR(70),
                                IN new_store_name VARCHAR(70))
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

/* Modifies the price of some purchase option of an album at a
 * store in some specific music format.
 */
DROP PROCEDURE IF EXISTS p_modify_album_price_catalog;
DELIMITER //
CREATE PROCEDURE
p_modify_album_price_catalog(IN given_album_id VARCHAR(70),
								IN old_store_name VARCHAR(70),
								IN old_price DECIMAL(10, 2),
                                IN old_format VARCHAR(70),
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

/* For a purchase option of an album, modifies the music format
 * that it is being sold in to the given new music format.
 */
DROP PROCEDURE IF EXISTS p_modify_album_format_catalog;
DELIMITER //
CREATE PROCEDURE
p_modify_album_format_catalog(IN given_album_id VARCHAR(70),
								IN old_store_name VARCHAR(70),
								IN old_price DECIMAL(10, 2),
                                IN old_format VARCHAR(70),
                                IN new_format VARCHAR(70))
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

/* Removes the given album from the database.
 */
DROP PROCEDURE IF EXISTS p_delete_album;
DELIMITER //
CREATE PROCEDURE
p_delete_album(IN given_album_id VARCHAR(70))
BEGIN
	DELETE FROM
		album
	WHERE album_id = given_album_id;
END //
DELIMITER ;

/* Returns general song information for a given song
 */
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

/* Returns all artists for a given song.
 */
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

/* Returns all featured artists for a given song.
 */
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

/* Returns the names of all genres associated with
 * the given song.
 */
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

/* For the given song, changes the song name to the
 * given new song name.
 */
DROP PROCEDURE IF EXISTS p_modify_track_name;
DELIMITER //
CREATE PROCEDURE
p_modify_track_name(IN given_song_id INT,
						IN new_track_name VARCHAR(70))
BEGIN
	UPDATE general_song SET track_name = new_track_name
    WHERE song_id = given_song_id;
END //
DELIMITER ;

/* For a given song, modifies the track number of that
 * song to be the given new track number.
 */
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

/* Modifies which album a given song belongs to.
 */
DROP PROCEDURE IF EXISTS p_modify_song_album;
DELIMITER //
CREATE PROCEDURE
p_modify_song_album(IN given_song_id INT,
						IN new_album_name VARCHAR(70), IN release_date_of_album DATE)
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

/* Returns the release dates of all albums that have the
 * given name.
 */
DROP PROCEDURE IF EXISTS p_release_dates_of_album;
DELIMITER //
CREATE PROCEDURE
p_release_dates_of_album(IN given_album_name VARCHAR(70))
BEGIN
    SELECT
		release_date
	FROM
		album
	WHERE
		album_name = given_album_name;
END //
DELIMITER ;

/* Modifies the given artist of the given song to be another
 * artist.
 */
DROP PROCEDURE IF EXISTS p_modify_song_artist;
DELIMITER //
CREATE PROCEDURE
p_modify_song_artist(IN given_track_id INT,
						IN old_artist_name VARCHAR(70),
                        IN new_artist_name VARCHAR(70))
BEGIN
	UPDATE artists_of_songs SET artist_name = new_artist_name
    WHERE artist_name = old_artist_name
		AND song_id = given_track_id;
END //
DELIMITER ;

/* Modifies the given featured artist of the given song to be another
 * artist.
 */
DROP PROCEDURE IF EXISTS p_modify_song_ft_artist;
DELIMITER //
CREATE PROCEDURE
p_modify_song_ft_artist(IN given_track_id INT,
						IN old_ft_artist_name VARCHAR(70),
                        IN new_ft_artist_name VARCHAR(70))
BEGIN
	UPDATE featured_artists_of_songs SET featured_artist_name = new_ft_artist_name
    WHERE featured_artist_name = old_ft_artist_name
		AND song_id = given_track_id;
END //
DELIMITER ;

/* Modifies a specific genre of a specific song to be
 * set to another genre.
 */
DROP PROCEDURE IF EXISTS p_modify_song_genre;
DELIMITER //
CREATE PROCEDURE
p_modify_song_genre(IN given_track_id INT,
						IN old_genre_name VARCHAR(70),
                        IN new_genre_name VARCHAR(70))
BEGIN
	UPDATE genre_of_song SET genre_name = new_genre_name
    WHERE genre_name = old_genre_name
		AND song_id = given_track_id;
END //
DELIMITER ;

/* Removes the given song from the database.
 */
DROP PROCEDURE IF EXISTS p_delete_song;
DELIMITER //
CREATE PROCEDURE
p_delete_song(IN given_song_id INT)
BEGIN
	DELETE FROM
		general_song
	WHERE song_id = given_song_id;
END //
DELIMITER ;

/* Retrieves all information pertaining to Singles for the
 * given song.
 */
DROP PROCEDURE IF EXISTS p_base_single_info;
DELIMITER //
CREATE PROCEDURE
p_base_single_info(IN given_track_id INT)
BEGIN
	SELECT
		release_date, cover_art
	FROM
		single_song
	WHERE
		song_id = given_track_id;
END //
DELIMITER ;

/* Modifies the release date of the single to be the given
 * release date.
 */
DROP PROCEDURE IF EXISTS p_modify_single_release_date;
DELIMITER //
CREATE PROCEDURE
p_modify_single_release_date(IN given_track_id INT,
						IN new_release_date DATE)
BEGIN
	UPDATE single_song SET release_date = new_release_date
    WHERE song_id = given_track_id;
END //
DELIMITER ;

/* Retrieves the album id for the album given its primary
 * key information.
 */
DROP PROCEDURE IF EXISTS p_get_album_id;
DELIMITER //
CREATE PROCEDURE
p_get_album_id(IN given_album_name VARCHAR(70),
						IN given_album_release_date DATE)
BEGIN
	SELECT
		album_id
	FROM
		album
	WHERE
		album_name = given_album_name
			AND
		release_date = given_album_release_date;
END //
DELIMITER ;

/* Adds a non-single track to the database with the given attributes.
 * Returns the ID of the song.
 */
DROP PROCEDURE IF EXISTS p_add_basic_song;
DELIMITER //
CREATE PROCEDURE
p_add_basic_song(IN given_track_name VARCHAR(70),
						IN given_track_number INT,
                        IN given_album_id INT,
                        IN given_track_length INT,
                        IN given_lyrics_file_path VARCHAR(70),
                        IN given_audio_sample_file_path VARCHAR(70))
BEGIN
	INSERT 
		INTO general_song(track_name,lyrics,audio_sample,length_seconds,album_id,track_number)
	VALUES (
		given_track_name, 
        given_lyrics_file_path,
        given_audio_sample_file_path,
        given_track_length,
        given_album_id,
        given_track_number);
	
    SELECT
		song_id
	FROM
		general_song
	WHERE
		track_name = given_track_name
			AND
		album_id = given_album_id;
END //
DELIMITER ;

/* Speciifes that the given song is a Single and sets its Single
 * attributes.
 */
DROP PROCEDURE IF EXISTS p_add_single_song;
DELIMITER //
CREATE PROCEDURE
p_add_single_song(IN given_song_id INT,
						IN given_track_release_date DATE,
                        IN given_single_cover_art VARCHAR(70))
BEGIN
	INSERT INTO single_song(song_id,release_date,cover_art)
	VALUES (
		given_song_id,
        given_track_release_date,
        given_single_cover_art);
END //
DELIMITER ;

/* For a given song, sets that a given artist created that song.
 */
DROP PROCEDURE IF EXISTS p_add_song_artist;
DELIMITER //
CREATE PROCEDURE
p_add_song_artist(IN given_song_id INT,
					IN given_artist_name VARCHAR(70))
BEGIN
	INSERT INTO artists_of_songs(song_id, artist_name)
	VALUES (
		given_song_id,
        given_artist_name);
END //
DELIMITER ;

/* For a given song, sets that a given artist was featured in
 * that song.
 */
DROP PROCEDURE IF EXISTS p_add_song_ft_artist;
DELIMITER //
CREATE PROCEDURE
p_add_song_ft_artist(IN given_song_id INT,
					IN given_ft_artist_name VARCHAR(70))
BEGIN
	INSERT INTO featured_artists_of_songs(song_id, featured_artist_name)
	VALUES (
		given_song_id,
        given_ft_artist_name);
END //
DELIMITER ;

/* For a given song, sets that a given genre describes that song.
 */
DROP PROCEDURE IF EXISTS p_add_song_genre;
DELIMITER //
CREATE PROCEDURE
p_add_song_genre(IN given_song_id INT,
					IN given_genre_name VARCHAR(70))
BEGIN
	INSERT INTO genre_of_song(song_id, genre_name)
	VALUES (
		given_song_id,
        given_genre_name);
END //
DELIMITER ;

/* Adds an artist with the given name to the database.
 */
DROP PROCEDURE IF EXISTS p_add_artist;
DELIMITER //
CREATE PROCEDURE
p_add_artist(IN new_artist_name VARCHAR(70))
BEGIN
	INSERT INTO artist(artist_name)
	VALUES (
		new_artist_name);
END //
DELIMITER ;

/* Adds an album to the database with the given attributes.
 */
DROP PROCEDURE IF EXISTS p_add_album;
DELIMITER //
CREATE PROCEDURE
p_add_album(IN in_album_name VARCHAR(70),
				IN in_release_date DATE,
				IN in_album_art_file_path VARCHAR(70))
BEGIN
	INSERT INTO album(album_name, release_date, album_cover)
	VALUES (
		in_album_name,
        in_release_date,
        in_album_art_file_path);
END //
DELIMITER ;

/* Specifies that the given album was created by the given artist.
 */
DROP PROCEDURE IF EXISTS p_add_album_artist;
DELIMITER //
CREATE PROCEDURE
p_add_album_artist(IN in_album_id INT,
					IN in_artist_name VARCHAR(70))
BEGIN
	INSERT INTO artists_of_albums(album_id, artist_name)
	VALUES (
		in_album_id,
        in_artist_name);
END //
DELIMITER ;

/* For a given album, sets that the given genre describes that album.
 */
DROP PROCEDURE IF EXISTS p_add_album_genre;
DELIMITER //
CREATE PROCEDURE
p_add_album_genre(IN in_album_id INT,
					IN in_genre_name VARCHAR(70))
BEGIN
	INSERT INTO genre_of_album(album_id, genre_name)
	VALUES (
		in_album_id,
        in_genre_name);
END //
DELIMITER ;

/* For a given album, produces a distribution of that album being
 * distributed in a specific format. Then sets that distribution to
 * be sold at the given store with the given id.
 */
DROP PROCEDURE IF EXISTS p_add_album_distribution_and_pricing;
DELIMITER //
CREATE PROCEDURE
p_add_album_distribution_and_pricing(IN in_album_id INT,
										IN in_store_name VARCHAR(70),
                                        IN in_price DECIMAL(10, 2),
                                        IN in_format VARCHAR(70))
BEGIN

	DECLARE new_distribution_id INT;
    
	INSERT INTO format_of_album(album_id, format_name)
	VALUES (
		in_album_id,
        in_format);
        
    SET new_distribution_id = 
		(SELECT
			distribution_id
		FROM
			format_of_album
		WHERE
			album_id = in_album_id
				AND
			format_name = in_format);
	
    INSERT INTO store_catalog(store_name, distribution_id, album_price)
    VALUES (
		in_store_name,
        new_distribution_id,
        in_price);
        
	
END //
DELIMITER ;

/* Adds a genre with the given name to the database.
 */
DROP PROCEDURE IF EXISTS p_add_genre;
DELIMITER //
CREATE PROCEDURE
p_add_genre(IN in_genre_name VARCHAR(70))
BEGIN
	INSERT INTO genre(genre_name)
	VALUES (in_genre_name);
END //
DELIMITER ;

/* Adds a store with the given name to the database.
 */
DROP PROCEDURE IF EXISTS p_add_store;
DELIMITER //
CREATE PROCEDURE
p_add_store(IN in_store_name VARCHAR(70))
BEGIN
	INSERT INTO store(store_name)
	VALUES (in_store_name);
END //
DELIMITER ;

/* Adds a music format with the given name to the database.
 */
DROP PROCEDURE IF EXISTS p_add_format;
DELIMITER //
CREATE PROCEDURE
p_add_format(IN in_format_name VARCHAR(70))
BEGIN
	INSERT INTO music_format(format_name)
	VALUES (in_format_name);
END //
DELIMITER ;

