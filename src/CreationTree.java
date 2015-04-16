/**
 * 
 * @author Devin Held ID: 26883102
 *
 */

import java.util.ArrayList;

// Simple object to represent the creation tree
class CreationTree {
	public PCB parent;
	public ArrayList<PCB> children;

	// A creation tree has a parent process, as well as a list of child
	// processes which are associated with the parent. This allows easy deletion
	// of all processes that were created from another one.
	CreationTree(PCB parent) {
		this.parent = parent;
		this.children = new ArrayList<PCB>();
	}

	// Adds a child process to the tree
	public void addChild(PCB pcb) {
		children.add(pcb);
	}

	// Clears all children from the tree. Useful when destroying processes
	public void clearChildren() {
		children = new ArrayList<PCB>();
	}

}