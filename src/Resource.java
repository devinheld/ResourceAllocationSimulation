/**
 * 
 * @author Devin Held ID: 26883102
 *
 */

// Simple object to represent individual resource
public class Resource {
	public String rid;
	public String status;
	public int num;

	Resource(String rid, int num) {
		this.setRid(rid);
		this.setStatus(Info.FREE);
		this.num = num;
	}

	// Helps when comparing status types
	public boolean equalStatusTypes(String type) {
		return status.equals(type);
	}

	// returns rid
	public String getRid() {
		return rid;
	}

	// Allows easy setting for the rid
	public void setRid(String rid) {
		this.rid = rid;
	}

	// Returns status
	public String getStatus() {
		return status;
	}

	// Sets the status
	public void setStatus(String status) {
		this.status = status;
	}
}