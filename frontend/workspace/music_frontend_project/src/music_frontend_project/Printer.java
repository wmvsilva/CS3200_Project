package music_frontend_project;

/**
 * Class for handling the printing of lines to STDOUT. Depending on the current
 * situation, one can use this utility class to print a message as information,
 * debug, error, etc.
 * 
 */
public final class Printer {

	/**
	 * Prints some informative string. The user should always see this.
	 * 
	 * @param msg
	 *            the message to print to the user
	 */
	static void info(String msg) {
		System.out.println(msg);
	}

	/**
	 * Handles a debug message in some way. This may or may not be printed to
	 * the screen
	 * 
	 * @param msg
	 *            the debugging message
	 */
	static void debug(String msg) {
		System.out.println(msg);
	}

	/**
	 * Handles an error message in some way. The user should probably see this.
	 * 
	 * @param msg
	 *            the error message
	 */
	static void err(String msg) {
		System.err.println(msg);
	}

}
