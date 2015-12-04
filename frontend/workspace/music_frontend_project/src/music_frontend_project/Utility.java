package music_frontend_project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

	public static boolean isValidDate(String newReleaseDate) {
		Pattern p = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");
		Matcher m = p.matcher(newReleaseDate);
		return m.find() && newReleaseDate.length() == 10;
	}
}
