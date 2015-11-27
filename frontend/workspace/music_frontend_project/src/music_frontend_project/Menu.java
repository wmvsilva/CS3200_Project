package music_frontend_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {

	int option;

	Token token;

	boolean shouldContinue = false;

	String display = null;

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
			Integer.parseInt(userResponse);
			result = true;
		} catch (IOException e) {
			Printer.debug(e.getMessage());
		} catch (NumberFormatException e) {
			Printer.info("Please enter a integer option:");
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

		System.out.println(question);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		result = br.readLine();

		return result;
	}

	public void executeUserChoice() {
		token.executeAction();
	}

	public Menu goToNextMenu() {
		return token.nextMenu();
	}
}
