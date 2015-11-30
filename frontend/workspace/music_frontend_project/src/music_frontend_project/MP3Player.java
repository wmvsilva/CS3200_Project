package music_frontend_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public final class MP3Player {

	public static void playAudio(String filepath) throws IOException {
		// fxPanel required to initialize toolkit
		@SuppressWarnings("unused")
		JFXPanel fxPanel = new JFXPanel();
		Media media = new Media(Paths.get(filepath).toUri().toString());
		MediaPlayer mp = new MediaPlayer(media);
		mp.play();

		System.out.println("Playing...");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();

		mp.stop();
	}

}
