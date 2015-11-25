package music_frontend_project;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	static final String CONFIG_FILE = "frontend_config.txt";

	public static void main(String[] args) {
		ApplicationConfig appConfig = null;

		try {
			appConfig = new ApplicationConfig(CONFIG_FILE);
		} catch (NumberFormatException e) {
			Printer.err("Port number was not formatted correctly in "
					+ "config file '" + CONFIG_FILE + "'.");
			e.printStackTrace();
			Main.exit(1);
		} catch (FileNotFoundException e) {
			Printer.err("The config file '" + CONFIG_FILE
					+ "' was not found.");
			e.printStackTrace();
			Main.exit(2);
		} catch (IOException e) {
			Printer.err("There was an IO issue with the config file '"
					+ CONFIG_FILE + "'.");
			e.printStackTrace();
			Main.exit(3);
		}

		Application app = new Application();
		appConfig.configure(app);
		app.run();
	}

	private static void exit(int retCode) {
		Printer.info("Exitting...");
		System.exit(retCode);
	}
}
