package music_frontend_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Menu {

	Map<Integer, Token> userChoices = new HashMap<Integer, Token>();

	boolean shouldContinue = false;

	String display = null;

	int userChoice;

	Menu(Token t, String dis) {

	}

	public boolean shouldContinue() {
		return shouldContinue;
	}

	public void display() {
		Printer.info(display);
	}

	public boolean getUserChoice() {
		boolean result = false;

		try {
			String userResponse = provideInputWithError();
			userChoice = Integer.parseInt(userResponse);
			result = true;
		} catch (IOException e) {
			Printer.debug(e.getMessage());
		} catch (NumberFormatException e) {
			Printer.info("The given input was not a number.");
			result = getUserChoice();
		}
		return result;

	}

	/**
	 * Gets the user to provide some input.
	 * 
	 * @return the string that the user typed in as a response to the question
	 * @throws IOException
	 *             if there is IO issue
	 */
	private static String provideInputWithError() throws IOException {
		String result = null;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		result = br.readLine();

		return result;
	}

	public void executeUserChoice() {
		userChoices.get(userChoice).executeAction();
	}

	public Menu goToNextMenu() {
		return userChoices.get(userChoice).nextMenu();
	}
}
