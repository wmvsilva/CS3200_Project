package music_frontend_project;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	static final String CONFIG_FILE = "frontend_config.txt";

	public static void main(String[] args) {
		ApplicationConfig appConfig;

		try {
			appConfig = new ApplicationConfig(CONFIG_FILE);
		} catch (NumberFormatException e) {
			System.err.println("Port number was not formatted correctly in "
					+ "config file '" + CONFIG_FILE + "'.");
			e.printStackTrace();
			Main.exit(1);
		} catch (FileNotFoundException e) {
			System.err.println("The config file '" + CONFIG_FILE
					+ "' was not found.");
			e.printStackTrace();
			Main.exit(2);
		} catch (IOException e) {
			System.err.println("There was an IO issue with the config file '"
					+ CONFIG_FILE + "'.");
			e.printStackTrace();
			Main.exit(3);
		}

		Application app = appConfig.configure(new Application());
		app.run();
	}

	private static void exit(int retCode) {
		System.out.println("Exitting...");
		System.exit(retCode);
	}
}
