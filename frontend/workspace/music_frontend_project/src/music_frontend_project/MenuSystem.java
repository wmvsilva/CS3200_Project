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
			for (int i = 0; i < searchedAlbums.size(); i++) {
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
		System.out.println(userPick);
		System.out.println(countAfterArtists);
		System.out.println(countAfterAlbums);
		if (userPick == 0) {
			mainMenu();
		} else if (userPick < countAfterArtists) {
			viewArtist(searchedArtists.get(userPick - 1));
		} else if (userPick < countAfterAlbums) {
			viewAlbum(searchedAlbums.get(userPick - 1 - searchedArtists.size())
					.getValue2());
		} else {
			// TODO viewSongs(searchedSongs.get(userPick).getValue2());
		}
	}

	private void viewAlbum(Integer albumId) throws IOException, SQLException {
		// Album Name, Release Date, Album Art
		Triplet<String, String, String> albumInfo = dbConn
				.getAlbumInfo(albumId);
		// Artists of album
		List<String> artists = dbConn.getArtistsOfAlbum(albumId);
		// Genres of album
		List<String> genres = dbConn.getGenresOfAlbum(albumId);
		// Track name, Song Id
		List<Pair<String, Integer>> tracks = dbConn.getAlbumTracks(albumId);
		// Store, Price, Format
		List<Triplet<String, Double, String>> albumStoreInfo = dbConn
				.getAlbumStoreInfo(albumId);

		Printer.info("Album Name: " + albumInfo.getValue0());
		String artistsDelimited = "";
		for (int i = 0; i < artists.size(); i++) {
			artistsDelimited += artists.get(i);
			if (i != artists.size() - 1) {
				artistsDelimited += ",";
			}
		}
		Printer.info("Artists: " + artistsDelimited);

		Printer.info("Release Date: " + albumInfo.getValue1());

		Printer.info("");

		for (Pair<String, Integer> track : tracks) {
			Printer.info(track.getValue0());
		}
		Printer.info("");

		for (Triplet<String, Double, String> storeInfo : albumStoreInfo) {
			Printer.info("Store: " + storeInfo.getValue0());
			Printer.info("Price: " + storeInfo.getValue1());
			Printer.info("Format: " + storeInfo.getValue2());
		}

		printOptions("View Song", "View Artist", "Show Album Art", "Modify",
				"Delete", "Main Menu");
		Integer choice = provideUserPick(5);
		switch (choice) {
		case (0):
			for (int i = 0; i < tracks.size(); i++) {
				String trackName = tracks.get(i).getValue0();
				Printer.info("" + i + ". " + trackName);
			}
			Integer trackChoice = provideUserPick(tracks.size() - 1);
			int trackId = tracks.get(trackChoice).getValue1();
			// TODO viewSong(trackId);
			break;
		case (1):
			for (int i = 0; i < artists.size(); i++) {
				String artistName = artists.get(i);
				Printer.info("" + i + ". " + artistName);
			}
			Integer artistChoice = provideUserPick(artists.size() - 1);
			String artistName = artists.get(artistChoice);
			viewArtist(artistName);
			break;
		case (2):
			String albumFilePath = albumInfo.getValue2();
			ImageViewer.viewImage(albumFilePath);
			viewAlbum(albumId);
			break;
		case (3):
			modifyAlbum(albumId);
			break;
		case (4):
			// TODO deleteAlbum(albumId);
			Printer.info("Album deleted.");
			mainMenu();
			break;
		case (5):
			mainMenu();
			break;
		}

	}

	private void modifyAlbum(Integer albumId) throws SQLException, IOException {
		// Album Name, Release Date, Album Art
		Triplet<String, String, String> albumInfo = dbConn
				.getAlbumInfo(albumId);
		// Artists of album
		List<String> artists = dbConn.getArtistsOfAlbum(albumId);
		// Genres of album
		List<String> genres = dbConn.getGenresOfAlbum(albumId);
		// Store, Price, Format
		List<Triplet<String, Double, String>> albumStoreInfo = dbConn
				.getAlbumStoreInfo(albumId);

		int count = 1;
		Printer.info("" + count + ". Album Name: " + albumInfo.getValue0());
		count++;
		Printer.info("" + count + ". Release Date: " + albumInfo.getValue1());
		count++;

		for (int i = 0; i < artists.size(); i++) {
			Printer.info("" + count + ". Artist: " + artists.get(i));
			count++;
		}
		int countAfterArtist = count;
		for (int i = 0; i < genres.size(); i++) {
			Printer.info("" + count + ". Genre: " + genres.get(i));
			count++;
		}
		int countAfterGenre = count;
		for (int i = 0; i < albumStoreInfo.size(); i++) {
			Triplet<String, Double, String> triplet = albumStoreInfo.get(i);
			Printer.info("" + count + ". Store: " + triplet.getValue0());
			count++;
			Printer.info("" + count + ". Price: " + triplet.getValue1());
			count++;
			Printer.info("" + count + ". Format: " + triplet.getValue2());
			count++;
		}

		int userInput = provideUserPick(1 + 2 + artists.size() + genres.size()
				+ 3 * albumStoreInfo.size(),
				"Enter number to modify or 0 to go back:");
		if (userInput == 0) {
			mainMenu();
			return;
		} else if (userInput == 1) {
			Printer.info("Enter a new value:");
			String newAlbumName = getUserInput();
			dbConn.modifyAlbumName(albumId, newAlbumName);
			return;
		} else if (userInput == 2) {
			Printer.info("Enter a new value:");
			String newReleaseDate = getUserInput();
			dbConn.modifyAlbumReleaseDate(albumId, newReleaseDate);
			return;
		} else if (userInput <= countAfterArtist) {
			Printer.info("Enter a new value:");
			String newArtistName = getUserInput();
			String oldArtistName = artists.get(userInput - 3);
			dbConn.modifyAlbumArtist(albumId, oldArtistName, newArtistName);
			return;
		} else if (userInput <= countAfterGenre) {
			Printer.info("Enter a new value:");
			String newGenreName = getUserInput();
			String oldGenreName = artists.get(userInput - 3 - artists.size());
			dbConn.modifyAlbumGenre(albumId, oldGenreName, newGenreName);
			return;
		} else {
			Printer.info("Enter a new value:");
			String newValue = getUserInput();
			int howFarIntoList = userInput - 3 - artists.size() - genres.size();
			int whichTriplet = (int) Math.ceil((double) howFarIntoList / 3.0);
			Triplet<String, Double, String> triplet = albumStoreInfo
					.get(whichTriplet);
			int whichEntity = howFarIntoList % 3;

			String oldStoreName = triplet.getValue0();
			Double oldPrice = triplet.getValue1();
			String oldFormat = triplet.getValue2();
			// Store
			if (whichEntity == 0) {
				dbConn.modifyAlbumStoreCatalog(albumId, oldStoreName, oldPrice,
						oldFormat, newValue);
			} else if (whichEntity == 2) {
				// Price
				dbConn.modifyPriceCatalog(albumId, oldStoreName, oldPrice,
						oldFormat, newValue);
			} else {
				// Format
				dbConn.modifyFormatCatalog(albumId, oldStoreName, oldPrice,
						oldFormat, newValue);
			}
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
			viewAlbum(albumId);
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
