package music_frontend_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import org.javatuples.Pair;
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
		// Album name, artist, id
		List<Triplet<String, String, Integer>> searchedAlbums = dbConn
				.searchAlbums(userInput);
		// Song name, artist, id
		List<Triplet<String, String, Integer>> searchedSongs = dbConn
				.searchSongs(userInput);
		int count = 1;
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
				Printer.info("" + count + ". " + curr.getValue0() + " - "
						+ curr.getValue1());
				count++;
			}
		}

		int countAfterAlbums = count;

		if (!searchedSongs.isEmpty()) {
			Printer.info("[Songs]");
			for (int i = 0; i < searchedSongs.size(); i++) {
				Triplet<String, String, Integer> curr = searchedSongs.get(i);
				Printer.info("" + count + ". " + curr.getValue0() + " - "
						+ curr.getValue1());
				count++;
			}
		}

		int countAfterSongs = count;

		int userPick = provideUserPick(5,
				"Enter a number or 0 to go to the Main Menu:");
		if (userPick == 0) {
			mainMenu();
		} else if (userPick < countAfterArtists) {
			viewArtist(searchedArtists.get(userPick - 1));
		} else if (userPick + countAfterArtists < countAfterAlbums) {
			// TODO viewAlbum(searchedAlbums.get(userPick).getValue2());
		} else {
			// TODO viewSongs(searchedSongs.get(userPick).getValue2());
		}
	}

	private void viewArtist(String artist) throws SQLException, IOException {
		// Album, album id
		List<Pair<String, Integer>> albumsByArtist = dbConn
				.getAlbumsByArtist(artist);
		Printer.info("Artist Name: " + artist);
		for (Pair<String, Integer> p : albumsByArtist) {
			Printer.info(p.getValue0());
		}
		Printer.info("");
		printOptions("View Album", "Modify", "Delete", "Main Menu");
		Integer choice = provideUserPick(3);
		switch (choice) {
		case (0):
			for (int i = 0; i < albumsByArtist.size(); i++) {
				String albumName = albumsByArtist.get(i).getValue0();
				Printer.info("" + i + ". " + albumName);
			}
			Integer albumChoice = provideUserPick(albumsByArtist.size() - 1);
			int albumId = albumsByArtist.get(albumChoice).getValue1();
			// TODO viewAlbum(albumId);
			break;
		case (1):
			Printer.info("Enter new artist name:");
			String userChange = getUserInput();
			dbConn.modifyArtist(artist, userChange);
			Printer.info("Artist changed to " + userChange);
			viewArtist(userChange);
			break;
		case (2):
			dbConn.deleteArtist(artist);
			Printer.info("Artist deleted.");
			mainMenu();
			break;
		case (3):
			mainMenu();
			break;
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
		return provideUserPick(maxPick, "Pick an option:");
	}

	private static Integer provideUserPick(int maxPick, String question)
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
}
