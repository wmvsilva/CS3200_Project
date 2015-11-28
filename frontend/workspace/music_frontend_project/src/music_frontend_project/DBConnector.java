package music_frontend_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.javatuples.Pair;
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
				.prepareStatement("CALL genres_of_album(" + albumId + ")");
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
				.prepareStatement("CALL genres_of_album(" + albumId + ")");
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
				.prepareStatement("CALL album_store_info(" + albumId + ")");
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(new Triplet<String, Double, String>(resultSet
						.getString(1), resultSet.getDouble(2), resultSet
						.getString(3)));
			}
		}

		return result;
	}
}
