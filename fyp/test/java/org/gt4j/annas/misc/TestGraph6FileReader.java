package org.gt4j.annas.misc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.IntegerEdge;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGraph6FileReader {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIterator() {
		Graph6FileReader g6r = new Graph6FileReader(
				"test_data/graph6/graph3.g6");
		assertTrue(g6r.iterator() instanceof Iterator);
		Iterator<GraphInterface<Integer, IntegerEdge>> i = g6r.iterator();
		GraphInterface<Integer, IntegerEdge> g;
		assertTrue(i.hasNext());
		g = i.next();
		assertTrue(g.getOrder() == 3);
		assertTrue(g.getSize() == 0);
		assertTrue(i.hasNext());
		g = i.next();
		assertTrue(g.getOrder() == 3);
		assertTrue(g.getSize() == 1);
		g = i.next();
		assertTrue(g.getOrder() == 3);
		assertTrue(g.getSize() == 2);
		g = i.next();
		assertTrue(g.getOrder() == 3);
		assertTrue(g.getSize() == 3);
		assertFalse(i.hasNext());
	}

}
