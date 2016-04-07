import static org.junit.Assert.*;

import org.junit.Test;

public class TotalInfectionTests {

	//testing on null user
	@Test(expected = NullPointerException.class)
	public void tiNullTest() {
		UserGraph g = new UserGraph();
		g.totalInfection(null, 1);
	}
	
	//testing on non-existant user
	@Test(expected = IllegalArgumentException.class)
	public void tiIllegalUserTest() {
		UserGraph g = new UserGraph();
		User a = new User("a", 1);
		g.totalInfection(a, 1);
	}
	
	//testing on graph of size one
	@Test
	public void tiSizeOneTest() {
		UserGraph g = new UserGraph();
		User a = new User("a", 1);
		g.addUser(a);
		g.totalInfection(a, 10);
		assertEquals(a.getSiteAccessed(), 10);
	}
	
	//testing on connected component of size one
	@Test
	public void tiCCSizeOneTest() {
		UserGraph g = new UserGraph();
		User a = new User("a", 1);
		User b = new User("b", 2);
		User c = new User("c", 3);
		User d = new User("d", 4);
		
		g.addUser(a);
		g.addUser(b);
		g.addUser(c);
		g.addUser(d);
		
		g.totalInfection(a, 10);
		
		assertEquals(a.getSiteAccessed(), 10);
		assertEquals(b.getSiteAccessed(), 2);
		assertEquals(c.getSiteAccessed(), 3);
		assertEquals(d.getSiteAccessed(), 4);
	}
	
	//testing on a larger connected component, where all nodes share an edge with u
	@Test
	public void tiOneLargerCCSharedEdgesTest() {
		UserGraph g = new UserGraph();
		User a = new User("a", 1);
		User b = new User("b", 2);
		User c = new User("c", 3);
		User d = new User("d", 4);
		User e = new User("e", 5);
		
		g.addUser(a);
		g.addUser(b);
		g.addUser(c);
		g.addUser(d);
		g.addUser(e);
		
		g.addEdge(a, b);
		g.addEdge(a, c);
		g.addEdge(d, e);
		
		g.totalInfection(a, 10);
		
		assertEquals(a.getSiteAccessed(), 10);
		assertEquals(b.getSiteAccessed(), 10);
		assertEquals(c.getSiteAccessed(), 10);
		assertEquals(d.getSiteAccessed(), 4);
		assertEquals(e.getSiteAccessed(), 5);
	}
	
	//testing on a larger connected component, where not all nodes share an edge with u
	@Test
	public void tiOneLargerCCNotAllSharedEdgesTest() {
		UserGraph g = new UserGraph();
		User a = new User("a", 1);
		User b = new User("b", 2);
		User c = new User("c", 3);
		User d = new User("d", 4);
		User e = new User("e", 5);
		
		g.addUser(a);
		g.addUser(b);
		g.addUser(c);
		g.addUser(d);
		g.addUser(e);
		
		g.addEdge(a, b);
		g.addEdge(b, c);
		g.addEdge(d, e);
		
		g.totalInfection(a, 10);
		
		assertEquals(a.getSiteAccessed(), 10);
		assertEquals(b.getSiteAccessed(), 10);
		assertEquals(c.getSiteAccessed(), 10);
		assertEquals(d.getSiteAccessed(), 4);
		assertEquals(e.getSiteAccessed(), 5);
	}
	
	//testing on a complex connected component
	@Test
	public void tiComplexCCTest() {
		UserGraph g = new UserGraph();
		User a = new User("a", 1);
		User b = new User("b", 2);
		User c = new User("c", 3);
		User d = new User("d", 4);
		User e = new User("e", 5);
		User f = new User("f", 6);
		User h = new User("h", 7);
		User i = new User("i", 8);
		
		g.addUser(a);
		g.addUser(b);
		g.addUser(c);
		g.addUser(d);
		g.addUser(e);
		g.addUser(f);
		g.addUser(h);
		g.addUser(i);
		
		g.addEdge(a, b);
		g.addEdge(a, c);
		g.addEdge(a, i);
		g.addEdge(c, d);
		g.addEdge(c, e);
		g.addEdge(b, f);
		g.addEdge(b, h);
		g.addEdge(i, h);
		
		g.totalInfection(a, 10);
		
		assertEquals(a.getSiteAccessed(), 10);
		assertEquals(b.getSiteAccessed(), 10);
		assertEquals(c.getSiteAccessed(), 10);
		assertEquals(d.getSiteAccessed(), 10);
		assertEquals(e.getSiteAccessed(), 10);
		assertEquals(f.getSiteAccessed(), 10);
		assertEquals(h.getSiteAccessed(), 10);
		assertEquals(i.getSiteAccessed(), 10);
	} 
	
	//testing on more than one connected component
	@Test
	public void tiMoreThanOneCCTest() {
		UserGraph g = new UserGraph();
		User a = new User("a", 1);
		User b = new User("b", 2);
		User c = new User("c", 3);
		User d = new User("d", 4);
		User e = new User("e", 5);
		User f = new User("f", 6);
		User h = new User("h", 7);
		
		g.addUser(a);
		g.addUser(b);
		g.addUser(c);
		g.addUser(d);
		g.addUser(e);
		g.addUser(f);
		g.addUser(h);
		
		g.addEdge(a, b);
		g.addEdge(b, c);
		g.addEdge(b, d);
		g.addEdge(c, d);
		g.addEdge(e, f);
		g.addEdge(f, h);
		
		g.totalInfection(a, 10);
		
		assertEquals(a.getSiteAccessed(), 10);
		assertEquals(b.getSiteAccessed(), 10);
		assertEquals(c.getSiteAccessed(), 10);
		assertEquals(d.getSiteAccessed(), 10);
		assertEquals(e.getSiteAccessed(), 5);
		assertEquals(f.getSiteAccessed(), 6);
		assertEquals(h.getSiteAccessed(), 7);
		
		g.totalInfection(c, 30);
		assertEquals(a.getSiteAccessed(), 30);
		assertEquals(b.getSiteAccessed(), 30);
		assertEquals(c.getSiteAccessed(), 30);
		assertEquals(d.getSiteAccessed(), 30);
		assertEquals(e.getSiteAccessed(), 5);
		assertEquals(f.getSiteAccessed(), 6);
		assertEquals(h.getSiteAccessed(), 7);
		
		g.totalInfection(h, 70);
		assertEquals(a.getSiteAccessed(), 30);
		assertEquals(b.getSiteAccessed(), 30);
		assertEquals(c.getSiteAccessed(), 30);
		assertEquals(d.getSiteAccessed(), 30);
		assertEquals(e.getSiteAccessed(), 70);
		assertEquals(f.getSiteAccessed(), 70);
		assertEquals(h.getSiteAccessed(), 70);
	} 
}
