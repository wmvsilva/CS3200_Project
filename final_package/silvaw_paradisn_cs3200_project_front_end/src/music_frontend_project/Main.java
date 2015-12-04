package music_frontend_project;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main class to load configuration and start application for interacting with
 * music database.
 * 
 */
public class Main {
	/**
	 * The location of the configuration file which should be in the format:
	 * 
	 * 
	 */
	static final String CONFIG_FILE = "frontend_config.txt";

	/**
	 * Entry point of program which attempts to load configuration file and run
	 * the music database interaction program with that configuration.
	 * 
	 * @param args
	 *            command line arguments that are unused
	 */
	public static void main(String[] args) {
		ApplicationConfig appConfig = null;

		// Attempt to load configuration. Any problems during this process will
		// cause the program to exit with an error message.
		try {
			appConfig = new ApplicationConfig(CONFIG_FILE);
		} catch (NumberFormatException e) {
			Printer.err("Port number was not formatted correctly in "
					+ "config file '" + CONFIG_FILE + "'.");
			e.printStackTrace();
			Main.exit(1);
		} catch (FileNotFoundException e) {
			Printer.err("The config file '" + CONFIG_FILE + "' was not found.");
			e.printStackTrace();
			Main.exit(1);
		} catch (IOException e) {
			Printer.err("There was an IO issue with the config file '"
					+ CONFIG_FILE + "'.");
			e.printStackTrace();
			Main.exit(1);
		}

		Application app = new Application();
		// Load application configuration and run
		appConfig.configure(app);
		app.run();
	}

	/**
	 * Notifies the user that this program is exiting and proceeds to end this
	 * program with the given return code
	 * 
	 * @param retCode
	 *            the return code that this program should exit with
	 */
	private static void exit(int retCode) {
		Printer.info("Exitting...");
		System.exit(retCode);
	}
}
