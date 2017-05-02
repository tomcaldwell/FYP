package org.gt4j.annas.graph.util;

import static org.junit.Assert.assertTrue;

import org.gt4j.annas.DefaultWeightedEdge;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.misc.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDijkstra {

	private UndirectedGraph<String, DefaultWeightedEdge> graph;

	private Dijkstra<String, DefaultWeightedEdge> dij;

	String a = "A"; // u0
	String b = "B";// u1
	String c = "C";// u2
	String d = "D";// u3
	String e = "E";// u4
	String f = "F";// u5
	String g = "G";// u5

	Path<String, DefaultWeightedEdge> gp;

	@Before
	public void setUp() throws Exception {

		graph = new UndirectedGraph<String, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);

		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addVertex(g);

		DefaultWeightedEdge e1 = graph.addEdge(a, c);
		e1.setWeight(13);
		e1 = graph.addEdge(a, f);
		e1.setWeight(8);
		e1 = graph.addEdge(a, e);
		e1.setWeight(16);
		e1 = graph.addEdge(b, f);
		e1.setWeight(10);
		e1 = graph.addEdge(b, d);
		e1.setWeight(6);
		e1 = graph.addEdge(c, e);
		e1.setWeight(11);
		e1 = graph.addEdge(c, d);
		e1.setWeight(14);
		e1 = graph.addEdge(d, e);
		e1.setWeight(5);
		e1 = graph.addEdge(d, f);
		e1.setWeight(17);
		e1 = graph.addEdge(e, f);
		e1.setWeight(7);

		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void AB() {
		dij = new Dijkstra<String, DefaultWeightedEdge>(graph,a,b);
		this.gp = dij.call();
		assertTrue(gp.getLength() == 3);
		assertTrue(gp.getDistance() == 18);
	}

	@Test
	public void AC() {
		dij = new Dijkstra<String, DefaultWeightedEdge>(graph,a,c);
		this.gp = dij.call();
		assertTrue(gp.getLength() == 2);
		assertTrue(gp.getDistance() == 13);
	}

	@Test
	public void AD() {
		dij = new Dijkstra<String, DefaultWeightedEdge>(graph,a,d);
		this.gp = dij.call();
		assertTrue(gp.getLength() == 4);
		assertTrue(gp.getDistance() == 20);
	}

	@Test
	public void AE() {
		dij = new Dijkstra<String, DefaultWeightedEdge>(graph,a,e);
		this.gp = dij.call();
		assertTrue(gp.getLength() == 3);
		assertTrue(gp.getDistance() == 15);
	}

	@Test
	public void AF() {
		dij = new Dijkstra<String, DefaultWeightedEdge>(graph,a,f);
		this.gp = dij.call();
		assertTrue(gp.getLength() == 2);
		assertTrue(gp.getDistance() == 8);
	}

	@Test
	public void AG() {
		dij = new Dijkstra<String, DefaultWeightedEdge>(graph,a,g);
		this.gp = dij.call();
		assertTrue(gp.getLength() == 0);
		assertTrue(gp.getDistance() == 0);
	}

}
