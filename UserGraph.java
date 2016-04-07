import java.util.HashMap;
import java.util.HashSet;

public class UserGraph {
	
	/** We represent the relationships between users in the form of an undirected graph. There is an
	 * edge between two users if they share a coach-student relationship. Using an undirected graph
	 * simplifies the infection process, but we can consider implementing the graph as a directed
	 * graph if we know that we want to build additional functionality on top of the infection 
	 * process.
	 */
	
	HashSet<User> users;
	
	//graph constructor
	public UserGraph() {
		users = new HashSet<User>();
	}
	
	//returns number of users in the graph
	public int size() {
		return users.size();
	}
	
	
	//add users to the graph
	public void addUser(User u) {
		if (u == null) {
			throw new NullPointerException();
		}
		users.add(u);
	}
	
	//add an edge between two users in the graph
	public void addEdge(User u, User v) {
		if (u == null || v == null) {
			throw new NullPointerException();
		} 
		if (!users.contains(u) || !users.contains(v)) {
			throw new IllegalArgumentException();
		}
		u.addNeighbor(v);
		v.addNeighbor(u);
	}
	
	/** total_infection: 
	 *  Infects all users in user's connected component 
	 *  @param u  The user from which from the infection will start
	 *  @param sv The site version that the users will be infected with 
	 */
	public void totalInfection(User u, int sv) {
		if (u == null) {
			throw new NullPointerException();
		}
		if (!users.contains(u)) {
			throw new IllegalArgumentException();
		}
		HashSet<User> visited = new HashSet<User>(); //vertices that have already been infected
		
		dfsInfect(u, visited, sv);
	}
	
	//helper function that infects users via dfs
	private void dfsInfect(User u, HashSet<User> visited, int sv) {
		//infect u
		u.access(sv);
		visited.add(u);
		
		//recursively call on all of u's unvisited neighbors
		for (User v : u.getNeighbors()){
			if (!visited.contains(v)) {
				dfsInfect(v, visited, sv);
			}
		}
	}
	
	/** limited_infection:
	 *  I decided to implement this function so that it infects complete connected components such
	 *  that the sum of users across all infected connected components is closest to (but doesn't 
	 *  exceed) n. The reasoning here is that the algorithm safeguards against cases where coaches 
	 *  and students access different version of the site, while guaranteeing that the algorithm 
	 *  doesn't accidentally infect more users than was originally intended. <p>
	 *  
	 *  This algorithm assumes that limited_infection will be called relatively infrequently, so it 
	 *  runs a DFS in order to find the size of each connected component in the graph. If you expect
	 *  limited_infection to be called frequently, with edge-inserts in between each call, it may
	 *  be beneficial to store the size of each connected component in something like a union-find 
	 *  structure.
	 *  
	 *  @param n  The maximum number of users that will be infected
	 *  @param sv The site version that the users will be infected with
	 */
	public void limitedInfection(int n, int sv) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		
		if (users.size() == 0) {
			return;
		}
		
		//arrays storing the size and a user in each connected component
		User[] ccUser = new User[users.size() + 1];
		int[] ccSize = new int[users.size() + 1];
		
		//find the size of each connected component
		int numCC = findSize(ccUser, ccSize);
		
		/* maxSum[x][m] stores the max number of users given completely infected CCs from 1...x
		that is <= than m*/
		int[][] maxSum = new int[numCC + 1][n + 1];
		
		/* inSol[x][m] stores whether CC x is in the solution indicated by maxSum[x][m] */
		boolean[][] inSol = new boolean[numCC + 1][n + 1];
		
		//calculate optimal CCs 
		dpFindSubset(numCC, n, ccSize, maxSum, inSol);
		
		//infect all connected components that exist in the solution for maxSum[numCC-1][n]
		int m = n; //initial max sum value
		for (int x = numCC; x > 0; x--) {
			if (inSol[x][m]) {
				totalInfection(ccUser[x], sv);
				m = m - ccSize[x];
			}
		}
	}
	
	//helper function that finds out whether each CC is included in an optimal solution 
	private void dpFindSubset(int numCC, int n, int[] ccSize, int[][] maxSum, boolean[][] inSol) {
		
		//fill in above arrays depending on whether its more optimal to include each CC or not
		for (int x = 0; x < numCC; x++) {
			for (int m = 0; m <= n; m++) {
				/*
				System.out.println("x: " + x);
				System.out.println("m: " + m);*/
				
				//base cases
				if (x == 0 || m == 0) {
					maxSum[x][m] = 0;
					inSol[x][m] = false;
				} 
				
				else {
					//case where we include x
					int include;
					if (ccSize[x] <= m) {
						include = ccSize[x] + maxSum[x - 1][m - ccSize[x]];
					} else {
						include = -1;
					}

					//case where we don't include x
					int exclude = maxSum[x - 1][m];

					//record the larger of the two cases
					if (include < exclude) {
						maxSum[x][m] = exclude;
						inSol[x][m] = false;
					} else {
						maxSum[x][m] = include;
						inSol[x][m] = true;
					}
				}
			}
		}
		
	}
	
	//helper function that finds the size of each connected component, returns # of CCs
	private int findSize(User[] ccUser, int[] ccSize) {
		HashSet<User> visited = new HashSet<User>(); //vertices that have already been visited
		
		//dfs to find size on all other connected components
		int i = 1;
		for (User u : users) {
			if (!visited.contains(u)) {
				ccUser[i] = u;
				ccSize[i++] = dfsFindSize(u, visited);
			}
		}
		
		return i;
	}
	
	private int dfsFindSize(User u, HashSet<User> visited) {
		//mark u as visited
		visited.add(u);
		
		//recursively find the number of users in u
		int size = 1;
		for (User v : u.getNeighbors()) {
			if (!visited.contains(v)) {
				size = size + dfsFindSize(v, visited);
			}
		}
		
		return size;
	}
	
	/** OPTIONAL CONTENT: perfect_limited_infection:
	 *  This algorithm is a version of limited_infection that infects exactly n users and throws an 
	 *  UnsupportedOperationException if that is not possible.
	 *  
	 *  @param n  The number of users that will be infected
	 *  @param sv The site version that the users will be infected with
	 */
	public void perfectLimitedInfection(int n, int sv) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		
		if (users.size() == 0) {
			return;
		}
		
		//arrays storing the size and a user in each connected component
		User[] ccUser = new User[users.size() + 1];
		int[] ccSize = new int[users.size() + 1];
		
		//find the size of each connected component
		int numCC = findSize(ccUser, ccSize);
		
		/* maxSum[x][m] stores the max number of users given completely infected CCs from 1...x
		that is <= than m*/
		int[][] maxSum = new int[numCC + 1][n + 1];
		
		/* inSol[x][m] stores whether CC x is in the solution indicated by maxSum[x][m] */
		boolean[][] inSol = new boolean[numCC + 1][n + 1];
		
		//calculate optimal CCs 
		dpFindSubset(numCC, n, ccSize, maxSum, inSol);
		
		//if the optimal solution isn't exactly n, throw exception
		if (maxSum[numCC-1][n] != n) {
			throw new UnsupportedOperationException();
		}
		
		//else, infect all connected components that exist in the solution for maxSum[numCC-1][n]
		int m = n; //initial max sum value
		for (int x = numCC; x > 0; x--) {
			if (inSol[x][m]) {
				totalInfection(ccUser[x], sv);
				m = m - ccSize[x];
			}
		}
	}
	
}
