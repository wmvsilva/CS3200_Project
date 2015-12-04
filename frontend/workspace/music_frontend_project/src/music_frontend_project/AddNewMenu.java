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
		int userPick = UserInteraction.provideUserPick(6,
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
			String newArtist = UserInteraction.getUserInput();
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
			String newGenre = UserInteraction.getUserInput();
			dbConn.addNewGenre(newGenre);
			addNewEntityMenu();
			break;
		case (5):
			// Add Store
			Printer.info("Enter the new store's name:");
			String newStoreName = UserInteraction.getUserInput();
			dbConn.addNewStore(newStoreName);
			addNewEntityMenu();
			break;
		case (6):
			// Add Format
			Printer.info("Enter the new format name");
			String newFormatName = UserInteraction.getUserInput();
			dbConn.addNewFormat(newFormatName);
			addNewEntityMenu();
			break;
		}
	}

	private void addAlbumMenu() throws IOException, SQLException {
		Printer.info("Album:");
		// Get album name
		Printer.info("Enter album name:");
		String albumName = UserInteraction.getUserInput();
		// Get artist names
		Printer.info("Enter the artist names:");
		String givenArtists = UserInteraction.getUserInput();
		List<String> artists = Arrays.asList(givenArtists.split(","));
		// Get genres
		Printer.info("Enter the genre names:");
		String givenGenres = UserInteraction.getUserInput();
		List<String> genres = Arrays.asList(givenGenres.split(","));
		// Get release date
		Printer.info("Enter the release date:");
		String releaseDate = UserInteraction.getUserInput();
		// Get stores, prices, formats
		Printer.info("Enter the comma-separated values in the form 'store;price;format':");
		String givenStoresPricesFormats = UserInteraction.getUserInput();
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
		String albumArtFilePath = UserInteraction.getUserInput();

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
		int userPick = UserInteraction.provideUserPick(2,
				"Enter a number or 0 to go to the Main Menu:");

		if (userPick == 0) {
			goToMainMenu();
			return;
		}

		boolean isSingle = (userPick == 1);

		// Get the track name
		Printer.info("Enter the track name:");
		String trackName = UserInteraction.getUserInput();
		// Get the track number
		Printer.info("Enter the track number:");
		String trackNumber = UserInteraction.getUserInput();
		// Get the album name
		Printer.info("Enter the album name:");
		String albumName = UserInteraction.getUserInput();
		// Get the album release date
		List<String> releaseDates = dbConn.getReleaseDatesOfAlbum(albumName);
		for (String date : releaseDates) {
			Printer.info(albumName + " - " + date);
		}
		Printer.info("Enter the album release date:");
		String albumReleaseDate = UserInteraction.getUserInput();
		// Get the artist names
		Printer.info("Enter a comma-delimited list of artists:");
		String givenArtists = UserInteraction.getUserInput();
		List<String> artists = new LinkedList<String>();
		artists.addAll(Arrays.asList(givenArtists.split(",")));
		// Get the ft artists names
		Printer.info("Enter a comma-delimited list of ft artists:");
		String givenFtArtists = UserInteraction.getUserInput();
		List<String> ftArtists = new LinkedList<String>();
		ftArtists.addAll(Arrays.asList(givenFtArtists.split(",")));
		// Get the genres
		Printer.info("Enter a comma-delimited list of genres:");
		String givenGenres = UserInteraction.getUserInput();
		List<String> genres = new LinkedList<String>();
		genres.addAll(Arrays.asList(givenGenres.split(",")));
		// Get the file path of lyrics
		Printer.info("Enter the filepath of the lyrics text file:");
		String lyricsFilePath = UserInteraction.getUserInput();
		// Get the file path of the audio sample
		Printer.info("Enter the filepath of the audio sample");
		String audioSampleFilePath = UserInteraction.getUserInput();
		// Get the length of the track in seconds
		Printer.info("Enter the length of the track in seconds:");
		String trackLengthSeconds = UserInteraction.getUserInput();

		// Get single attributes
		String releaseDate = null;
		String coverArt = null;
		if (isSingle) {
			// Get release date
			Printer.info("Enter the release date:");
			releaseDate = UserInteraction.getUserInput();
			// Get cover art
			Printer.info("Enter the file path of the cover art:");
			coverArt = UserInteraction.getUserInput();
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