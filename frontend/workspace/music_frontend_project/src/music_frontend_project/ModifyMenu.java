package music_frontend_project;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

public class ModifyMenu {

	DBConnector dbConn;

	public ModifyMenu(DBConnector c) {
		dbConn = c;
	}

	void modifySingleSong(int trackId) throws SQLException, IOException {
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
		int countAfterGenre = count;
		Printer.info("" + count + ". Release Date: "
				+ singleSongInfo.getValue0());

		Printer.infoln();
		int userInput = UserInteraction.provideUserPick(4 + artists.size()
				+ ftArtists.size() + genres.size(),
				"Enter a number to modify or 0 to go back:");

		if (userInput == 0) {
			viewSong(trackId);
			return;
		} else if (userInput == 1) {
			Printer.info("Enter a value:");
			String newTrackName = UserInteraction.getUserInput();
			dbConn.modifyTrackName(trackId, newTrackName);
			viewSong(trackId);
			return;
		} else if (userInput == 2) {
			Printer.info("Enter a value:");
			String newTrackNumber = UserInteraction.getUserInput();
			dbConn.modifyTrackNumber(trackId, newTrackNumber);
			viewSong(trackId);
			return;
		} else if (userInput == 3) {
			Printer.info("Enter the new album name:");
			String newAlbum = UserInteraction.getUserInput();
			List<String> releaseDates = dbConn.getReleaseDatesOfAlbum(newAlbum);
			if (releaseDates.isEmpty()) {
				Printer.err("Found no albums with name " + newAlbum);
				modifySingleSong(trackId);
				return;
			}
			for (String date : releaseDates) {
				Printer.info(newAlbum + " - " + date);
			}
			Printer.info("Enter the new album release date:");
			String newReleaseDate = null;
			while (newReleaseDate == null) {
				String userDateInput = UserInteraction.getUserInput();
				if (releaseDates.contains(userDateInput)) {
					newReleaseDate = userDateInput;
				}
				Printer.info("Please enter a valid date from one of the above albums:");
			}
			dbConn.modifySongAlbum(trackId, newAlbum, newReleaseDate);
			viewSong(trackId);
			return;
		} else if (userInput < countAfterArtist) {
			Printer.info("Enter a value:");
			String newArtist = UserInteraction.getUserInput();
			String oldArtist = artists.get(userInput - 4);
			modifySongArtist(trackId, oldArtist, newArtist);
			viewSong(trackId);
			return;
		} else if (userInput < countAfterFtArtist) {
			Printer.info("Enter a value:");
			String newFtArtist = UserInteraction.getUserInput();
			String oldFtArtist = ftArtists.get(userInput - 4 - artists.size());
			dbConn.modifySongFtArtist(trackId, oldFtArtist, newFtArtist);
			viewSong(trackId);
			return;
		} else if (userInput < countAfterGenre) {
			Printer.info("Enter a value:");
			String newGenre = UserInteraction.getUserInput();
			String oldGenre = genres.get(userInput - 4 - artists.size()
					- ftArtists.size());
			modifySongGenre(trackId, oldGenre, newGenre);
			viewSong(trackId);
			return;
		} else {
			Printer.info("Enter a value:");
			String newReleaseDate = UserInteraction.getUserInput();
			dbConn.modifySingleReleaseDate(trackId, newReleaseDate);
			viewSong(trackId);
			return;
		}
	}

	private void modifySongGenre(int trackId, String oldGenre, String newGenre)
			throws SQLException {
		if (!dbConn.doesGenreExist(trackId, oldGenre, newGenre)) {
			Printer.err("Genre " + newGenre + " does not exist.");
			Printer.err("");
			return;
		}
		dbConn.modifySongGenre(trackId, oldGenre, newGenre);
	}

	private void modifySongArtist(int trackId, String oldArtist,
			String newArtist) throws SQLException, IOException {
		if (!dbConn.doesArtistExist(newArtist)) {
			Printer.err("Artist " + newArtist + " does not exist.");
			viewSong(trackId);
		}
		dbConn.modifySongArtist(trackId, oldArtist, newArtist);
	}

