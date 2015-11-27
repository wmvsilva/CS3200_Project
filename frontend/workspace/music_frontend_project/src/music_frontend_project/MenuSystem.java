package music_frontend_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import org.javatuples.Triplet;

public final class MenuSystem {

	DBConnector dbConn;

	public MenuSystem(DBConnector c) {
		dbConn = c;
	}

	public void mainMenu() throws IOException, SQLException {
		printOptions("Search", "Add New", "Exit");
		Integer choice = provideUserPick(2);
		switch (choice) {
		case (0):
			searchMenu();
			break;
		case (1):
			// addNewMenu();
			break;
		case (2):
			return;
		}
	}

	public void searchMenu() throws IOException, SQLException {
		Printer.info("Enter text to search for:");
		String userInput = getUserInput();
		List<String> searchedArtists = dbConn.searchArtists(userInput);
		List<Triplet<String, String, Integer>> searchedAlbums = dbConn
				.searchAlbums(userInput);
		List<Triplet<String, String, Integer>> searchedSongs = dbConn
				.searchSongs(userInput);
		int count = 0;
		if (!searchedArtists.isEmpty()) {
			Printer.info("[Artists]");
			for (int i = 0; i < searchedArtists.size(); i++) {
				Printer.info("" + count + ". " + searchedArtists.get(i));
				count++;
			}
		}
		int countAfterArtists = count;

		if (!searchedAlbums.isEmpty()) {
			Printer.info("[Albums]");
			for (int i = 0; i < searchedArtists.size(); i++) {
				Triplet<String, String, Integer> curr = searchedAlbums.get(i);
				Printer.info("" + count + ". " + curr.getValue0() + " "
						+ curr.getValue1());
				count++;
			}
		}

		int countAfterAlbums = count;

		if (!searchedSongs.isEmpty()) {
			Printer.info("[Songs]");
			for (int i = 0; i < searchedSongs.size(); i++) {
				Triplet<String, String, Integer> curr = searchedSongs.get(i);
				Printer.info("" + count + ". " + curr.getValue0() + " "
						+ curr.getValue1());
				count++;
			}
		}

		int countAfterSongs = count;

		int userPick = provideUserPick(5);
		if (userPick < countAfterArtists) {
			// viewArtist(searchedArtists.get(userPick));
		} else if (userPick + countAfterArtists < countAfterAlbums) {
			// viewAlbum(searchedAlbums.get(userPick).getValue2());
		} else {
			// viewSongs(searchedSongs.get(userPick).getValue2());
		}
	}

	private String getUserInput() throws IOException {
		String result = null;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		result = br.readLine();

		return result;
	}

	private void printOptions(String... options) {
		for (int i = 0; i < options.length; i++) {
			Printer.info("" + i + ". " + options[i]);
		}
	}

	private static Integer provideUserPick(int maxPick) throws IOException {
		Integer result = null;
		Printer.info("Pick an option: ");

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
}
