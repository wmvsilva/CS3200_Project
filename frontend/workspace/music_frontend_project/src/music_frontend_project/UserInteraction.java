package music_frontend_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInteraction {

	static String getUserInput() throws IOException {
		String result = null;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		result = br.readLine();

		return result;
	}

	static Integer provideUserPick(int maxPick) throws IOException {
		return provideUserPick(maxPick, "Enter an option:");
	}

	static Integer provideUserPick(int maxPick, String question)
			throws IOException {
		if (maxPick < 0) {
			Printer.err("No options available.");
			throw new IOException(
					"No options available. This error should have been caught. Please let us know so we can fix this error!");
		}
		Integer result = null;
		Printer.info(question);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			result = Integer.parseInt(br.readLine());
			if (result > maxPick || result < 0) {
				Printer.info("An unspecificied option was selected.");
				result = provideUserPick(maxPick);
			}
		} catch (NumberFormatException e) {
			Printer.err("A non-integer option was selected.");
			result = provideUserPick(maxPick);
		}

		return result;
	}

	static void printOptions(String... options) {
		for (int i = 0; i < options.length; i++) {
			Printer.info("" + i + ". " + options[i]);
		}
	}

	public static double getPriceFromUser(String newValue) throws IOException {
		Double result = null;
		try {
			result = Double.parseDouble(newValue);
		} catch (NumberFormatException e) {
			Printer.err("Please enter a valid price:");
			newValue = getUserInput();
			return getPriceFromUser(newValue);
		}
		return result;

	}

	public static Integer getPositiveIntFromUser() throws IOException {
		String input = getUserInput();
		Integer result = null;
		try {
			result = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			Printer.err("Number was not entered. Please enter an integer: ");
			return getPositiveIntFromUser();
		}

		if (result <= 0) {
			Printer.info("Please enter a positive integer:");
			return getPositiveIntFromUser();
		}

		return result;
	}

	public static String getDateFromUser() throws IOException {
		String input = getUserInput();
		while (!Utility.isValidDate(input)) {
			Printer.err("Please enter a date in the format yyyy-mm-dd:");
			input = getUserInput();
		}
		return input;
	}
}
