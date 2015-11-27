package music_frontend_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class MenuSystem {

	public static void mainMenu() throws IOException {
		printOptions("Search", "Add New", "Exit");
		Integer choice = provideUserPick();
		switch (choice) {
		case (0):
			searchMenu();
			break;
		case (1):
			addNewMenu();
			break;
		case (2):
			return;
			break;
		}
	}

	private static void printOptions(String... options) {
		for (int i = 0; i < options.length; i++) {
			Printer.info("" + i + ". " + options[i]);
		}
	}

	private static Integer provideUserPick() throws IOException {
		Integer result = null;
		Printer.info("Pick an option: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			result = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e) {
			Printer.err("A non-integer option was selected.");
			result = provideUserPick();
		}

		br.close();
		return result;
	}
}
