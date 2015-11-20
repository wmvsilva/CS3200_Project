import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {

	static final String INPUT_FILE = "config.txt";
	static final String OUTPUT_FILE = "data_loader.sql";

	public static void main(String[] args) throws IOException {
		File dir = new File(".");
		List<File> files = readConfig();
		FileWriter fw = new FileWriter(OUTPUT_FILE);

		for (File f : files) {
			if (f.getName().contains(".csv")) {
				transferToFile(f, fw);
			}
		}
		fw.close();
	}

	public static List<File> readConfig() throws IOException {
		List<File> result = new LinkedList<File>();
		BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
		String line = br.readLine();
		while (line != null) {
			result.add(new File(line));
			line = br.readLine();
		}

		return result;
	}

	public static List<File> allRecursiveFiles(File dir) {
		List<File> result = new LinkedList<File>();
		File[] directoryListing = dir.listFiles();

		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.isFile()) {
					result.add(child);
				} else {
					result.addAll(allRecursiveFiles(child));
				}
			}
		}

		return result;
	}

	public static void transferToFile(File f, FileWriter fw) throws IOException {
		System.out.println(f.getName());
		String tableName = f.getName().replace(".csv", "");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String firstLine = br.readLine();
		firstLine = firstLine.replace("\"", "");
		String insert = "insert into " + tableName + "(" + firstLine
				+ ")\n\tvalues ";

		String line = br.readLine();
		while (line != null) {
			String values = "("
					+ line.replace("\"", "'").replace("\\N", "NULL") + ")";
			fw.write(insert + values + ";\n");
			line = br.readLine();
		}
		br.close();
	}
}
