/**
 * 
 * @author Devin Held ID: 26883102
 *
 */

import java.util.ArrayList;

// Simple object to represent process control blocks
public class PCB {

	// PCBs consist of a pid, other resources, status types, status lists,
	// creation trees and priorities
	public int priority;
	public String pid;
	public String statusType = Info.NULL;
	public ArrayList<PCB> statusList;
	private CreationTree creation_tree;
	public ArrayList<Resource> other_resources;

	// This initializer is used when creating the initial process which does not
	// have a parent
	PCB() {

	}

	// The basic initializer takes a pid, priority, and parent process. It
	// initializes the status list, creation tree and other resources
	PCB(String pid, int priority, PCB parent) {
		this.priority = priority;
		this.pid = pid;
		this.statusList = new ArrayList<PCB>();
		this.creation_tree = new CreationTree(parent);
		this.other_resources = new ArrayList<Resource>();
	}

	// Helps when checking to see if a pcb is runnning or blocked
	public boolean equalStatusTypes(String type) {
		return statusType.equals(type);
	}

	// Gets the children from the creation tree. Helps with searching and
	// destroying
	public ArrayList<PCB> getChildren() {
		return creation_tree.children;
	}

	// Adds a child to the creation tree
	public void addChildToTree(PCB pcb) {
		creation_tree.addChild(pcb);
	}

	// Clears all children from the creation tree. Used when destroying
	// processes
	public void clearChildren() {
		creation_tree.clearChildren();
		statusType = Info.NULL;
	}

	// Removes resource from list of other resources
	public void removeFromOtherResources(Resource r) {
		other_resources.remove(r);
	}

	// Adds resource to list of other resources
	public void addToOtherResources(Resource r) {
		other_resources.add(r);
	}
}
