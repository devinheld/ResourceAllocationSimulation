/**
 * 
 * @author Devin Held ID: 26883102
 *
 */

// Constants for use throughout the program
public class Info {

	// Name of input file
	public static final String INPUT_FILE = "/Volumes/NO NAME/input.txt";

	// Name of output file
	public static final String OUTPUT_FILE = "/Volumes/NO NAME/26883102.txt";

	// Status type for free resource block
	public static final String FREE = "free";

	// Status type for allocated resource block
	public static final String ALLOCATED = "allocated";

	// Status type for ready process
	public static final String READY = "ready";

	// Status type for running process
	public static final String RUNNING = "running";

	// Status type for blocked process
	public static final String BLOCKED = "blocked";

	// ID for empty name for RCB and PCB
	public static final String NULL = "null";

	// Inital priority when one wasn't initialized
	public static final int NO_PRIORITY = -1;

	// Used when trying to destroy a non existing process or release or request
	// an absent block
	public static final String ERROR = "error";

}