	void modifyGeneralSong(int trackId) throws SQLException, IOException {
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
		Printer.infoln();

		int userInput = UserInteraction.provideUserPick(3 + artists.size()
				+ ftArtists.size() + genres.size(),
				"Enter number to modify or 0 to go back:");

		if (userInput == 0) {
			viewSong(trackId);
			return;
		} else if (userInput == 1) {
			Printer.info("Enter a value:");
			String newTrackName = UserInteraction.getUserInput();
			dbConn.modifyTrackName(trackId, newTrackName);
			viewSong(trackId);
			return;
		} else if (userInput == 2) {
			Printer.info("Enter a value:");
			String newTrackNumber = UserInteraction.getUserInput();
			dbConn.modifyTrackNumber(trackId, newTrackNumber);
			viewSong(trackId);
			return;
		} else if (userInput == 3) {
			Printer.info("Enter the new album name:");
			String newAlbum = UserInteraction.getUserInput();
			List<String> releaseDates = dbConn.getReleaseDatesOfAlbum(newAlbum);
			if (releaseDates.isEmpty()) {
				Printer.err("Found no albums with name " + newAlbum);
				modifyGeneralSong(trackId);
				return;
			}
			for (String date : releaseDates) {
				Printer.info(newAlbum + " - " + date);
			}
			Printer.info("Enter the new album release date:");
			String newReleaseDate = null;
			while (newReleaseDate == null) {
				String userDateInput = UserInteraction.getUserInput();
				if (releaseDates.contains(userDateInput)) {
					newReleaseDate = userDateInput;
				}
				Printer.info("Please enter a valid date from one of the above albums:");
			}
			dbConn.modifySongAlbum(trackId, newAlbum, newReleaseDate);
			viewSong(trackId);
			return;
		} else if (userInput < countAfterArtist) {
			Printer.info("Enter a value:");
			String newArtist = UserInteraction.getUserInput();
			String oldArtist = artists.get(userInput - 4);
			modifySongArtist(trackId, oldArtist, newArtist);
			viewSong(trackId);
			return;
		} else if (userInput < countAfterFtArtist) {
			Printer.info("Enter a value:");
			String newFtArtist = UserInteraction.getUserInput();
			String oldFtArtist = ftArtists.get(userInput - 4 - artists.size());
			dbConn.modifySongFtArtist(trackId, oldFtArtist, newFtArtist);
			viewSong(trackId);
			return;
		} else {
			Printer.info("Enter a value:");
			String newGenre = UserInteraction.getUserInput();
			String oldGenre = genres.get(userInput - 4 - artists.size()
					- ftArtists.size());
			modifySongGenre(trackId, oldGenre, newGenre);
			viewSong(trackId);
			return;
		}
	}

	void modifyAlbum(Integer albumId) throws SQLException, IOException {
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

		int userInput = UserInteraction.provideUserPick(1 + 2 + artists.size()
				+ genres.size() + 3 * albumStoreInfo.size(),
				"Enter number to modify or 0 to go back:");
		if (userInput == 0) {
			viewAlbum(albumId);
			return;
		} else if (userInput == 1) {
			Printer.info("Enter a new value:");
			String newAlbumName = UserInteraction.getUserInput();
			dbConn.modifyAlbumName(albumId, newAlbumName);
			viewAlbum(albumId);
			return;
		} else if (userInput == 2) {
			Printer.info("Enter a new value:");
			String newReleaseDate = UserInteraction.getUserInput();
			dbConn.modifyAlbumReleaseDate(albumId, newReleaseDate);
			viewAlbum(albumId);
			return;
		} else if (userInput < countAfterArtist) {
			Printer.info("Enter a new value:");
			String newArtistName = UserInteraction.getUserInput();
			String oldArtistName = artists.get(userInput - 3);
			dbConn.modifyAlbumArtist(albumId, oldArtistName, newArtistName);
			viewAlbum(albumId);
			return;
		} else if (userInput < countAfterGenre) {
			Printer.info("Enter a new value:");
			String newGenreName = UserInteraction.getUserInput();
			String oldGenreName = genres.get(userInput - 3 - artists.size());
			dbConn.modifyAlbumGenre(albumId, oldGenreName, newGenreName);
			Printer.info("Modifed genre " + oldGenreName + " to genre "
					+ newGenreName + ".");
			viewAlbum(albumId);
			return;
		} else {
			Printer.info("Enter a new value:");
			String newValue = UserInteraction.getUserInput();
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

	private void viewAlbum(int albumId) throws IOException, SQLException {
		SearchMenu searchMenu = new SearchMenu(dbConn);
		searchMenu.viewAlbum(albumId);
	}

	private void viewSong(int songId) throws IOException, SQLException {
		SearchMenu searchMenu = new SearchMenu(dbConn);
		searchMenu.viewSong(songId);
	}

}
