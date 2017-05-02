package org.gt4j.annas.graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUndirectedGraph {

	private GraphInterface<String, DefaultEdge> graph;

	private String a;
	private String b;
	private String c;
	private String d;


	@Before
	public void setUp() throws Exception {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		this.a = new String("A");
		this.b = new String("B");
		this.c = new String("C");
		this.d = new String("D");
	}

	@After
	public void tearDown() throws Exception {
		this.a = null;
		this.b = null;
		this.c = null;
		this.d = null;

		this.graph = null;
		System.gc();
	}

	@Test
	public void testUndirectedGraphEdgeFactoryOfVE() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(
				new ClassEdgeFactory<String, DefaultEdge>(DefaultEdge.class));
		assertTrue(this.graph != null);
	}

	@Test
	public void testUndirectedGraphClassOfQextendsE() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		assertTrue(this.graph != null);
	}

	@Test
	public void testAddEdge() {
		this.graph.addVertex(a);
		this.graph.addVertex(b);

		assertTrue(this.graph.getSize() == 0);
		DefaultEdge e1 = this.graph.addEdge(a, b);
		assertTrue(this.graph.getSize() == 1);
		DefaultEdge e2 = this.graph.addEdge(a, b);
		assertTrue(this.graph.getSize() == 2);
		assertTrue(this.graph.getEdges(b, a).contains(e1));
		assertTrue(this.graph.getEdges(b, a).contains(e2));
		
		DefaultEdge e3 = this.graph.addEdge(a, a);
		assertNotNull(e3);
		assertTrue(this.graph.getEdges(a).contains(e3));
	}

	@Test
	public void testAddVertex() {
		assertTrue(this.graph.getOrder() == 0);
		this.graph.addVertex(a);
		assertTrue(this.graph.getOrder() == 1);
		this.graph.addVertex(b);
		assertTrue(this.graph.getOrder() == 2);
	}

	@Test
	public void testGetEdgesV() {
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);

		DefaultEdge e1 = this.graph.addEdge(a, b);
		DefaultEdge e2 = this.graph.addEdge(b, c);
		DefaultEdge e3 = this.graph.addEdge(c, d);
		DefaultEdge e4 = this.graph.addEdge(d, a);
		DefaultEdge e5 = this.graph.addEdge(d, a);

		assertTrue(this.graph.getEdges(a).contains(e1));
		assertTrue(this.graph.getEdges(b).contains(e2));
		assertTrue(this.graph.getEdges(c).contains(e3));
		assertTrue(this.graph.getEdges(d).contains(e4));

		assertTrue(this.graph.getEdges(a).contains(e4));
		assertTrue(this.graph.getEdges(b).contains(e1));
		assertTrue(this.graph.getEdges(c).contains(e2));
		assertTrue(this.graph.getEdges(d).contains(e3));
		
		assertTrue(this.graph.getEdges(a).size() ==3);
		Collection<DefaultEdge> es = this.graph.getEdges(a);
		assertTrue(es.contains(e1));
		assertTrue(es.contains(e4));
		assertTrue(es.contains(e5));
		assertFalse(es.contains(e3));

	}

	@Test
	public void testContainsE() {
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		DefaultEdge edge = this.graph.addEdge(a, b);
		assertTrue(this.graph.containsEdge(edge));
	}

	@Test
	public void testGetEdgesVV() {
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);

		DefaultEdge e1 = this.graph.addEdge(a, b);
		DefaultEdge e2 = this.graph.addEdge(b, c);
		DefaultEdge e3 = this.graph.addEdge(c, d);
		DefaultEdge e4 = this.graph.addEdge(d, a);

		assertTrue(this.graph.getEdges(a, b).contains(e1));
		assertTrue(this.graph.getEdges(b, c).contains(e2));
		assertTrue(this.graph.getEdges(c, d).contains(e3));
		assertTrue(this.graph.getEdges(d, a).contains(e4));

		assertTrue(this.graph.getEdges(a, d).contains(e4));
		assertTrue(this.graph.getEdges(b, a).contains(e1));
		assertTrue(this.graph.getEdges(c, b).contains(e2));
		assertTrue(this.graph.getEdges(d, c).contains(e3));
		
		assertTrue(this.graph.getEdges(d, b).isEmpty());
		assertTrue(this.graph.getEdges(c, a).isEmpty());

	}

	@Test
	public void testGetEdges() {
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);

		DefaultEdge e1 = this.graph.addEdge(a, b);
		DefaultEdge e2 = this.graph.addEdge(b, c);
		DefaultEdge e3 = this.graph.addEdge(c, d);
		DefaultEdge e4 = this.graph.addEdge(d, a);

		assertTrue(this.graph.getEdges().contains(e1));
		assertTrue(this.graph.getEdges().contains(e2));
		assertTrue(this.graph.getEdges().contains(e3));
		assertTrue(this.graph.getEdges().contains(e4));
		assertTrue(this.graph.getEdges().size() == 4);

	}

	@Test
	public void testRemoveEdgeE() {
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);

		DefaultEdge e1 = this.graph.addEdge(a, b);
		DefaultEdge e2 = this.graph.addEdge(b, c);
		DefaultEdge e3 = this.graph.addEdge(c, d);
		DefaultEdge e4 = this.graph.addEdge(d, a);

		assertTrue(this.graph.getEdges().contains(e1));
		assertTrue(this.graph.getEdges().contains(e2));
		assertTrue(this.graph.getEdges().contains(e3));
		assertTrue(this.graph.getEdges().contains(e4));
		assertTrue(this.graph.getEdges().size() == 4);

		this.graph.removeEdge(e1);
		assertFalse(this.graph.getEdges().contains(e1));
		this.graph.removeEdge(e2);
		assertFalse(this.graph.getEdges().contains(e2));
		this.graph.removeEdge(e3);
		assertFalse(this.graph.getEdges().contains(e3));
		this.graph.removeEdge(e4);
		assertFalse(this.graph.getEdges().contains(e4));
		assertTrue(this.graph.getEdges().size() == 0);
	}

	@Test
	public void testRemoveEdgeVV() {
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);

		DefaultEdge e1 = this.graph.addEdge(a, b);
		DefaultEdge e2 = this.graph.addEdge(b, c);
		DefaultEdge e3 = this.graph.addEdge(c, d);
		DefaultEdge e4 = this.graph.addEdge(d, a);

		assertTrue(this.graph.getEdges().contains(e1));
		assertTrue(this.graph.getEdges().contains(e2));
		assertTrue(this.graph.getEdges().contains(e3));
		assertTrue(this.graph.getEdges().contains(e4));
		assertTrue(this.graph.getEdges().size() == 4);

		this.graph.removeEdge(a, b);
		assertFalse(this.graph.getEdges().contains(e1));
		this.graph.removeEdge(b, c);
		assertFalse(this.graph.getEdges().contains(e2));
		this.graph.removeEdge(d, c);
		assertFalse(this.graph.getEdges().contains(e3));
		this.graph.removeEdge(d, a);
		assertFalse(this.graph.getEdges().contains(e4));
		assertTrue(this.graph.getEdges().size() == 0);
	}

	@Test
	public void testRemoveEdgeV() {
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);

		DefaultEdge e1 = this.graph.addEdge(a, b);
		DefaultEdge e2 = this.graph.addEdge(b, c);
		DefaultEdge e3 = this.graph.addEdge(c, d);
		DefaultEdge e4 = this.graph.addEdge(d, a);

		assertTrue(this.graph.getEdges().contains(e1));
		assertTrue(this.graph.getEdges().contains(e2));
		assertTrue(this.graph.getEdges().contains(e3));
		assertTrue(this.graph.getEdges().contains(e4));
		assertTrue(this.graph.getEdges().size() == 4);

		assertTrue(this.graph.getOrder() == 4);
		this.graph.removeEdge(a);
		assertTrue(this.graph.getOrder() == 4);
		assertTrue(this.graph.getEdges().size() == 2);
		assertTrue(this.graph.getEdges().contains(e2));
		assertTrue(this.graph.getEdges().contains(e3));
	}

	@Test
	public void testRemoveVertex() {
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);

		DefaultEdge e1 = this.graph.addEdge(a, b);
		DefaultEdge e2 = this.graph.addEdge(b, c);
		DefaultEdge e3 = this.graph.addEdge(c, d);
		DefaultEdge e4 = this.graph.addEdge(d, a);

		assertTrue(this.graph.getEdges().contains(e1));
		assertTrue(this.graph.getEdges().contains(e2));
		assertTrue(this.graph.getEdges().contains(e3));
		assertTrue(this.graph.getEdges().contains(e4));
		assertTrue(this.graph.getEdges().size() == 4);

		assertTrue(this.graph.getOrder() == 4);
		this.graph.removeVertex(a);
		assertTrue(this.graph.getOrder() == 3);
		assertTrue(this.graph.getEdges().size() == 2);
		assertTrue(this.graph.getEdges().contains(e2));
		assertTrue(this.graph.getEdges().contains(e3));

	}

}
