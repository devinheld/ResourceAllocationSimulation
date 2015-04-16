/**
 * 
 * @author Devin Held ID: 26883102
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

// Reads file, and seperates lines and commands accordingly.
public class Shell {
	String inputFile;
	TaskAnalyzer taskAnalyzer;

	// Shell is initialized with an input file.
	// Initializes new task analyzer which will handle the interactions
	public Shell(String inputFile) {
		this.inputFile = inputFile;
		this.taskAnalyzer = new TaskAnalyzer();
	}

	// Beings parsing file, initializing readers and saves file to disk
	public void parseFile() {
		BufferedReader buffReader = null;
		FileReader file = null;
		String out = "";

		try {
			file = new FileReader(inputFile);
			buffReader = new BufferedReader(file);
			out = parseCommands(buffReader);
			buffReader.close();
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileSave(out);
		}
	}

	// Saves file holding all outputs to disk
	public void fileSave(String fileBody) {
		try {
			PrintWriter out = new PrintWriter(Info.OUTPUT_FILE);
			out.write(fileBody);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Calls appropriate function to analyze input
	public String readLine(String inputLine) {
		// Index 0 will be the command, the rest will be the extra information
		String[] line = inputLine.split(" ");

		switch (line[0]) {
		case "init":
			return taskAnalyzer.begin();
		case "cr":
			return taskAnalyzer.create(line[1], Integer.valueOf(line[2]));
		case "de":
			return taskAnalyzer.destroy(line[1]);
		case "req":
			return taskAnalyzer.request(line[1], Integer.valueOf(line[2]));
		case "rel":
			return taskAnalyzer.release(line[1], Integer.valueOf(line[2]));
		case "to":
			return taskAnalyzer.timeout();
		}
		return "";
	}

	// Receives the results from the line evaluation and creates output string
	private String parseCommands(BufferedReader buffReader) throws IOException {
		StringBuilder builder = new StringBuilder();
		String line = buffReader.readLine();
		builder.append("init\n");
		while (line != null) {
			if (line.equals("") || line == null) {
				builder.append("\n");
			} else {
				builder.append(readLine(line.trim()));
				builder.append("\n");
			}
			line = buffReader.readLine();
		}

		return builder.toString();

	}

}
