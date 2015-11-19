/*
William Silva, Nathaniel Paradis
Professor Durant
CS 3200
7 December 2015

MySQL Database Schema for Final Project
*/

CREATE DATABASE IF NOT EXISTS music /*!40100 DEFAULT CHARACTER SET utf8 */;
USE music;

# Drops tables in reverse order they were added.
DROP TABLE IF EXISTS store_catalog;
DROP TABLE IF EXISTS format_of_album;
DROP TABLE IF EXISTS genre_of_album;
DROP TABLE IF EXISTS artists_of_albums;
DROP TABLE IF EXISTS genre_of_song;
DROP TABLE IF EXISTS featured_artists_of_songs;
DROP TABLE IF EXISTS artists_of_songs;
DROP TABLE IF EXISTS store;
DROP TABLE IF EXISTS music_format;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS artist;
DROP TABLE IF EXISTS album_song;
DROP TABLE IF EXISTS single_song;
DROP TABLE IF EXISTS general_song;
DROP TABLE IF EXISTS album;

######################################
######################################
# Entities
######################################
######################################

/*
 * album table contains music album attributes
 */
CREATE TABLE album
(
	# album_id is a generated primary key. It is used rather than the composite
    # key of album_name and release_date to simplify foreign key references to
    # albums
	album_id INT NOT NULL AUTO_INCREMENT,
    # The name of the album
	album_name VARCHAR(70) NOT NULL,
    # The date that the album was originally released
    release_date DATE NOT NULL,
    # A filepath that leads to an image file of the album's cover art
    album_cover VARCHAR(512) NULL,
    
    # Auto_incremented field is used as primary key
    CONSTRAINT
		pk_album_album_id
	PRIMARY KEY ( album_id ),
    
    # The album's name and release date should uniquely identify it
    CONSTRAINT
		uk_album_album_name_release_date
	UNIQUE KEY ( album_name, release_date )
    
) ENGINE = INNODB;

/*
 * general_song table acts as a superclass for the single_song 
 * table. It contains all general information about a
 * song.
 */
