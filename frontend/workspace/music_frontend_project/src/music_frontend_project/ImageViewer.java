package music_frontend_project;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public final class ImageViewer {

	final static String SINGLE_COVER_ART_SEARCH_PATH = "./resources/single_cover_art/";

	static void viewImage(String filepath) {
		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon(filepath);
		JLabel label = new JLabel(icon);
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void viewSingleCoverArtImage(String singleCoverArtFileName) {
		if (singleCoverArtFileName == null) {
			Printer.err("This song has no cover art.");
			Printer.infoln();
			return;
		}

		String filePath = SINGLE_COVER_ART_SEARCH_PATH + singleCoverArtFileName;
		File f = new File(filePath);
		if (!f.exists() || f.isDirectory()) {
			Printer.err("Could not find file " + filePath);
			Printer.infoln();
			return;
		}

		viewImage(filePath);
	}

}