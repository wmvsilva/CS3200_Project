package music_frontend_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Sextet;
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

		int userPick = provideUserPick(
				searchedArtists.size() + searchedAlbums.size()
						+ searchedSongs.size(),
				"Enter a number or 0 to go to the Main Menu:");
		if (userPick == 0) {
			mainMenu();
		} else if (userPick < countAfterArtists) {
			viewArtist(searchedArtists.get(userPick - 1));
		} else if (userPick < countAfterAlbums) {
			viewAlbum(searchedAlbums.get(userPick - 1 - searchedArtists.size())
					.getValue2());
		} else {
			viewSong(searchedSongs.get(
					userPick - 1 - searchedArtists.size()
							- searchedAlbums.size()).getValue2());
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
			viewSong(trackId);
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
			dbConn.deleteAlbum(albumId);
			Printer.info("Album deleted.");
			mainMenu();
			break;
		case (5):
			mainMenu();
			break;
		}

	}

	private void viewSong(int trackId) throws SQLException, IOException {
		if (!dbConn.isSingle(trackId)) {
			viewGeneralSong(trackId);
		} else {
			// TODO viewSingle(trackId);
		}

	}

	private void viewGeneralSong(int trackId) throws SQLException, IOException {
		// Track Name, Track Number, Album Name, Album id, Lyric filepath,
		// Sample filepath
		Sextet<String, Integer, String, Integer, String, String> baseSongInfo = dbConn
				.getBaseSongInfo(trackId);
		// Artists
		List<String> artists = dbConn.getSongArtists(trackId);
		// Featured Artists
		List<String> ftArtists = dbConn.getSongFtArtists(trackId);
		// Genres
		List<String> genres = dbConn.getGenres(trackId);

		// Print all found information
		Printer.info("Track Name: " + baseSongInfo.getValue0());
		Printer.info("Track Number: " + baseSongInfo.getValue1());
		Printer.info("Album: " + baseSongInfo.getValue2());
		// Artists
		String artistsDelim = "";
		for (int i = 0; i < artists.size(); i++) {
			artistsDelim += artists.get(i);
			if (i != artists.size() - 1) {
				artistsDelim += ",";
			}
		}
		Printer.info("Artists: " + artistsDelim);
		// Ft Artists
		String ftArtistsDelim = "";
		for (int i = 0; i < ftArtists.size(); i++) {
			ftArtistsDelim += ftArtists.get(i);
			if (i != ftArtists.size() - 1) {
				ftArtistsDelim += ",";
			}
		}
		Printer.info("Ft Artists: " + ftArtistsDelim);
		// Genres
		String genreDelim = "";
		for (int i = 0; i < genres.size(); i++) {
			genreDelim += genres.get(i);
			if (i != genres.size() - 1) {
				genreDelim += ",";
			}
		}
		Printer.info("Genres: " + genreDelim);

		// Show menu
		printOptions("Show Album", "Show Artist", "Show Ft Artist",
				"Show Lyrics", "Play Audio Sample", "Modify", "Delete",
				"Main Menu");
		Integer choice = provideUserPick(7);
		switch (choice) {
		case (0):
			// Show Album
			viewAlbum(baseSongInfo.getValue3());
			break;
		case (1):
			// Show Artist
			for (int i = 0; i < artists.size(); i++) {
				Printer.info("" + i + ". " + artists.get(i));
			}
			int artistChoice = provideUserPick(artists.size() - 1);
			viewArtist(artists.get(artistChoice));
			break;
		case (2):
			// Show Ft Artist
			if (ftArtists.isEmpty()) {
				Printer.info("There are no featured artists.");
				viewSong(trackId);
				return;
			}
			for (int i = 0; i < ftArtists.size(); i++) {
				Printer.info("" + i + ". " + ftArtists.get(i));
			}
			int ftArtistChoice = provideUserPick(ftArtists.size() - 1);
			viewArtist(ftArtists.get(ftArtistChoice));
			break;
		case (3):
			// Show Lyrics
			String lyricFilepath = baseSongInfo.getValue4();
			byte[] encoded = Files.readAllBytes(Paths.get(lyricFilepath));
			String lyrics = new String(encoded, Charset.defaultCharset());
			Printer.info("[Lyrics]");
			Printer.info(lyrics);
			viewSong(trackId);
			break;
		case (4):
			// Play Audio Sample
			MP3Player.playAudio(baseSongInfo.getValue5());
			viewSong(trackId);
			break;
		case (5):
			// Modify
			modifyGeneralSong(trackId);
			break;
		case (6):
			// Delete
			dbConn.deleteSong(trackId);
			Printer.info("Song deleted.");
			mainMenu();
			break;
		case (7):
			// Main menu
			mainMenu();
			break;
		}

	}

	private void modifyGeneralSong(int trackId) throws SQLException,
			IOException {
		// Track Name, Track Number, Album Name, Album id, Lyric filepath,
		// Sample filepath
		Sextet<String, Integer, String, Integer, String, String> baseSongInfo = dbConn
				.getBaseSongInfo(trackId);
		// Artists
		List<String> artists = dbConn.getSongArtists(trackId);
		// Featured Artists
		List<String> ftArtists = dbConn.getSongFtArtists(trackId);
		// Genres
		List<String> genres = dbConn.getGenres(trackId);

		// Options
		Printer.info("1. Track Name: " + baseSongInfo.getValue0());
		Printer.info("2. Track Number: " + baseSongInfo.getValue1());
		Printer.info("3. Album: " + baseSongInfo.getValue2());
		int count = 4;
		// Artists
		for (int i = 0; i < artists.size(); i++) {
			Printer.info("" + count + ". Artist: " + artists.get(i));
			count++;
		}
		int countAfterArtist = count;
		// Featured Artists
		for (int i = 0; i < ftArtists.size(); i++) {
			Printer.info("" + count + ". Ft Artist: " + ftArtists.get(i));
			count++;
		}
		int countAfterFtArtist = count;
		// Genres
		for (int i = 0; i < genres.size(); i++) {
			Printer.info("" + count + ". Genre: " + genres.get(i));
			count++;
		}

		int userInput = provideUserPick(3 + artists.size() + ftArtists.size()
				+ genres.size(), "Enter number to modify or 0 to go back:");

		if (userInput == 0) {
			viewSong(trackId);
			return;
		} else if (userInput == 1) {
			Printer.info("Enter a value:");
			String newTrackName = getUserInput();
			dbConn.modifyTrackName(trackId, newTrackName);
			viewSong(trackId);
			return;
		} else if (userInput == 2) {
			Printer.info("Enter a value:");
			String newTrackNumber = getUserInput();
			dbConn.modifyTrackNumber(trackId, newTrackNumber);
			viewSong(trackId);
			return;
		} else if (userInput == 3) {
			Printer.info("Enter the new album name:");
			String newAlbum = getUserInput();
			List<String> releaseDates = dbConn.getReleaseDatesOfAlbum(newAlbum);
			for (String date : releaseDates) {
				Printer.info(newAlbum + " - " + date);
			}
			Printer.info("Enter the new album release date:");
			String newReleaseDate = getUserInput();
			dbConn.modifySongAlbum(trackId, newAlbum, newReleaseDate);
			viewSong(trackId);
			return;
		} else if (userInput < countAfterArtist) {
			Printer.info("Enter a value:");
			String newArtist = getUserInput();
			String oldArtist = artists.get(userInput - 4);
			dbConn.modifySongArtist(trackId, oldArtist, newArtist);
			viewSong(trackId);
			return;
		} else if (userInput < countAfterFtArtist) {
			Printer.info("Enter a value:");
			String newFtArtist = getUserInput();
			String oldFtArtist = ftArtists.get(userInput - 4 - artists.size());
			dbConn.modifySongFtArtist(trackId, oldFtArtist, newFtArtist);
			viewSong(trackId);
			return;
		} else {
			Printer.info("Enter a value:");
			String newGenre = getUserInput();
			String oldGenre = genres.get(userInput - 4 - artists.size()
					- ftArtists.size());
			dbConn.modifySongGenre(trackId, oldGenre, newGenre);
			viewSong(trackId);
			return;
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
			viewAlbum(albumId);
			return;
		} else if (userInput == 1) {
			Printer.info("Enter a new value:");
			String newAlbumName = getUserInput();
			dbConn.modifyAlbumName(albumId, newAlbumName);
			viewAlbum(albumId);
			return;
		} else if (userInput == 2) {
			Printer.info("Enter a new value:");
			String newReleaseDate = getUserInput();
			dbConn.modifyAlbumReleaseDate(albumId, newReleaseDate);
			viewAlbum(albumId);
			return;
		} else if (userInput < countAfterArtist) {
			Printer.info("Enter a new value:");
			String newArtistName = getUserInput();
			String oldArtistName = artists.get(userInput - 3);
			dbConn.modifyAlbumArtist(albumId, oldArtistName, newArtistName);
			viewAlbum(albumId);
			return;
		} else if (userInput < countAfterGenre) {
			Printer.info("Enter a new value:");
			String newGenreName = getUserInput();
			String oldGenreName = genres.get(userInput - 3 - artists.size());
			dbConn.modifyAlbumGenre(albumId, oldGenreName, newGenreName);
			Printer.info("Modifed genre " + oldGenreName + " to genre "
					+ newGenreName + ".");
			viewAlbum(albumId);
			return;
		} else {
			Printer.info("Enter a new value:");
			String newValue = getUserInput();
			int howFarIntoList = userInput - 2 - artists.size() - genres.size();
			Printer.info("howFarIntoList: " + howFarIntoList);
			int whichTriplet = (int) Math.ceil((double) howFarIntoList / 3.0) - 1;
			Triplet<String, Double, String> triplet = albumStoreInfo
					.get(whichTriplet);
			int whichEntity = howFarIntoList % 3;
			Printer.info("Entity is " + whichEntity);

			String oldStoreName = triplet.getValue0();
			Double oldPrice = triplet.getValue1();
			String oldFormat = triplet.getValue2();
			// Store
			if (whichEntity == 1) {
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
			viewAlbum(albumId);
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
