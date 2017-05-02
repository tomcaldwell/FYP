package org.gt4j.annas.graph.util;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.gt4j.annas.graph.DefaultWeightedEdge;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.graph.WeightedEdgeInterface;
import org.gt4j.annas.misc.Path;
import org.junit.Before;
import org.junit.Test;

public class TestFloyd {

	private GraphInterface<String, DefaultWeightedEdge> graph;

	private String a;
	private String b;
	private String c;
	private String d;
	private String e;
	private String f;

	private Floyd<String, DefaultWeightedEdge> floyd;

	@Before
	public void setup() {
		this.graph = new UndirectedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		this.a = new String("A");
		this.b = new String("B");
		this.c = new String("C");
		this.d = new String("D");
		this.e = new String("E");
		this.f = new String("F");

		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b).setWeight(5d);//, new DefaultWeight(5d));
		this.graph.addEdge(a, f).setWeight(1d);//, new DefaultWeight(1d));
		this.graph.addEdge(a, d).setWeight(7d);//, new DefaultWeight(7d));
		this.graph.addEdge(a, e).setWeight(3d);//, new DefaultWeight(3d));
		this.graph.addEdge(b, f).setWeight(1d);//, new DefaultWeight(1d));
		this.graph.addEdge(b, c).setWeight(4d);//, new DefaultWeight(4d));
		this.graph.addEdge(c, f).setWeight(1d);//, new DefaultWeight(1d));
		this.graph.addEdge(c, d).setWeight(2d);//, new DefaultWeight(2d));
		this.graph.addEdge(d, f).setWeight(50d);//, new DefaultWeight(50d));
		this.graph.addEdge(d, e).setWeight(3d);//, new DefaultWeight(3d));


		this.floyd = new Floyd<String, DefaultWeightedEdge>(this.graph);

	}

	@Test
	public void testGetDistance() {
		//Matrix m = new Matrix(this.floyd.getD());
		//m.print();
		assertTrue(this.floyd.getDistance(a, b) == 2d);
		assertTrue(this.floyd.getDistance(a, c) == 2d);
		assertTrue(this.floyd.getDistance(a, d) == 4d);
		assertTrue(this.floyd.getDistance(a, e) == 3d);
		assertTrue(this.floyd.getDistance(a, f) == 1d);
		assertTrue(this.floyd.getDistance(b, c) == 2d);
		assertTrue(this.floyd.getDistance(b, d) == 4d);
		assertTrue(this.floyd.getDistance(b, e) == 5d);
		assertTrue(this.floyd.getDistance(b, f) == 1d);
		assertTrue(this.floyd.getDistance(c, d) == 2d);
		assertTrue(this.floyd.getDistance(c, e) == 5d);
		assertTrue(this.floyd.getDistance(c, f) == 1d);
		assertTrue(this.floyd.getDistance(d, e) == 3d);
		assertTrue(this.floyd.getDistance(d, e) == 3d);
		assertTrue(this.floyd.getDistance(e, f) == 4d);
	}

	@Test
	public void testGetRoute() {
		Path<String, DefaultWeightedEdge> gp = this.floyd.getRoute(a, c);

		assertTrue(gp != null);
		assertTrue(gp.getLength() == 3);
		Iterator<DefaultWeightedEdge> i = gp.getIterator();

	
		WeightedEdgeInterface<String> edge = i.next();
		assertTrue(edge.getTail()== a);
		assertTrue(edge.getHead()== f);
		edge = i.next();
		assertTrue(edge.getHead()== f);
		assertTrue(edge.getTail()== c);

	}

}
