/**
 * 
 * @author Devin Held ID: 26883102
 *
 */

// Entry point to the program
public class Main {

	public static void main(String[] args) {
		Shell shell;

		// If there isn't any arguments specified, default to input.txt
		if (args.length == 0)
			shell = new Shell(Info.INPUT_FILE);
		else
			// otherwise use the provided argument
			shell = new Shell(args[0]);

		// Begin parsing the file, which invokes the task analyzer
		shell.parseFile();
	}
}