CREATE TABLE general_song
(
	# Auto_generated field to be used as a primary key rather than
    # the composite key of track_name and album_id.
	song_id INT NOT NULL AUTO_INCREMENT,
    # The name of the song
	track_name VARCHAR(70) NOT NULL,
    # The entire lyrics of the song, including newlines
    lyrics TEXT NULL,
    # Filepath to an audio file of a short sample of the song
    audio_sample VARCHAR(512) NULL,
    # The length of the song in seconds
    length_seconds SMALLINT UNSIGNED NULL,
    # The associated album of this song
    album_id INT NOT NULL,
    # The number of the track within the album. Given our
    # assumptions, there should always be a track number
    track_number TINYINT UNSIGNED NOT NULL,
    
    # Auto_incremented field is used as primary key
    CONSTRAINT
		pk_general_song_song_id
    PRIMARY KEY ( song_id ),
    
    # Each track within an album will have a unique name
	CONSTRAINT
		uk_general_song_track_name_album_id
	UNIQUE KEY ( track_name, album_id ),
    
    # album_id refers to the album table
    # If the album is updated, the reference should also
    # be updated.
    # Album deletion is restricted to prevent data loss of
    # songs. One should delete all songs in an album before
    # they delete the album.
    CONSTRAINT
		fk_general_song_album_album_id
    FOREIGN KEY ( album_id)
		REFERENCES album(album_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
        
) ENGINE = INNODB;

/*
 * single_song table acts as a subclass for the general_song 
 * table. It contains all information that is specific to a song
 * released as a single.
 */
CREATE TABLE single_song
(
	# Reference to the general_song table that provides more
    # general information
	song_id INT NOT NULL,
    # The date that the single was released
    release_date DATE NULL,
    # File path to an image file of the cover art for this single
    cover_art VARCHAR(512) NULL,
    
    # Each row contains information pertaining to a unique song
    CONSTRAINT
		pk_single_song_song_id
	PRIMARY KEY ( song_id ),
    
    # References song_id in general_song
    # If the general song information is updated/removed,
    # then this should also be updated/removed.
    CONSTRAINT
		fk_single_song_general_song_song_id
	FOREIGN KEY ( song_id )
		REFERENCES general_song( song_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE
        
) ENGINE = INNODB;

/*
 * artist table contains information about a music artist who performs
 * songs. Currently, this is just limited to the name but a larger project
 * could include complete biographical information.
 */
CREATE TABLE artist
(
	# The stage name of the artist
	artist_name VARCHAR(70) NOT NULL,
    
    # The stage name of the artist is assumed to be unique.
    CONSTRAINT
		pk_artist_artist_name
    PRIMARY KEY ( artist_name)
    
) ENGINE = INNODB;

/*
 * genre table contains information about a musical genre. Currently, this
 * just contains the name of the genre but a larger project could include
 * genre history, related genres, etc.
 */
CREATE TABLE genre
(
	# The most common name of the genre
	genre_name VARCHAR(70) NOT NULL,
    
    # Each genre is represented once in the table
    CONSTRAINT
		pk_genre_genre_name
    PRIMARY KEY ( genre_name )
    
) ENGINE = INNODB;

/*
 * music_format table contains information about the distribution format of
 * albums. Examples include digitial, CD, record, etc.
 */
CREATE TABLE music_format
(
	# The most common name of the distribution format
	format_name VARCHAR(70) NOT NULL,
    
    # Each distribution format has a unique name
    CONSTRAINT
		pk_music_format_format_name
    PRIMARY KEY ( format_name)
    
) ENGINE = INNODB;

/*
 * store table contains information about stores that sell albums.
 * If this project were expanded, this table would definitely include
 * more fields such as store logo, link to store, etc.
 */
CREATE TABLE store
(
	# The company name
	store_name VARCHAR(70) NOT NULL,
    
    # Each name of a company is unique
    CONSTRAINT
		pk_store_store_name
    PRIMARY KEY ( store_name)
    
) ENGINE = INNODB;

######################################
######################################
# Relationships
######################################
######################################

/*
 * artists_of_songs table contains which artists have authored
 * which songs. This is a many-to-many relationship. Songs
 * should have at least one artist here. Due to limitations of
 * MySQL (lack of deferred constraints), total participation
 * on the 'many' side of a relationship is not possible.
 * Therefore, it is up to the user/application to enforce this.
 */
CREATE TABLE artists_of_songs
(
	# The id of a song
	song_id INT NOT NULL,
    # The name of the artist who authored the song
    artist_name VARCHAR(70) NOT NULL,
    
    # Each song can have many artists and each artist can have
    # many songs
    CONSTRAINT
		pk_artists_of_songs_song_id_artist_name
    PRIMARY KEY ( song_id, artist_name ),
    
    # References song table
    # If songs are updated/deleted, information about
    # who wrote them should also be updated/deleted.
	CONSTRAINT
		fk_artists_of_songs_general_song_song_id
	FOREIGN KEY ( song_id )
		REFERENCES general_song( song_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    # References artist table
    # If an artist is updated, it should be updated here to.
    # If an artist is deleted, the fact that the artist made
    # this song is no longer relevant information and should
    # be deleted.
	CONSTRAINT
		fk_artists_of_songs_artist_artist_name
	FOREIGN KEY ( artist_name )
		REFERENCES artist( artist_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

/*
 * featured_artists_of_songs table contains which artists
 * have been featured in which songs. For instance, the song
 * 'Daft Punk - Get Lucky ft. Pharrell Williams' features
 * Pharrel Williams so this table would contain a reference
 * to Get Lucky and Pharrel Williams.
 * Songs can have many featured artists and featured artists
 * can have many songs.
 */
CREATE TABLE featured_artists_of_songs
(
	# The song with a featured artist
	song_id INT NOT NULL,
    # Name of the featured artist
    featured_artist_name VARCHAR(70) NOT NULL,
    
    # Song can have many ft artists and ft artists can be in
    # many songs
    CONSTRAINT
		pk_featured_artists_of_songs_song_id_ft_artist_name
    PRIMARY KEY ( song_id, featured_artist_name ),
    
    # References general_song table
    # If a song is updated/deleted, we should also
    # update the information here or delete it.
	CONSTRAINT
		fk_featured_artists_of_songs_general_song_song_id
	FOREIGN KEY ( song_id )
		REFERENCES general_song( song_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,

	# References artist table
    # If an artist is updated/deleted, changes
    # should be reflected here.
	CONSTRAINT
		fk_featured_artists_of_songs_artist_ft_artist_name
	FOREIGN KEY ( featured_artist_name )
		REFERENCES artist( artist_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

/*
 * genre_of_song table contains the information about which songs
 * have what genres. This is a many-to-many relationship as songs
 * can have many genres and genres can have many songs. Songs
 * should have at least one genre but this cannot be enforced using
 * MySQL due to its limitations. It is up to the user/application
 * to enforce this.
 */
CREATE TABLE genre_of_song
(
	# The id of the song
	song_id INT NOT NULL,
    # The name of the genre
    genre_name VARCHAR(70) NOT NULL,
    
    # Each song can have many genres and each genre can have many songs
    CONSTRAINT
		pk_genre_of_song_song_id_genre_name
    PRIMARY KEY ( song_id, genre_name ),
    
    # References song table
    # If a song is updated/deleted, all related information about
    # that song should also receive these changes
	CONSTRAINT
		fk_genre_of_song_general_song_song_id
	FOREIGN KEY ( song_id )
		REFERENCES general_song( song_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
	
    # References song table
    # If a genre is updated/deleted, this information change
    # should cascade to all related data.
	CONSTRAINT
		fk_genre_of_song_genre_genre_name
	FOREIGN KEY ( genre_name )
		REFERENCES genre( genre_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

/*
 * artists_of_albums table contains which albums have which artists.
 * This is a separate table from the artists_of_songs table because
 * the artist(s) of a full album are often different than the artists
 * of the tracks within it.
 * This is a many-to-many relationship as each artist can have multiple
 * albums and each album can have multiple artists.
 * Each album should have at least one artist but this cannot be enforced
 * MySQL and is therefore up to the user/application.
 */
CREATE TABLE artists_of_albums
(
	# The id of the album
	album_id INT NOT NULL,
    # The name of the artist who create the album
    artist_name VARCHAR(70) NOT NULL,
    
    # Each artist can have multiple albums and each album
    # can have multiple artists
    CONSTRAINT
		pk_artists_of_albums_album_id_artist_name
    PRIMARY KEY ( album_id, artist_name ),
    
    # References album table
    # If an album is deleted/updated, information about
    # who wrote it should get these changes.
	CONSTRAINT
		fk_artists_of_albums_album_album_id
	FOREIGN KEY ( album_id )
		REFERENCES album( album_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
	
    # References artist table
    # If an artist is updated/deleted, information about
    # what albums he or she made should get the changes.
	CONSTRAINT
		fk_artists_of_albumss_artist_artist_name
	FOREIGN KEY ( artist_name )
		REFERENCES artist( artist_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

/*
 * genre_of_album table contains information about which albums have
 * what genres. This included in addition to the genre_of_song table
 * because the genre of an entire album is usually more broad than
 * the genres of the individual songs within an album
 * This is a many-to-many relationship as albums can have many genres
 * and genres can have many albums.
 * Note that an album should have at least one genre. This cannot be
 * enforced by MySQL and is up to the user/application to enforce.
 */
CREATE TABLE genre_of_album
(
	# The id of the album
	album_id INT NOT NULL,
    # The name of a genre of the album
    genre_name VARCHAR(70) NOT NULL,
    
    # An album can have many genres and a genre can have many albums
    CONSTRAINT
		pk_genre_of_album_album_id_genre_name
    PRIMARY KEY ( album_id, genre_name ),
    
    # References album table
    # If an album is updated/deleted, information about the genre
    # of the album should get these changes.
	CONSTRAINT
		fk_genre_of_album_album_album_id
	FOREIGN KEY ( album_id )
		REFERENCES album( album_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
	
    # References genre table
    # If a genre is updated/deleted, all references to it should
    # also be updated/removed.
	CONSTRAINT
		fk_genre_of_album_genre_genre_name
	FOREIGN KEY ( genre_name )
		REFERENCES genre( genre_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

/*
 * format_of_album table describes what formats albums can be
 * distributed in. An album should be distributed in at least
 * one format and it is up to the user/application to enforce
 * this.
 */
CREATE TABLE format_of_album
(
	# Auto_generated field to act as a primary key so that
    # tables referring to this only need to have one column
	distribution_id INT NOT NULL AUTO_INCREMENT,
    # The id of the album
	album_id INT NOT NULL,
    # The name of the format that the album is distributed in
    format_name VARCHAR(70) NOT NULL,
    
    # Autogenerated primary key
    CONSTRAINT
		pk_format_of_album_distribution_id
    PRIMARY KEY ( distribution_id ),
    
    # Album id and format name are a composite key
	CONSTRAINT
		uk_format_of_album_album_id_format_name
	UNIQUE KEY ( album_id, format_name ),
    
    # References album table
    # If an album is updated/deleted, the way it
    # is distributed should be updated/deleted
    # as well
	CONSTRAINT
		fk_format_of_album_album__album_id
	FOREIGN KEY ( album_id )
		REFERENCES album( album_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
	
    # References music_format table
    # If information about a music_format is updated/deleted,
    # it should be updated/deleted here as well.
	CONSTRAINT
		fk_format_of_album_music_format_format_name
	FOREIGN KEY ( format_name )
		REFERENCES music_format( format_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;

/*
 * store_catalog table describes a catalog of a store selling albums
 * at given prices. Currently, this only contains the price information
 * but enhancements to this project could include processing time,
 * link to price, reviews, etc.
 */
CREATE TABLE store_catalog
(
	# The name of the store selling the album
	store_name VARCHAR(70) NOT NULL,
    # The format of the album being sold
	distribution_id INT NOT NULL,
    # The price of the album
    album_price DECIMAL(10, 2) NOT NULL,
    
    # Each store sells can sell many albums but for each album,
    # it only sells an album at one price
    CONSTRAINT
		pk_store_catalog_store_name_distribution_id
    PRIMARY KEY ( store_name, distribution_id ),
    
    # References store table
    # If a store is updated/deleted, all information
    # about that store should be cascaded.
	CONSTRAINT
		fk_store_catalog_store_store_name
	FOREIGN KEY ( store_name )
		REFERENCES store( store_name )
        ON DELETE CASCADE
        ON UPDATE CASCADE,
	
    # References format_of_album table
    # If a distribution is updated/deleted, all information
    # should be cascaded.
	CONSTRAINT
		fk_store_catalog_format_of_album_distribution_id
	FOREIGN KEY ( distribution_id )
		REFERENCES format_of_album( distribution_id )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    
) ENGINE = INNODB;
