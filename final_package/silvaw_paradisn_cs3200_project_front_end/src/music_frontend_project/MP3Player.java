package music_frontend_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public final class MP3Player {

	static final String SEARCH_PATH = "./resources/audio_samples/";

	public static void playAudio(String fileName) throws IOException {
		if (fileName == null) {
			Printer.err("This song has no audio sample.");
			return;
		}

		String filePath = SEARCH_PATH + fileName;
		File f = new File(filePath);
		if (!f.exists() || f.isDirectory()) {
			Printer.err("Could not find file " + filePath);
			Printer.infoln();
			return;
		}

		// fxPanel required to initialize toolkit
		@SuppressWarnings("unused")
		JFXPanel fxPanel = new JFXPanel();
		Media media = new Media(Paths.get(filePath).toUri().toString());
		MediaPlayer mp = new MediaPlayer(media);
		mp.play();

		Printer.info("Playing... Press enter to continue.");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();

		mp.stop();
	}

}
