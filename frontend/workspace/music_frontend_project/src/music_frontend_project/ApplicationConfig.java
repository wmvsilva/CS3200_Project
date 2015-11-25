package music_frontend_project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ApplicationConfig {

	private String username;

	private String password;

	private String serverName;

	private int portNumber;

	private String dbName;

	public ApplicationConfig(String configFile) throws IOException,
			FileNotFoundException, NumberFormatException {
		BufferedReader br = new BufferedReader(new FileReader("file.txt"));
		username = br.readLine();
		password = br.readLine();
		serverName = br.readLine();
		portNumber = Integer.parseInt(br.readLine());
		dbName = br.readLine();
		br.close();
	}

	public void configure(Application app) {
		app.setUsername(username);
		app.setPassword(password);
		app.setServerName(serverName);
		app.setPortNumber(portNumber);
		app.setDbName(dbName);
	}

	

}
