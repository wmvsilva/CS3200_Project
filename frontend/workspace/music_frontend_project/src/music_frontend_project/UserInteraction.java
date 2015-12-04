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

}
