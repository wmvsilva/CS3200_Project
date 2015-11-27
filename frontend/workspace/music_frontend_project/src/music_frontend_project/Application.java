package music_frontend_project;

import java.io.IOException;

/**
 * Application to assist some user in interacting with a music database. The
 * user will be able to:
 * 
 * @1- Search the database for songs, albums, and artists. By selecting one of
 *     these, the user can display further information about that entity and
 *     also modify that entity.
 * @2- Add new items to the search database such as songs, artists, albums,
 *     genres, stores, and formats.
 * 
 */
public class Application {

	/** The user name of the mySQL database */
	private String username;

	/** The password of the mySQL database */
	private String password;

	/** The computer running MySQL */
	private String serverName;

	/** The port of the MySQL server to connect to */
	private int portNumber;

	/** The name of the mySQL database */
	private String dbName;

	/** Handles interaction with the mySQL database */
	private DBConnector dbConn = new DBConnector();

	/**
	 * Getter for username
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Accessor for username
	 * 
	 * @param username
	 *            the username to use to create the connection
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter for password
	 * 
	 * @return the password of the database
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 *            password of the database to use to create the connection
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for server name
	 * 
	 * @return the server name to use to create the database connection
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * Setter for server name
	 * 
	 * @param serverName
	 *            the server name to use to create the database connection
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * Getter for portNumber
	 * 
	 * @return the portNumber of the DB that is to be connected to
	 */
	public int getPortNumber() {
		return portNumber;
	}

	/**
	 * Setter for portNumber
	 * 
	 * @param portNumber
	 *            the integer port number to connect to the database with
	 */
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * Getter for dbName
	 * 
	 * @return the name of the database to connect to
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * Setter for dbName
	 * 
	 * @param dbName
	 *            the name of the database to be connected to
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * Runs the application which connects to a database and provides a menu
	 * system for the user to navigate to interact with the database
	 */
	public void run() {
		printDatabaseConfig();
		connectToDBOrExit();
		try {
			userMenus();
		} catch (IOException e) {
			Printer.err(e.getMessage());
			Printer.err("There was an IO issue.");
		}
		exit(0);
	}

	private void userMenus() throws IOException {
		MenuSystem.mainMenu();
	}

	private void connectToDBOrExit() {
		dbConn.connectToDB(username, password, serverName, portNumber, dbName);
		if (!dbConn.isConnected()) {
			Printer.debug(dbConn.getErrorMsg());
			Printer.info("Failed to connect to database.");
			exit(1);
		}
	}

	private void exit(int retCode) {
		Printer.info("Exitting...");
		if (!dbConn.disconnectFromDB()) {
			Printer.debug(dbConn.getDisconnectError());
			Printer.info("Could not disconnect from database.");
		}
		System.exit(retCode);
	}

	/**
	 * Using debug messages, prints the currently loaded database configuration.
	 */
	private void printDatabaseConfig() {
		Printer.debug("Database Configuration:");
		Printer.debug("Server Name: " + getServerName());
		Printer.debug("Port Number: " + getPortNumber());
		Printer.debug("Username: " + getUsername());
	}

}
