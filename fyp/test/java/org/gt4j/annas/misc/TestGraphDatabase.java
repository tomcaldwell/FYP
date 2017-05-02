package org.gt4j.annas.misc;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;

import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.IntegerEdge;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.gt4j.annas.misc.GraphDatabase;

public class TestGraphDatabase {

	private GraphDatabase gdb;

	private static String filename = "test_data/sample.db";

	@Before
	public void setUp() throws Exception {
		this.gdb = GraphDatabase.getSQLiteDatabase(filename);

		for (int z = 2; z < 6; z++) {

			Graph6FileReader g6r = new Graph6FileReader("test_data/graph6/graph" + z + ".g6");

			String line = null;

			for (GraphInterface<Integer, IntegerEdge> g : g6r) {
				line = Graph6.encodeGraph(g);
				gdb.insert("", line, g.getOrder(), g.getSize());
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		(new File(filename)).deleteOnExit();
	}

	@Test
	public void testGetGraphOfOrder() throws SQLException {
		GraphInterface<Integer, IntegerEdge> g = null;
		Iterator<GraphInterface<Integer, IntegerEdge>> i = this.gdb.getGraphOfOrder(3);
		for (; i.hasNext();) {
			g = i.next();
			assertTrue(g.getOrder() == 3);
		}

	}

	@Test
	public void testGetGraphOfOrderGreaterThan() throws SQLException {
		GraphInterface<Integer, IntegerEdge> g = null;
		Iterator<GraphInterface<Integer, IntegerEdge>> i = this.gdb.getGraphOfOrderGreaterThan(2);
		for (; i.hasNext();) {
			g = i.next();
			assertTrue(g.getOrder() > 2);
		}

	}

	@Test
	public void testGetGraphOfOrderLessThan() throws SQLException {
		GraphInterface<Integer, IntegerEdge> g = null;
		Iterator<GraphInterface<Integer, IntegerEdge>> i = this.gdb.getGraphOfOrderLessThan(3);
		for (; i.hasNext();) {
			g = i.next();
			assertTrue(g.getOrder() < 3);
		}

	}

	@Test
	public void testGetGraphOfSize() throws SQLException {
		GraphInterface<Integer, IntegerEdge> g = null;
		Iterator<GraphInterface<Integer, IntegerEdge>> i = this.gdb.getGraphOfSize(4);
		for (; i.hasNext();) {
			g = i.next();
			assertTrue(g.getSize() == 4);
		}

	}

	@Test
	public void testGetGraphOfSizeGreaterThan() throws SQLException {
		GraphInterface<Integer, IntegerEdge> g = null;
		Iterator<GraphInterface<Integer, IntegerEdge>> i = this.gdb.getGraphOfSizeGreaterThan(5);
		for (; i.hasNext();) {
			g = i.next();
			assertTrue(g.getSize() > 5);
		}
	}

	@Test
	public void testGetGraphOfSizeLessThan() throws SQLException {
		GraphInterface<Integer, IntegerEdge> g = null;
		Iterator<GraphInterface<Integer, IntegerEdge>> i = this.gdb.getGraphOfSizeLessThan(6);
		for (; i.hasNext();) {
			g = i.next();
			assertTrue(g.getSize() < 6);
		}
	}

	@Test
	public void testToString() {
		assertTrue(this.gdb.toString().equals("GraphDatabase: Connected to " + filename));

	}

}
