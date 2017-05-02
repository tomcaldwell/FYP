package org.gt4j.annas.graph.util.traverse;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDFTraversal {

	private GraphInterface<String, DefaultEdge> graph;

	private String a;
	private String b;
	private String c;
	private String d;
	private String e;
	private String f;
	private String g;

	private DepthFirst<String> traversal;

	@Before
	public void setUp() throws Exception {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		this.a = new String("A");
		this.b = new String("B");
		this.c = new String("C");
		this.d = new String("D");
		this.e = new String("E");
		this.f = new String("F");
		this.g = new String("G");

		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);
		this.graph.addVertex(g);

		this.graph.addEdge(a, b);
		this.graph.addEdge(a, c);
		this.graph.addEdge(b, d);
		this.graph.addEdge(b, e);
		this.graph.addEdge(c, f);
		this.graph.addEdge(c, g);

		this.traversal = new DepthFirst<String>(this.graph, a);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRunN() {
		Iterator<String> i = this.traversal.iterator();

		assertTrue(i.next() == a);
		assertTrue(i.next() == c);
		assertTrue(i.next() == g);
		assertTrue(i.next() == f);
		assertTrue(i.next() == b);
		assertTrue(i.next() == e);
		assertTrue(i.next() == d);

	}

}
