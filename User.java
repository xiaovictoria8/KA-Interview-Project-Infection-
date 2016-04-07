import java.util.HashSet;

public class User {
	
	/** Each user is represented as a node in an undirected graph. Each node stores a set of all its
	 * edges for the sake of elegant code.
	 */
	
	// userID, presumably there is a process that ensures IDs are unique
	private String userID;
	
	// the version of site that user is currently accessing. 
	private int siteAccessed;
	
	//set of all user's neighbors 
	private HashSet<User> neighbors;
	
	//object constructor
	public User(String userID, int sv) {
		this.userID = userID;
		this.siteAccessed = sv;
		siteAccessed = sv;
		neighbors = new HashSet<User>();
	}
	
	//return set of all neighbors
	public HashSet<User> getNeighbors() {
		return neighbors;
	}
	
	//add new neighbor
	public void addNeighbor(User u) {
		if (u == null) {
			throw new NullPointerException();
		}
		neighbors.add(u);
	}
	
	//returns version of site that user is accessing
	public int getSiteAccessed() {
		return siteAccessed;
	}
	
	//change version of site user is accessing
	public void access(int v) {
		siteAccessed = v;
	}
	
	//each user's string representation is its userID
	public String toString() {
		return userID;
	}
	
}
