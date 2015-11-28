package music_frontend_project;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public final class ImageViewer {

	static void viewImage(String filepath) {
		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon(filepath);
		JLabel label = new JLabel(icon);
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}