package music_frontend_project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class used to load the configuration for the main application from some file
 * 
 */
public class ApplicationConfig {

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

	/**
	 * Constructor for ApplicationConfig which takes in a filename and loads the
	 * configuration data contained in that file.
	 * 
	 * The file should be of the format:
	 * 
	 * 
	 * @param configFile
	 *            filepath of the configuration file
	 * @throws IOException
	 *             if there is IO issue while reading lines from
	 * @throws FileNotFoundException
	 *             if the configuration is not found at the given location
	 * @throws NumberFormatException
	 *             if the port number in the configuration file is not a
	 *             parsable integer
	 */
	public ApplicationConfig(String configFile) throws IOException,
			FileNotFoundException, NumberFormatException {
		// Open configuration file
		BufferedReader br = new BufferedReader(new FileReader(configFile));
		// Read configuration data into variables
		username = br.readLine();
		password = br.readLine();
		serverName = br.readLine();
		portNumber = Integer.parseInt(br.readLine());
		dbName = br.readLine();

		br.close();
	}

	/**
	 * Applies the currently loaded configuration to the given application
	 * 
	 * @param app
	 *            application to configure
	 */
	public void configure(Application app) {
		app.setUsername(username);
		app.setPassword(password);
		app.setServerName(serverName);
		app.setPortNumber(portNumber);
		app.setDbName(dbName);
	}

}
