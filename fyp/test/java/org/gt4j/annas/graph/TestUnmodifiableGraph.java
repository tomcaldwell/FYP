package org.gt4j.annas.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.gt4j.annas.exception.UnmodifiableGraphException;

public class TestUnmodifiableGraph {

	private GraphInterface<String, DefaultEdge> graph;

	private String a;
	private String b;
	private String c;
	private String d;

	@Before
	public void setUp() throws Exception {
		this.a = new String("A");
		this.b = new String("B");
		this.c = new String("C");
		this.d = new String("D");

		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addEdge(a, b);
		this.graph = new UnmodifiableGraph<String, DefaultEdge>(this.graph);
	}

	@Test
	public void testUnmodifiableGraph() {
		assertNotNull(this.graph);
	}

	@Test(expected = UnmodifiableGraphException.class)
	public void testAddEdge() {
		this.graph.addEdge(a, c);
	}

	@Test(expected = UnmodifiableGraphException.class)
	public void testAddVertex() {
		this.graph.addVertex(d);
	}

	@Test
	public void testContainsEdgeVV() {
		assertTrue(this.graph.containsEdge(a, b));
		assertFalse(this.graph.containsEdge(b, c));
	}

	@Test
	public void testContainsEdgeE() {
		assertTrue(this.graph.containsEdge(this.graph.getEdges().iterator().next()));
	}

	@Test
	public void testContainsVertex() {
		assertTrue(this.graph.containsVertex(a));
		assertFalse(this.graph.containsVertex(d));
	}

	@Test
	public void testGetDegree() {
		assertEquals(this.graph.getDegree(a), 1);
	}

	@Test
	public void testGetEdgeFactory() {
		assertNotNull(this.graph.getEdgeFactory());
	}

	@Test
	public void testGetEdgesV() {
		assertEquals(this.graph.getEdges(a).size(), 1);
	}

	@Test
	public void testGetEdgesVV() {
		assertEquals(this.graph.getEdges(a, b).size(), 1);
		assertEquals(this.graph.getEdges(a, c).size(), 0);
	}

	@Test
	public void testGetEdges() {
		assertEquals(this.graph.getEdges().size(), 1);
	}

	@Test
	public void testGetObserver() {
		assertNull(this.graph.getObserver());
	}

	@Test
	public void testGetOrder() {
		assertEquals(this.graph.getOrder(), 3);
	}

	@Test
	public void testGetSize() {
		assertEquals(this.graph.getSize(), 1);
	}

	@Test
	public void testGetVertices() {
		assertEquals(this.graph.getVertices().size(), 3);
	}

	@Test(expected = UnmodifiableGraphException.class)
	public void testRemoveEdgeE() {

		this.graph.removeEdge(this.graph.getEdges().iterator().next());

	}

	@Test(expected = UnmodifiableGraphException.class)
	public void testRemoveEdgeVV() {

		this.graph.removeEdge(b, c);

	}

	@Test(expected = UnmodifiableGraphException.class)
	public void testRemoveEdgeV() {
		this.graph.removeEdge(c);

	}

	@Test(expected = UnmodifiableGraphException.class)
	public void testRemoveEdges() {

		this.graph.removeEdges(null);

	}

	@Test(expected = UnmodifiableGraphException.class)
	public void testRemoveVertex() {

		this.graph.removeVertex(d);

	}

	@Test(expected = UnmodifiableGraphException.class)
	public void testRemoveVertices() {

		this.graph.removeVertices(null);

	}

	@Test(expected = UnmodifiableGraphException.class)
	public void testResetEdges() {

		this.graph.resetEdges();

	}

	@Test(expected = UnmodifiableGraphException.class)
	public void testSetObserver() {

		this.graph.setObserver(null);

	}

}
