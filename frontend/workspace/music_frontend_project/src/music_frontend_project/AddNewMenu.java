package music_frontend_project;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.javatuples.Triplet;

public class AddNewMenu {

	DBConnector dbConn;

	public AddNewMenu(DBConnector c) {
		dbConn = c;
	}

	public void addNewEntityMenu() throws IOException, SQLException {
		// List options
		Printer.info("1. Song");
		Printer.info("2. Artist");
		Printer.info("3. Album");
		Printer.info("4. Genre");
		Printer.info("5. Store");
		Printer.info("6. Format");

		// Get user input
		int userPick = MenuSystem.provideUserPick(6,
				"Enter a number or 0 to go to the Main Menu:");

		// Go to next menu
		switch (userPick) {
		case (0):
			MenuSystem mainMenu = new MenuSystem(dbConn);
			mainMenu.mainMenu();
			break;
		case (1):
			// Add song
			addSongMenu();
			break;
		case (2):
			// Add artist
			Printer.info("Enter the new artist name:");
			String newArtist = MenuSystem.getUserInput();
			dbConn.addNewArtist(newArtist);
			addNewEntityMenu();
			break;
		case (3):
			// Add album
			addAlbumMenu();
			break;
		case (4):
			// Add Genre
			Printer.info("Enter the new genre:");
			String newGenre = MenuSystem.getUserInput();
			dbConn.addNewGenre(newGenre);
			addNewEntityMenu();
			break;
		case (5):
			// Add Store
			Printer.info("Enter the new store's name:");
			String newStoreName = MenuSystem.getUserInput();
			dbConn.addNewStore(newStoreName);
			addNewEntityMenu();
			break;
		case (6):
			// Add Format
			Printer.info("Enter the new format name");
			String newFormatName = MenuSystem.getUserInput();
			dbConn.addNewFormat(newFormatName);
			addNewEntityMenu();
			break;
		}
	}

	private void addAlbumMenu() throws IOException, SQLException {
		Printer.info("Album:");
		// Get album name
		Printer.info("Enter album name:");
		String albumName = MenuSystem.getUserInput();
		// Get artist names
		Printer.info("Enter the artist names:");
		String givenArtists = MenuSystem.getUserInput();
		List<String> artists = Arrays.asList(givenArtists.split(","));
		// Get genres
		Printer.info("Enter the genre names:");
		String givenGenres = MenuSystem.getUserInput();
		List<String> genres = Arrays.asList(givenGenres.split(","));
		// Get release date
		Printer.info("Enter the release date:");
		String releaseDate = MenuSystem.getUserInput();
		// Get stores, prices, formats
		Printer.info("Enter the comma-separated values in the form 'store;price;format':");
		String givenStoresPricesFormats = MenuSystem.getUserInput();
		List<String> pipeDelimitedStoresPricesFormats = Arrays
				.asList(givenStoresPricesFormats.split(","));
		List<Triplet<String, String, String>> storesPricesFormats = new LinkedList<Triplet<String, String, String>>();
		for (String s : pipeDelimitedStoresPricesFormats) {
			List<String> splitPipes = Arrays.asList(s.split(";"));
			String storeName = splitPipes.get(0);
			String price = splitPipes.get(1);
			String format = splitPipes.get(2);
			Triplet<String, String, String> triplet = new Triplet<String, String, String>(
					storeName, price, format);
			storesPricesFormats.add(triplet);
		}
		// Get album art file path
		Printer.info("Enter the album art file path:");
		String albumArtFilePath = MenuSystem.getUserInput();

		// Add the album
		dbConn.addAlbum(albumName, artists, genres, releaseDate,
				storesPricesFormats, albumArtFilePath);
		addNewEntityMenu();
	}

	private void goToMainMenu() throws IOException, SQLException {
		MenuSystem mainMenu = new MenuSystem(dbConn);
		mainMenu.mainMenu();
	}

	private void addSongMenu() throws IOException, SQLException {
		Printer.info("1. Single");
		Printer.info("2. Album Track");
		int userPick = MenuSystem.provideUserPick(2,
				"Enter a number or 0 to go to the Main Menu:");

		if (userPick == 0) {
			goToMainMenu();
			return;
		}

		boolean isSingle = (userPick == 1);

		// Get the track name
		Printer.info("Enter the track name:");
		String trackName = MenuSystem.getUserInput();
		// Get the track number
		Printer.info("Enter the track number:");
		String trackNumber = MenuSystem.getUserInput();
		// Get the album name
		Printer.info("Enter the album name:");
		String albumName = MenuSystem.getUserInput();
		// Get the album release date
		List<String> releaseDates = dbConn.getReleaseDatesOfAlbum(albumName);
		for (String date : releaseDates) {
			Printer.info(albumName + " - " + date);
		}
		Printer.info("Enter the album release date:");
		String albumReleaseDate = MenuSystem.getUserInput();
		// Get the artist names
		Printer.info("Enter a comma-delimited list of artists:");
		String givenArtists = MenuSystem.getUserInput();
		List<String> artists = new LinkedList<String>();
		artists.addAll(Arrays.asList(givenArtists.split(",")));
		// Get the ft artists names
		Printer.info("Enter a comma-delimited list of ft artists:");
		String givenFtArtists = MenuSystem.getUserInput();
		List<String> ftArtists = new LinkedList<String>();
		ftArtists.addAll(Arrays.asList(givenFtArtists.split(",")));
		// Get the genres
		Printer.info("Enter a comma-delimited list of genres:");
		String givenGenres = MenuSystem.getUserInput();
		List<String> genres = new LinkedList<String>();
		genres.addAll(Arrays.asList(givenGenres.split(",")));
		// Get the file path of lyrics
		Printer.info("Enter the filepath of the lyrics text file:");
		String lyricsFilePath = MenuSystem.getUserInput();
		// Get the file path of the audio sample
		Printer.info("Enter the filepath of the audio sample");
		String audioSampleFilePath = MenuSystem.getUserInput();
		// Get the length of the track in seconds
		Printer.info("Enter the length of the track in seconds:");
		String trackLengthSeconds = MenuSystem.getUserInput();

		// Get single attributes
		String releaseDate = null;
		String coverArt = null;
		if (isSingle) {
			// Get release date
			Printer.info("Enter the release date:");
			releaseDate = MenuSystem.getUserInput();
			// Get cover art
			Printer.info("Enter the file path of the cover art:");
			coverArt = MenuSystem.getUserInput();
		}

		if (isSingle) {
			dbConn.addSingle(trackName, trackNumber, albumName,
					albumReleaseDate, artists, ftArtists, genres,
					lyricsFilePath, audioSampleFilePath, releaseDate, coverArt,
					trackLengthSeconds);
			addNewEntityMenu();
		} else {
			dbConn.addTrack(trackName, trackNumber, albumName,
					albumReleaseDate, artists, ftArtists, genres,
					lyricsFilePath, audioSampleFilePath, trackLengthSeconds);
			addNewEntityMenu();
		}
	}
}
