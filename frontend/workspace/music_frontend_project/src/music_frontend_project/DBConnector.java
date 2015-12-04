package music_frontend_project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.javatuples.Pair;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

public class DBConnector {

	private Connection conn = null;

	private boolean connected = false;

	private String errorMsg = null;

	private String disconnectError = null;

	public void connectToDB(String username, String password,
			String serverName, int portNumber, String dbName) {
		// Set connection properties
		Properties connectionProps = new Properties();
		connectionProps.put("user", username);
		connectionProps.put("password", password);

		// Connect to the database
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + serverName
					+ ":" + portNumber + "/" + dbName, connectionProps);
		} catch (SQLException e) {
			errorMsg = e.getMessage();
		}

		connected = true;
	}

	public boolean isConnected() {
		return connected;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public boolean disconnectFromDB() {
		try {
			conn.close();
		} catch (SQLException e) {
			disconnectError = e.getMessage();
			return false;
		}
		return true;
	}

	public String getDisconnectError() {
		return disconnectError;
	}

	public List<String> searchArtists(String userInput) throws SQLException {
		List<String> result = new LinkedList<String>();
		try (PreparedStatement statement = conn
				.prepareStatement("CALL search_artist('" + userInput + "')");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(resultSet.getString(1));
			}
		}

		return result;
	}

	public List<Triplet<String, String, Integer>> searchAlbums(String userInput)
			throws SQLException {
		List<Triplet<String, String, Integer>> result = new LinkedList<Triplet<String, String, Integer>>();

		try (PreparedStatement statement = conn
				.prepareStatement("CALL search_album('" + userInput + "')");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(new Triplet<String, String, Integer>(resultSet
						.getString(1), resultSet.getString(2), resultSet
						.getInt(3)));
			}
		}

		return result;
	}

	public List<Triplet<String, String, Integer>> searchSongs(String userInput)
			throws SQLException {
		List<Triplet<String, String, Integer>> result = new LinkedList<Triplet<String, String, Integer>>();

		try (PreparedStatement statement = conn
				.prepareStatement("CALL search_song('" + userInput + "')");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(new Triplet<String, String, Integer>(resultSet
						.getString(1), resultSet.getString(2), resultSet
						.getInt(3)));
			}
		}

		return result;
	}

	public List<Pair<String, Integer>> getAlbumsByArtist(String artist)
			throws SQLException {
		List<Pair<String, Integer>> result = new LinkedList<Pair<String, Integer>>();

		try (PreparedStatement statement = conn
				.prepareStatement("CALL albums_by_artist('" + artist + "')");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(new Pair<String, Integer>(resultSet.getString(1),
						resultSet.getInt(2)));
			}
		}

		return result;
	}

	public void modifyArtist(String artist, String userChange)
			throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL modify_artist('" + artist + "', '"
						+ userChange + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void deleteArtist(String artist) throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL delete_artist('" + artist + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public Triplet<String, String, String> getAlbumInfo(Integer albumId)
			throws SQLException {
		Triplet<String, String, String> result;
		// Album Name, Release Date, Album Art
		try (PreparedStatement statement = conn
				.prepareStatement("CALL basic_album_info(" + albumId + ")");
				ResultSet resultSet = statement.executeQuery()) {
			resultSet.next();
			result = new Triplet<String, String, String>(
					resultSet.getString(1), resultSet.getString(2),
					resultSet.getString(3));
		}

		return result;
	}

	public List<String> getArtistsOfAlbum(Integer albumId) throws SQLException {
		List<String> result = new LinkedList<String>();
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_artists_of_album(" + albumId + ")");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(resultSet.getString(1));
			}
		}

		return result;
	}

	public List<String> getGenresOfAlbum(Integer albumId) throws SQLException {
		List<String> result = new LinkedList<String>();
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_genres_of_album(" + albumId + ")");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(resultSet.getString(1));
			}
		}

		return result;
	}

	public List<Pair<String, Integer>> getAlbumTracks(Integer albumId)
			throws SQLException {
		List<Pair<String, Integer>> result = new LinkedList<Pair<String, Integer>>();

		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_tracks_of_album(" + albumId + ")");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(new Pair<String, Integer>(resultSet.getString(1),
						resultSet.getInt(2)));
			}
		}

		return result;
	}

	public List<Triplet<String, Double, String>> getAlbumStoreInfo(
			Integer albumId) throws SQLException {
		List<Triplet<String, Double, String>> result = new LinkedList<Triplet<String, Double, String>>();

		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_album_store_info(" + albumId + ")");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(new Triplet<String, Double, String>(resultSet
						.getString(1), resultSet.getDouble(2), resultSet
						.getString(3)));
			}
		}

		return result;
	}

	public void modifyAlbumName(Integer albumId, String newAlbumName)
			throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_album_name(" + albumId + ",'"
						+ newAlbumName + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void modifyAlbumReleaseDate(Integer albumId, String newReleaseDate)
			throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_album_release_date(" + albumId
						+ ",'" + newReleaseDate + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void modifyAlbumArtist(Integer albumId, String oldArtistName,
			String newArtistName) throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_album_artist(" + albumId
						+ ",'" + oldArtistName + "', '" + newArtistName + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void modifyAlbumGenre(Integer albumId, String oldGenreName,
			String newGenreName) throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_album_genre(" + albumId + ",'"
						+ oldGenreName + "', '" + newGenreName + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void modifyAlbumStoreCatalog(Integer albumId, String oldStoreName,
			Double oldPrice, String oldFormat, String newValue)
			throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_album_store_catalog("
						+ albumId + ",'" + oldStoreName + "', '" + oldPrice
						+ "', '" + oldFormat + "', '" + newValue + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void modifyPriceCatalog(Integer albumId, String oldStoreName,
			Double oldPrice, String oldFormat, String newValue)
			throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_album_price_catalog("
						+ albumId + ",'" + oldStoreName + "', '" + oldPrice
						+ "', '" + oldFormat + "', '" + newValue + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void modifyFormatCatalog(Integer albumId, String oldStoreName,
			Double oldPrice, String oldFormat, String newValue)
			throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_album_format_catalog("
						+ albumId + ",'" + oldStoreName + "', '" + oldPrice
						+ "', '" + oldFormat + "', '" + newValue + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void deleteAlbum(Integer albumId) throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_delete_album(" + albumId + ")");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	// Track Name, Track Number, Album Name, Album id, Lyric filepath,
	// Sample filepath
	public Sextet<String, Integer, String, Integer, String, String> getBaseSongInfo(
			int trackId) throws SQLException {
		Sextet<String, Integer, String, Integer, String, String> result;

		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_base_song_info(" + trackId + ")");
				ResultSet resultSet = statement.executeQuery()) {
			resultSet.next();
			result = new Sextet<String, Integer, String, Integer, String, String>(
					resultSet.getString(1), resultSet.getInt(2),
					resultSet.getString(3), resultSet.getInt(4),
					resultSet.getString(5), resultSet.getString(6));
		}

		return result;
	}

	public List<String> getSongArtists(int trackId) throws SQLException {
		List<String> result = new LinkedList<String>();

		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_song_artists(" + trackId + ")");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(resultSet.getString(1));
			}
		}

		return result;
	}

	public List<String> getSongFtArtists(int trackId) throws SQLException {
		List<String> result = new LinkedList<String>();

		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_song_ft_artists(" + trackId + ")");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(resultSet.getString(1));
			}
		}

		return result;
	}

	public List<String> getGenres(int trackId) throws SQLException {
		List<String> result = new LinkedList<String>();

		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_song_genres(" + trackId + ")");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(resultSet.getString(1));
			}
		}

		return result;
	}

	public boolean isSingle(int trackId) throws SQLException {
		CallableStatement cStmt = conn
				.prepareCall("{? = call f_is_song_single(?)}");
		cStmt.registerOutParameter(1, java.sql.Types.BOOLEAN);
		cStmt.setInt(2, trackId);
		cStmt.execute();
		Boolean outputValue = cStmt.getBoolean(1);

		return outputValue;
	}

	public void modifyTrackName(int trackId, String newTrackName)
			throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_track_name(" + trackId + ", '"
						+ newTrackName + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void modifyTrackNumber(int trackId, String newTrackNumber)
			throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_track_number(" + trackId
						+ ", " + newTrackNumber + ")");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void modifySongAlbum(int trackId, String newAlbum,
			String newReleaseDate) throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_song_album(" + trackId + ", '"
						+ newAlbum + "', '" + newReleaseDate + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void modifySongArtist(int trackId, String oldArtist, String newArtist)
			throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_song_artist(" + trackId
						+ ", '" + oldArtist + "', '" + newArtist + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void modifySongFtArtist(int trackId, String oldFtArtist,
			String newFtArtist) throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_song_ft_artist(" + trackId
						+ ", '" + oldFtArtist + "', '" + newFtArtist + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void modifySongGenre(int trackId, String oldGenre, String newGenre)
			throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_song_genre(" + trackId + ", '"
						+ oldGenre + "', '" + newGenre + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public List<String> getReleaseDatesOfAlbum(String newAlbumName)
			throws SQLException {
		List<String> result = new LinkedList<String>();
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_release_dates_of_album('"
						+ newAlbumName + "')");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(resultSet.getString(1));
			}
		}

		return result;
	}

	public void deleteSong(int trackId) throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_delete_song(" + trackId + ")");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public Pair<String, String> getSingleSongInfo(int trackId)
			throws SQLException {
		Pair<String, String> result = null;

		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_base_single_info(" + trackId + ")");
				ResultSet resultSet = statement.executeQuery()) {
			resultSet.next();
			result = new Pair<String, String>(resultSet.getString(1),
					resultSet.getString(2));
		}

		return result;
	}

	public void modifySingleReleaseDate(int trackId, String newReleaseDate)
			throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_modify_single_release_date("
						+ trackId + ", '" + newReleaseDate + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void addNewArtist(String newArtist) throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_add_artist('" + newArtist + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void addNewGenre(String newGenre) throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_add_genre('" + newGenre + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void addNewStore(String newStoreName) throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_add_store('" + newStoreName + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void addNewFormat(String newFormatName) throws SQLException {
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_add_format('" + newFormatName + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}
	}

	public void addAlbum(String albumName, List<String> artists,
			List<String> genres, String releaseDate,
			List<Triplet<String, String, String>> storesPricesFormats,
			String albumArtFilePath) throws SQLException {
		// Add the album
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_add_album('" + albumName + "', '"
						+ releaseDate + "', '" + albumArtFilePath + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}

		// Find the albumId
		int albumId = getAlbumId(albumName, releaseDate);

		// Add the artists
		for (String artist : artists) {
			try (PreparedStatement statement = conn
					.prepareStatement("CALL p_add_album_artist(" + albumId
							+ ", '" + artist + "')");
					ResultSet resultSet = statement.executeQuery()) {
			}
		}

		// Add the genres
		for (String genre : genres) {
			try (PreparedStatement statement = conn
					.prepareStatement("CALL p_add_album_genre(" + albumId
							+ ", '" + genre + "')");
					ResultSet resultSet = statement.executeQuery()) {
			}
		}
		// Add the store, prices, formats
		for (Triplet<String, String, String> storePriceFormat : storesPricesFormats) {
			String storeName = storePriceFormat.getValue0();
			String price = storePriceFormat.getValue1();
			String format = storePriceFormat.getValue2();
			String sqlStatement = "CALL p_add_album_distribution_and_pricing("
					+ albumId + ", '" + storeName + "', " + price + ", '"
					+ format + "')";
			try (PreparedStatement statement = conn
					.prepareStatement(sqlStatement);
					ResultSet resultSet = statement.executeQuery()) {
			}
		}
	}

	private int getAlbumId(String albumName, String releaseDate)
			throws SQLException {
		Integer result = null;

		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_get_album_id('" + albumName + "', '"
						+ releaseDate + "')");
				ResultSet resultSet = statement.executeQuery()) {
			resultSet.next();
			result = resultSet.getInt(1);
		}

		return result;
	}

	public void addSingle(String trackName, String trackNumber,
			String albumName, String albumReleaseDate, List<String> artists,
			List<String> ftArtists, List<String> genres, String lyricsFilePath,
			String audioSampleFilePath, String releaseDate, String coverArt,
			String trackLengthSeconds) throws SQLException {

		int albumId = getAlbumId(albumName, albumReleaseDate);
		Integer songId = null;
		// Add the base song information
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_add_basic_song(\"" + trackName
						+ "\", " + trackNumber + ", " + albumId + ", "
						+ trackLengthSeconds + ", \"" + lyricsFilePath
						+ "\", \"" + audioSampleFilePath + "\")");
				ResultSet resultSet = statement.executeQuery()) {
			resultSet.next();
			songId = resultSet.getInt(1);
		}

		// Add the single song information
		try (PreparedStatement statement = conn
				.prepareStatement("CALL p_add_single_song(" + songId + ", '"
						+ releaseDate + "', '" + coverArt + "')");
				ResultSet resultSet = statement.executeQuery()) {
		}

		// Add the artists
		for (String artist : artists) {
			try (PreparedStatement statement = conn
					.prepareStatement("CALL p_add_song_artist(" + songId
							+ ", '" + artist + "')");
					ResultSet resultSet = statement.executeQuery()) {
			}
		}

		// Add the ft artists
		for (String ftArtist : ftArtists) {
			try (PreparedStatement statement = conn
					.prepareStatement("CALL p_add_song_ft_artist(" + songId
							+ ", '" + ftArtist + "')");
					ResultSet resultSet = statement.executeQuery()) {
			}
		}

		// Add the genres
		for (String genre : genres) {
			try (PreparedStatement statement = conn
					.prepareStatement("CALL p_add_song_genre(" + songId + ", '"
							+ genre + "')");
					ResultSet resultSet = statement.executeQuery()) {
			}
		}
	}

	public void addTrack(String trackName, String trackNumber,
			String albumName, String albumReleaseDate, List<String> artists,
			List<String> ftArtists, List<String> genres, String lyricsFilePath,
			String audioSampleFilePath, String trackLengthSeconds)
			throws SQLException {

		int albumId = getAlbumId(albumName, albumReleaseDate);
		Integer songId = null;
		// Add the base song information
		String strStmt = "CALL p_add_basic_song(\"" + trackName + "\", "
				+ trackNumber + ", " + albumId + ", " + trackLengthSeconds
				+ ", \"" + lyricsFilePath + "\", \"" + audioSampleFilePath
				+ "\")";
		try (PreparedStatement statement = conn.prepareStatement(strStmt);
				ResultSet resultSet = statement.executeQuery()) {
			resultSet.next();
			songId = resultSet.getInt(1);
		}

		// Add the artists
		for (String artist : artists) {
			try (PreparedStatement statement = conn
					.prepareStatement("CALL p_add_song_artist(" + songId
							+ ", '" + artist + "')");
					ResultSet resultSet = statement.executeQuery()) {
			}
		}

		// Add the ft artists
		for (String ftArtist : ftArtists) {
			try (PreparedStatement statement = conn
					.prepareStatement("CALL p_add_song_ft_artist(" + songId
							+ ", '" + ftArtist + "')");
					ResultSet resultSet = statement.executeQuery()) {
			}
		}

		// Add the genres
		for (String genre : genres) {
			try (PreparedStatement statement = conn
					.prepareStatement("CALL p_add_song_genre(" + songId + ", '"
							+ genre + "')");
					ResultSet resultSet = statement.executeQuery()) {
			}
		}

	}

	public boolean doesArtistExist(String newArtist) throws SQLException {
		CallableStatement cStmt = conn
				.prepareCall("{? = call f_does_artist_exist(?)}");
		cStmt.registerOutParameter(1, java.sql.Types.BOOLEAN);
		cStmt.setString(2, newArtist);
		cStmt.execute();
		Boolean outputValue = cStmt.getBoolean(1);

		return outputValue;
	}

	public boolean doesGenreExist(int trackId, String oldGenre, String newGenre)
			throws SQLException {

		CallableStatement cStmt = conn
				.prepareCall("{? = call f_does_genre_exist(?)}");
		cStmt.registerOutParameter(1, java.sql.Types.BOOLEAN);
		cStmt.setString(2, newGenre);
		cStmt.execute();
		Boolean outputValue = cStmt.getBoolean(1);

		return outputValue;
	}
}
