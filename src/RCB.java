/**
 * 
 * @author Devin Held ID: 26883102
 *
 */

import java.util.ArrayList;

// Simple object to represent the resource control block
public class RCB {

	// Represents the list of waiting processes
	public ArrayList<PCB> waiting_list, readyList, allProcesses;

	// Resources within the block
	private Resource r1, r2, r3, r4;

	// Counter for number of free units
	private int status;

	// Initializes all resources inside the block and creates the empty waiting
	// list
	RCB() {
		this.r1 = new Resource("R1", 0);
		this.r2 = new Resource("R2", 0);
		this.r3 = new Resource("R3", 0);
		this.r4 = new Resource("R4", 0);
		this.setStatus(4);
		this.waiting_list = new ArrayList<PCB>();
		this.readyList = new ArrayList<PCB>();
		this.allProcesses = new ArrayList<PCB>();
	}

	// Returns the corresponding resource based on the given rid
	public Resource getResource(String rid) {
		switch (rid) {
		case "R1":
			return r1;
		case "R2":
			return r2;
		case "R3":
			return r3;
		case "R4":
			return r4;
		default:
			return null;
		}
	}

	// Adds process to the list of all processes. Allows easy deletion and
	// searching.
	public void addToAllProcesses(PCB pcb) {
		allProcesses.add(pcb);
	}

	// Removes process from list of all processes
	public void removeFromAllProcesses(PCB pcb) {
		allProcesses.remove(pcb);
	}

	// Adds process to ready list
	public void addToReadyList(PCB pcb) {
		readyList.add(pcb);
	}

	// Removes process from ready list
	public void removeFromReadyList(PCB pcb) {
		readyList.remove(pcb);
	}

	// Keeps track of how many resources are free
	public void decrementFreeUnits() {
		status--;
	}

	// Keeps track of how many resources are free
	public void incrementFreeUnits() {
		status++;
	}

	// Added to suppress warnings
	public int getStatus() {
		return status;
	}

	// Added to suppress warnings
	public void setStatus(int status) {
		this.status = status;
	}

	// Adds process to waiting list
	public void addToWaitingList(PCB pcb) {
		waiting_list.add(pcb);
	}

	// Removes process from waiting list
	public void removeFromWaitingList(PCB pcb) {
		waiting_list.remove(pcb);
	}

}
