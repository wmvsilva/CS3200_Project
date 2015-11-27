package music_frontend_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
}
