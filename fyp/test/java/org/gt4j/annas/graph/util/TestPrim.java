package org.gt4j.annas.graph.util;

import static org.junit.Assert.assertTrue;

import org.gt4j.annas.DefaultWeightedEdge;
import org.gt4j.annas.graph.DirectedGraph;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.graph.WeightedEdgeInterface;
import org.junit.Before;
import org.junit.Test;

public class TestPrim {

	private UndirectedGraph<String, DefaultWeightedEdge> graph;

	private DirectedGraph<String, DefaultWeightedEdge> Dirgraph;

	private Prim<String, DefaultWeightedEdge> prim;

	String a = "A"; // u0
	String b = "B";// u1
	String c = "C";// u2
	String d = "D";// u3
	String e = "E";// u4
	String f = "F";// u5

	private WeightedEdgeInterface<String> e0;
	private WeightedEdgeInterface<String> e1;
	private WeightedEdgeInterface<String> e2;
	private WeightedEdgeInterface<String> e3;
	private WeightedEdgeInterface<String> e4;
	private WeightedEdgeInterface<String> e5;
	private WeightedEdgeInterface<String> e6;

	@Before
	public void setUp() throws Exception {

		this.graph = new UndirectedGraph<String, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);

		this.Dirgraph = new DirectedGraph<String, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);

		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);

		/*
		 * graph.addArc(a, e, new DefaultWeight(4d)); graph.addArc(a, c, new
		 * DefaultWeight(3d)); graph.addArc(e, c, new DefaultWeight(0d));
		 * graph.addArc(c, d, new DefaultWeight(8d)); graph.addArc(c, b, new
		 * DefaultWeight(9d)); graph.addArc(c, f, new DefaultWeight(3d));
		 * graph.addArc(b, f, new DefaultWeight(5d));
		 */

		e0 = graph.addEdge(a, e);
		e0.setWeight(4d);
		e1 = graph.addEdge(a, c);
		e1.setWeight(3d);
		e2 = graph.addEdge(e, c);
		e2.setWeight(0d);
		e3 = graph.addEdge(c, d);
		e3.setWeight(8d);
		e4 = graph.addEdge(c, b);
		e4.setWeight(9d);
		e5 = graph.addEdge(c, f);
		e5.setWeight(3d);
		e6 = graph.addEdge(b, f);
		e6.setWeight(5d);

	}

	@Test
	public void TestUndirected() {
		this.prim = new Prim<String, DefaultWeightedEdge>(this.graph);

		GraphInterface<String, DefaultWeightedEdge> g = this.prim.call();

		assertTrue(g.getOrder() == 6);
		assertTrue(g.getSize() == 5);

		assertTrue(g.containsEdge("A", "C"));
		assertTrue(g.containsEdge("C", "E"));
		assertTrue(g.containsEdge("C", "F"));
		assertTrue(g.containsEdge("F", "B"));
		assertTrue(g.containsEdge("C", "D"));
		assertTrue(this.prim.getWeight() == 19);
	}

	@Test
	public void TestDirected() {
		try {
			this.prim = new Prim<String, DefaultWeightedEdge>(this.Dirgraph);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

	}

}
