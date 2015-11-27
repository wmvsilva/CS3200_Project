package music_frontend_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import music_frontend_project.actions.Action;

public class Menu {

	List<Token> userChoices = new LinkedList<Token>();

	boolean shouldContinue = true;

	int userChoice;

	public boolean shouldContinue() {
		return shouldContinue;
	}

	public void display() {
		for (int i = 0; i < userChoices.size(); i++) {
			Printer.info("" + i + ". " + userChoices.get(i).getDescription());
		}
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

	public void setShouldContinue(boolean b) {
		shouldContinue = b;
	}

	public void addOption(String description, Action action, Menu nextMenu) {
		Token menuOption = new Token(description, action, nextMenu);
		userChoices.add(menuOption);
	}
}
