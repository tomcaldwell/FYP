package org.gt4j.annas.graph.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.DirectedGraph;
import org.gt4j.annas.graph.GraphInterface;
import org.junit.Before;
import org.junit.Test;

public class TestTarjan {

	private GraphInterface<String, DefaultEdge> graph;

	private String a;
	private String b;
	private String c;
	private String d;
	private String e;
	private String f;
	private String g;
	private String h;

	@Before
	public void setup() {
		this.graph = new DirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		this.a = new String("A");
		this.b = new String("B");
		this.c = new String("C");
		this.d = new String("D");
		this.e = new String("E");
		this.f = new String("F");
		this.g = new String("G");
		this.h = new String("H");

		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);
		this.graph.addVertex(g);
		this.graph.addVertex(h);

		this.graph.addEdge(a, b);
		this.graph.addEdge(b, e);
		this.graph.addEdge(e, a);
		this.graph.addEdge(e, f);
		this.graph.addEdge(b, f);
		this.graph.addEdge(f, g);
		this.graph.addEdge(g, f);
		this.graph.addEdge(b, c);
		this.graph.addEdge(c, g);
		this.graph.addEdge(c, d);
		this.graph.addEdge(d, c);
		this.graph.addEdge(d, h);
		this.graph.addEdge(h, d);
		this.graph.addEdge(h, g);
		this.graph.addEdge(c, g);

	}

	@Test
	public void testExecute() {
		Tarjan<String, DefaultEdge> t = new Tarjan<String, DefaultEdge>(
				this.graph);

		ArrayList<ArrayList<String>> k = t.execute();
		assertTrue(k.size() == 3);
	}

}
