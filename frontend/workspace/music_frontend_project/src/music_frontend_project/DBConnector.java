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
}
