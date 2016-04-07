import static org.junit.Assert.*;

import org.junit.Test;

public class PerfectLimitedInfectionTests {

	//testing on null user
	@Test
	public void pliNullTest() {
		UserGraph g = new UserGraph();
		g.perfectLimitedInfection(5, 10);

		assertTrue(g.size() == 0);
	}
	
	//testing on negative n value
	@Test(expected = IllegalArgumentException.class)
	public void pliNegativeNTest() {
		UserGraph g = new UserGraph();
		g.perfectLimitedInfection(-1, 10);
	}
	
	//testing on n = 0
	@Test
	public void pliZeroNTest() {
		UserGraph g = new UserGraph();
		User a = new User("a", 1);
		User b = new User("b", 2);
		User c = new User("c", 3);
		g.addUser(a);
		g.perfectLimitedInfection(0, 10);
		assertEquals(a.getSiteAccessed(), 1);
		assertEquals(b.getSiteAccessed(), 2);
		assertEquals(c.getSiteAccessed(), 3);
	}
	
	//testing when only one CC is = than n
	@Test
	public void pliInfectOneCCTest() {
		UserGraph g = new UserGraph();
		User a = new User("a", 1);
		User b = new User("b", 2);
		User c = new User("c", 3);
		User d = new User("d", 4);
		User e = new User("e", 5);
		User f = new User("f", 6);
		
		g.addUser(a);
		g.addUser(b);
		g.addUser(c);
		g.addUser(d);
		g.addUser(e);
		g.addUser(f);
		
		g.addEdge(b, c);
		g.addEdge(d, e);
		g.addEdge(d, f);
		
		g.perfectLimitedInfection(1, 10);
		assertEquals(a.getSiteAccessed(), 10);
		assertEquals(b.getSiteAccessed(), 2);
		assertEquals(c.getSiteAccessed(), 3);
		assertEquals(d.getSiteAccessed(), 4);
		assertEquals(e.getSiteAccessed(), 5);
		assertEquals(f.getSiteAccessed(), 6);
	}
	
	//test when there is no way to infect exactly n users
	@Test(expected = UnsupportedOperationException.class)
	public void pliInfectMultipleCCTest() {
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
		
		g.addEdge(d, e);
		g.addEdge(e, f);
		g.addEdge(d, h);
		g.addEdge(f, i);
		
		g.perfectLimitedInfection(4, 10);
	}
	
	//test when infected CCs aren't CCs with adjacent sizes
	@Test
	public void pliInfectLessThanNTest() {
		UserGraph g = new UserGraph();
		User a = new User("a", 1);
		User b = new User("b", 2);
		User c = new User("c", 3);
		User d = new User("d", 4);
		User e = new User("e", 5);
		User f = new User("f", 6);
		User h = new User("h", 7);
		User i = new User("i", 8);
		User j = new User("j", 9);
		User k = new User("k", 10);
		User l = new User("l", 11);
		
		g.addUser(a);
		g.addUser(b);
		g.addUser(c);
		g.addUser(d);
		g.addUser(e);
		g.addUser(f);
		g.addUser(h);
		g.addUser(i);
		g.addUser(j);
		g.addUser(k);
		g.addUser(l);
		
		g.addEdge(b, c);
		g.addEdge(d, e);
		g.addEdge(e, f);
		g.addEdge(d, h);
		g.addEdge(f, i);
		g.addEdge(j, k);
		g.addEdge(k, l);
		
		g.perfectLimitedInfection(4, 40);
		assertEquals(a.getSiteAccessed(), 40);
		assertEquals(b.getSiteAccessed(), 2);
		assertEquals(c.getSiteAccessed(), 3);
		assertEquals(d.getSiteAccessed(), 4);
		assertEquals(e.getSiteAccessed(), 5);
		assertEquals(f.getSiteAccessed(), 6);
		assertEquals(h.getSiteAccessed(), 7);
		assertEquals(i.getSiteAccessed(), 8);
		assertEquals(j.getSiteAccessed(), 40);
		assertEquals(k.getSiteAccessed(), 40);
		assertEquals(l.getSiteAccessed(), 40);
	}
	
	//test when infected CCs aren't CCs with adjacent sizes, and function fails
	@Test(expected = UnsupportedOperationException.class)
	public void pliInfectLessThanNUnsupportedTest() {
		UserGraph g = new UserGraph();
		User b = new User("b", 2);
		User c = new User("c", 3);
		User d = new User("d", 4);
		User e = new User("e", 5);
		User f = new User("f", 6);
		User h = new User("h", 7);
		User i = new User("i", 8);
		User j = new User("j", 9);
		User k = new User("k", 10);
		User l = new User("l", 11);
		User m = new User("m", 12);
		User n = new User("n", 13);
		User o = new User("o", 14);
		User p = new User("p", 15);
		
		
		g.addUser(b);
		g.addUser(c);
		g.addUser(d);
		g.addUser(e);
		g.addUser(f);
		g.addUser(h);
		g.addUser(i);
		g.addUser(j);
		g.addUser(k);
		g.addUser(l);
		g.addUser(m);
		g.addUser(n);
		g.addUser(o);
		g.addUser(p);
		
		g.addEdge(b, c);
		g.addEdge(d, e);
		g.addEdge(e, f);
		g.addEdge(d, h);
		g.addEdge(h, m);
		g.addEdge(h, n);
		g.addEdge(h, p);
		g.addEdge(f, i);
		g.addEdge(j, k);
		g.addEdge(k, l);
		g.addEdge(j, o);
		
		g.perfectLimitedInfection(7, 40);
	}
	
	//test in the case of a tie between two subsets of CC
	@Test
	public void pliCCTieTest() {
		UserGraph g = new UserGraph();
		User a = new User("a", 1);
		User b = new User("b", 2);
		User c = new User("c", 3);
		User d = new User("d", 4);
		User e = new User("e", 5);
		User f = new User("f", 6);
		User h = new User("h", 7);
		User i = new User("i", 8);
		User j = new User("j", 9);
		User k = new User("k", 10);
		
		g.addUser(a);
		g.addUser(b);
		g.addUser(c);
		g.addUser(d);
		g.addUser(e);
		g.addUser(f);
		g.addUser(h);
		g.addUser(i);
		g.addUser(j);
		g.addUser(k);
		
		g.addEdge(b, c);
		g.addEdge(d, e);
		g.addEdge(e, f);
		g.addEdge(i, h);
		g.addEdge(i, k);
		g.addEdge(i, j);
		
		g.perfectLimitedInfection(5, 40);
		boolean condition1 = (a.getSiteAccessed() == 40 && b.getSiteAccessed() == 2 && 
				c.getSiteAccessed() == 3 && d.getSiteAccessed() == 4 && e.getSiteAccessed() == 5
				&& f.getSiteAccessed() == 6 && i.getSiteAccessed() == 40 && 
				j.getSiteAccessed() == 40 && k.getSiteAccessed() == 40);
		boolean condition2 = (a.getSiteAccessed() == 1 && b.getSiteAccessed() == 40 && 
				c.getSiteAccessed() == 40 && d.getSiteAccessed() == 40 && e.getSiteAccessed() == 40
				&& f.getSiteAccessed() == 40 && i.getSiteAccessed() == 8 && 
				j.getSiteAccessed() == 9 && k.getSiteAccessed() == 10);
		
		
		assertTrue(condition1 || condition2);
	}

}
