package music_frontend_project;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

public class SearchMenu {

	DBConnector dbConn;

	public SearchMenu(DBConnector c) {
		dbConn = c;
	}

	private void mainMenu() throws IOException, SQLException {
		MenuSystem mainMenu = new MenuSystem(dbConn);
		mainMenu.mainMenu();
	}

	public void searchMenu() throws IOException, SQLException {
		Printer.info("Enter text to search for:");
		String userInput = UserInteraction.getUserInput();
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

		int userPick = UserInteraction.provideUserPick(searchedArtists.size()
				+ searchedAlbums.size() + searchedSongs.size(),
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

	public void viewAlbum(Integer albumId) throws IOException, SQLException {
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

		String genresDelimited = "";
		for (int i = 0; i < genres.size(); i++) {
			artistsDelimited += genres.get(i);
			if (i != genres.size() - 1) {
				genresDelimited += ",";
			}
		}
		Printer.info(genresDelimited);

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

		UserInteraction.printOptions("View Song", "View Artist",
				"Show Album Art", "Modify", "Delete", "Main Menu");
		Integer choice = UserInteraction.provideUserPick(5);
		switch (choice) {
		case (0):
			for (int i = 0; i < tracks.size(); i++) {
				String trackName = tracks.get(i).getValue0();
				Printer.info("" + i + ". " + trackName);
			}
			Integer trackChoice = UserInteraction
					.provideUserPick(tracks.size() - 1);
			int trackId = tracks.get(trackChoice).getValue1();
			viewSong(trackId);
			break;
		case (1):
			for (int i = 0; i < artists.size(); i++) {
				String artistName = artists.get(i);
				Printer.info("" + i + ". " + artistName);
			}
			Integer artistChoice = UserInteraction.provideUserPick(artists
					.size() - 1);
			String artistName = artists.get(artistChoice);
			viewArtist(artistName);
			break;
		case (2):
			String albumFilePath = albumInfo.getValue2();
			ImageViewer.viewImage(albumFilePath);
			viewAlbum(albumId);
			break;
		case (3):
			ModifyMenu modifyMenu = new ModifyMenu(dbConn);
			modifyMenu.modifyAlbum(albumId);
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

	void viewSong(int trackId) throws SQLException, IOException {
		if (!dbConn.isSingle(trackId)) {
			viewGeneralSong(trackId);
		} else {
			viewSingle(trackId);
		}

	}

	private void viewSingle(int trackId) throws SQLException, IOException {
		// Track Name, Track Number, Album Name, Album id, Lyric filepath,
		// Sample filepath
		Sextet<String, Integer, String, Integer, String, String> baseSongInfo = dbConn
				.getBaseSongInfo(trackId);
		// Release Date, Cover Art Filepath
		Pair<String, String> singleSongInfo = dbConn.getSingleSongInfo(trackId);
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
		// Release Date
		Printer.info("Release Date: " + singleSongInfo.getValue0());

		// Show menu
		UserInteraction.printOptions("Show Album", "Show Artist",
				"Show Ft Artist", "Show Lyrics", "Play Audio Sample",
				"Show Cover Art", "Modify", "Delete", "Main Menu");
		Integer choice = UserInteraction.provideUserPick(8);
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
			int artistChoice = UserInteraction
					.provideUserPick(artists.size() - 1);
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
			int ftArtistChoice = UserInteraction.provideUserPick(ftArtists
					.size() - 1);
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
			// Show Cover Art
			String singleCoverArtFilePath = singleSongInfo.getValue1();
			ImageViewer.viewImage(singleCoverArtFilePath);
			viewSong(trackId);
			break;
		case (6):
			// Modify
			ModifyMenu modifyMenu = new ModifyMenu(dbConn);
			modifyMenu.modifySingleSong(trackId);
			break;
		case (7):
			// Delete
			dbConn.deleteSong(trackId);
			Printer.info("Song deleted.");
			mainMenu();
			break;
		case (8):
			// Main menu
			mainMenu();
			break;
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
		UserInteraction.printOptions("Show Album", "Show Artist",
				"Show Ft Artist", "Show Lyrics", "Play Audio Sample", "Modify",
				"Delete", "Main Menu");
		Integer choice = UserInteraction.provideUserPick(7);
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
			int artistChoice = UserInteraction
					.provideUserPick(artists.size() - 1);
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
			int ftArtistChoice = UserInteraction.provideUserPick(ftArtists
					.size() - 1);
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
			ModifyMenu modifyMenu = new ModifyMenu(dbConn);
			modifyMenu.modifyGeneralSong(trackId);
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

	private void viewArtist(String artist) throws SQLException, IOException {
		// Album, album id
		List<Pair<String, Integer>> albumsByArtist = dbConn
				.getAlbumsByArtist(artist);
		Printer.info("Artist Name: " + artist);
		for (Pair<String, Integer> p : albumsByArtist) {
			Printer.info(p.getValue0());
		}
		Printer.info("");
		UserInteraction.printOptions("View Album", "Modify", "Delete",
				"Main Menu");
		Integer choice = UserInteraction.provideUserPick(3);
		switch (choice) {
		case (0):
			for (int i = 0; i < albumsByArtist.size(); i++) {
				String albumName = albumsByArtist.get(i).getValue0();
				Printer.info("" + i + ". " + albumName);
			}
			Integer albumChoice = UserInteraction
					.provideUserPick(albumsByArtist.size() - 1);
			int albumId = albumsByArtist.get(albumChoice).getValue1();
			viewAlbum(albumId);
			break;
		case (1):
			Printer.info("Enter new artist name:");
			String userChange = UserInteraction.getUserInput();
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

}
