package music_frontend_project;


public class Application {
	
	private String username;

	private String password;

	private String serverName;

	private int portNumber;

	private String dbName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void run() {
		printDatabaseConfig();
	}
	
	private void printDatabaseConfig() {
		Printer.debug("Database Configuration:");
		Printer.debug("Server Name: " + getServerName());
		Printer.debug("Port Number: " + getPortNumber());
		Printer.debug("Username: " + getUsername());
	}

}
