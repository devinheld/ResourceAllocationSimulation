/**
 * 
 * @author Devin Held ID: 26883102
 *
 */

import java.util.ArrayList;

// This class is the core of the scheduler, handling all process and resource management
public class TaskAnalyzer {
	// The main variables in this class are the resource block and the active
	// process. The resource block keeps track of the four different resources,
	// the ready list, and the waiting list. The active process keeps track of
	// which process is running, allowing us to add different processes created
	// while it is running to its creation tree. PCBs have ids, priorities,
	// status type, status list, creation tree and other resources.
	RCB resourceBlock;
	PCB activeProcess;

	// Invoke the task analyzer
	TaskAnalyzer() {
		begin();
	}

	// Create a process within the system, given id and priority
	public String create(String pid, int priority) {
		PCB pcb = new PCB(pid, priority, activeProcess);

		if (findName(pid))
			return Info.ERROR;

		// Add the process to the correct creation tree
		activeProcess.addChildToTree(pcb);

		// Add the process to the ready list and all processes list
		addToPCB(pcb);

		// Invoke the scheduler, telling us which process is running
		return scheduler();
	}

	// Destroys the corresponding process and all of the child processes
	public String destroy(String pid) {
		// Searches for a process with the corresponding pid
		PCB pcb = searchForProcess(pid);
		if (pcb == null)
			return Info.ERROR;

		// Kills the tree within the process
		kill_tree(pcb);
		return scheduler();
	}

	// Kills the child process belonging to the specified pcb
	private void kill_tree(PCB pcb) {
		// Retrieve all children of the corresponding pcb
		ArrayList<PCB> children = pcb.getChildren();
		for (int i = 0; i < children.size(); i++) {
			PCB child = children.get(i);

			// Kill the tree within each child
			kill_tree(child);
		}

		// This finalizes the deletion of the process
		updateResources(pcb);
		resourceBlock.incrementFreeUnits();
	}

	// Requests a resource
	public String request(String rid, int num) {
		// Get the resource corresponding to the rid
		Resource rcb = resourceBlock.getResource(rid);
		if (rcb == null)
			return Info.ERROR;

		if (num > 4)
			return Info.ERROR;

		rcb.num = num;

		// If the resource isnt free then continue requesting
		if (!rcb.equalStatusTypes(Info.FREE))
			requestResource();
		else {
			// Otherwise set its status to allocated and add it to the active
			// processes other resources
			rcb.status = Info.ALLOCATED;
			activeProcess.addToOtherResources(rcb);
		}
		return scheduler();
	}

	// Releases a resource
	public String release(String rid, int num) {
		// Get the resource corresponding to the rid
		Resource rcb = resourceBlock.getResource(rid);
		if (rcb == null)
			return Info.ERROR;

		if (rcb.getStatus().equals(Info.FREE) || rcb.num < num)
			return Info.ERROR;

		// Remove the resource from the active process
		activeProcess.removeFromOtherResources(rcb);

		return scheduler();
	}

	// Times out the process
	public String timeout() {
		// Find the top process
		PCB top = findTopProcess();
		if (top == null)
			return Info.ERROR;

		// Remove the top process to change its status then readd it
		removeProcess(top);
		top.statusType = Info.READY;
		addProcess(top);

		return scheduler();
	}

	// Handles which process should be running currently. Returns the pid for
	// easy writing to the output file and the console
	public String scheduler() {
		// Find the next process to run
		PCB top = findTopProcess();
		if (top == null)
			return Info.ERROR;

		// If the active process isnt running or the top process has higher
		// priority, then we want the top process to run
		if (!activeProcess.equalStatusTypes(Info.RUNNING)
				|| activeProcess.priority < top.priority) {
			top.statusType = Info.RUNNING;
			activeProcess = top;
		}

		System.out.print(activeProcess.pid + " ");
		return activeProcess.pid;
	}

	/**
	 * Helper functions fall below this line. They keep the above main functions
	 * simple and straight forward, enhancing readability and efficiency of
	 * debugging
	 */

	// Initializes the initial resource block and active process
	public String begin() {
		this.resourceBlock = new RCB();
		this.activeProcess = new PCB();

		return createInitialProcess();
	}

	// Creates the intial process and adds it to the ready list and list of all
	// processes
	public String createInitialProcess() {
		PCB pcb = new PCB("init", 0, null);
		addToPCB(pcb);

		return scheduler();
	}

	// adds process to ready list and list of all processes
	private void addToPCB(PCB pcb) {
		addProcess(pcb);

		// Decrement the number of available units in the resource blocks
		resourceBlock.decrementFreeUnits();
	}

	// Searches for a process given the pid
	private PCB searchForProcess(String pid) {

		// Search through the list of all processes
		for (int i = 0; i < resourceBlock.allProcesses.size(); i++) {
			PCB check = resourceBlock.allProcesses.get(i);

			// If the pids are equal, then we found the pcb we want
			if (check.pid.equals(pid))
				return check;
		}
		return null;
	}

	// Determines the top process that should be running currently
	public PCB findTopProcess() {
		int num = -1;
		PCB top = null;

		// Search through the processes in the ready list
		for (PCB process : resourceBlock.readyList) {

			// If the process we are on has greater priority than the already
			// saved one then we want to save this one
			if (process.priority > num) {
				num = process.priority;
				top = process;
			}
		}
		return top;
	}

	// Removes the process from the ready list and list of all processes
	public void removeProcess(PCB pcb) {
		resourceBlock.removeFromReadyList(pcb);
		resourceBlock.removeFromAllProcesses(pcb);
	}

	// Adds the process to the ready list and list of all processes
	public void addProcess(PCB pcb) {
		resourceBlock.addToReadyList(pcb);
		resourceBlock.addToAllProcesses(pcb);
	}

	// Helps with the request function
	public void requestResource() {

		// The active process becomes blocked when requesting resources
		activeProcess.statusType = Info.BLOCKED;

		// The statuslist of the active processes is not connected with the
		// waiting list of the resource block
		activeProcess.statusList = resourceBlock.waiting_list;

		// The process is no longer on the ready list, and is added to the
		// waiting list
		resourceBlock.removeFromReadyList(activeProcess);
		resourceBlock.addToWaitingList(activeProcess);
	}

	// Updates the resources in the other resources array
	private void updateResources(PCB pcb) {
		for (int i = 0; i < pcb.other_resources.size(); i++) {
			Resource r = pcb.other_resources.get(i);
			activeProcess.removeFromOtherResources(r);

			// Takes the first pcb off the waiting list and sets its status to
			// ready
			updateWaitingList();
		}

		// Clears the children in the process
		pcb.clearChildren();
		removeProcess(pcb);
	}

	// Updates resources in the waiting list
	private void updateWaitingList() {
		if (resourceBlock.waiting_list.size() != 0) {

			// Remove the first process on the waiting list and set its status
			// to ready
			PCB pcb = resourceBlock.waiting_list.remove(0);
			pcb.statusType = Info.READY;
			pcb.statusList = resourceBlock.waiting_list;

			// Adds the process to the ready list.
			resourceBlock.addToReadyList(pcb);
		}
	}

	// Checks to see if pid already exists
	boolean findName(String pid) {
		for (PCB pcb : resourceBlock.allProcesses) {
			if (pcb.pid.equals(pid))
				return true;
		}
		return false;
	}
}
