package org.gt4j.annas.graph.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.DirectedGraph;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.SimpleUndirectedGraph;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.math.Matrix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUtilities {

	private GraphInterface<String, DefaultEdge> graph;

	private String a;
	private String b;
	private String c;
	private String d;
	private String e;
	private String f;


	@Before
	public void setUp() throws Exception {

		this.a = new String("A");
		this.b = new String("B");
		this.c = new String("C");
		this.d = new String("D");
		this.e = new String("E");
		this.f = new String("F");

	}

	@After
	public void tearDown() throws Exception {

		this.a = null;
		this.b = null;
		this.c = null;
		this.d = null;
		this.e = null;
		this.f = null;
		this.graph = null;

		System.gc();
	}

	@Test
	public void testGetAdjacencyMatrix() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);
		Matrix t = new Matrix(new double[][] {
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f } });

		assertTrue(Utilities.getAdjacencyMatrix(graph).eq(t));

		this.graph.addEdge(a, b);
		this.graph.addEdge(a, e);
		this.graph.addEdge(e, b);
		this.graph.addEdge(e, d);
		this.graph.addEdge(d, c);
		this.graph.addEdge(c, b);
		this.graph.addEdge(d, f);

		t = new Matrix(new double[][] { { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f },
				{ 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f },
				{ 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f },
				{ 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f },
				{ 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f } });
		assertTrue(Utilities.getAdjacencyMatrix(graph).eq(t));

	}

	@Test
	public void testGetAdjacencyMatrixDirected() {
		this.graph = new DirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);
		Matrix t = new Matrix(new double[][] {
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f } });

		assertTrue(Utilities.getAdjacencyMatrix(graph).eq(t));

		this.graph.addEdge(a, b);
		this.graph.addEdge(a, e);
		this.graph.addEdge(e, b);
		this.graph.addEdge(e, d);
		this.graph.addEdge(d, c);
		this.graph.addEdge(c, b);
		this.graph.addEdge(d, f);

		t = new Matrix(new double[][] { { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f },
				{ 0.0f, 1.0f, .0f, 0.0f, 1.0f, 0.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f },
				{ 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f } });

		assertTrue(Utilities.getAdjacencyMatrix(graph).eq(t));

	}

	@Test
	public void testGetConnectedComponents() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(d, c);
		this.graph.addEdge(e, f);

		Collection<Collection<String>> comps = Utilities
				.getConnectedComponents(graph);

		assertTrue(comps.size() == 3);

	}

	@Test
	public void testGetComplement() {
		//TODO
	}

	@Test
	public void testGetClosedNeighbourhood() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(a, c);
		this.graph.addEdge(a, f);

		Collection<String> V = Utilities.getClosedNeighbourhood(graph, a);

		assertTrue(V.contains(a));
		assertTrue(V.contains(b));
		assertTrue(V.contains(c));
		assertTrue(V.contains(f));
		assertFalse(V.contains(e));
	}

	@Test
	public void testClosedNNeighborhood() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(a, c);
		this.graph.addEdge(a, f);
		this.graph.addEdge(f, e);

		Collection<String> V = Utilities.getClosedNNeighborhood(graph, a, 2);

		assertTrue(V.contains(a));
		assertTrue(V.contains(b));
		assertTrue(V.contains(c));
		assertTrue(V.contains(f));
		assertTrue(V.contains(e));

		assertFalse(V.contains(d));
	}

	@Test
	public void testGetOpenNeighbourhood() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(a, c);
		this.graph.addEdge(a, f);

		Collection<String> V = Utilities.getOpenNeighbourhood(graph, a);

		assertFalse(V.contains(a));
		assertTrue(V.contains(b));
		assertTrue(V.contains(c));
		assertTrue(V.contains(f));
		assertFalse(V.contains(e));
	}

	@Test
	public void testOpenNNeighborhood() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(a, c);
		this.graph.addEdge(a, f);
		this.graph.addEdge(f, e);

		Collection<String> V = Utilities.getOpenNNeighborhood(graph, a, 2);

		assertTrue(!V.contains(a));
		assertTrue(V.contains(b));
		assertTrue(V.contains(c));
		assertTrue(V.contains(f));
		assertTrue(V.contains(e));

		assertFalse(V.contains(d));
	}

	@Test
	public void testGetEvenDegreeVertices() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(c, d);
		this.graph.addEdge(e, f);

		Collection<String> V = Utilities.getEvenDegreeVertices(graph);

		assertTrue(V.isEmpty());

		this.graph.addEdge(b, c);
		V = Utilities.getEvenDegreeVertices(graph);
		assertTrue(V.contains(b));
		assertTrue(V.contains(c));
		this.graph.addEdge(d, e);
		V = Utilities.getEvenDegreeVertices(graph);
		assertTrue(V.contains(d));
		assertTrue(V.contains(e));
		this.graph.addEdge(a, f);
		V = Utilities.getEvenDegreeVertices(graph);
		assertTrue(V.contains(a));
		assertTrue(V.contains(f));
	}

	@Test
	public void testGetOddDegreeVertices() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(c, d);
		this.graph.addEdge(e, f);

		Collection<String> V = Utilities.getOddDegreeVertices(graph);

		assertTrue(V.size() == 6);

		this.graph.addEdge(b, c);
		V = Utilities.getOddDegreeVertices(graph);
		assertFalse(V.contains(b));
		assertFalse(V.contains(c));
		this.graph.addEdge(d, e);
		V = Utilities.getOddDegreeVertices(graph);
		assertFalse(V.contains(d));
		assertFalse(V.contains(e));
		this.graph.addEdge(a, f);
		V = Utilities.getOddDegreeVertices(graph);
		assertFalse(V.contains(a));
		assertFalse(V.contains(f));
	}

	@Test
	public void testGetVerticesOfDegree() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(a, d);
		this.graph.addEdge(a, f);

		Collection<String> V = Utilities.getVerticesOfDegree(graph, 3);
		assertTrue(V.contains(a));
		assertTrue(V.size() == 1);

	}

	@Test
	public void testMinimumDegree() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);

		this.graph.addEdge(a, b);
		this.graph.addEdge(b, c);
		this.graph.addEdge(c, a);

		assertTrue(Utilities.getMinimumDegree(graph) == 2);

		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		assertTrue(Utilities.getMinimumDegree(graph) == 0);
		this.graph.addEdge(d, e);
		this.graph.addEdge(e, f);
		this.graph.addEdge(f, d);
		assertTrue(Utilities.getMinimumDegree(graph) == 2);

		this.graph.removeEdge(a, b);
		assertTrue(Utilities.getMinimumDegree(graph) == 1);

	}

	@Test
	public void testMaximumDegree() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);

		assertTrue(Utilities.getMaximumDegree(graph) == 0);

		this.graph.addEdge(a, b);
		this.graph.addEdge(b, c);
		this.graph.addEdge(c, a);
		this.graph.removeEdge(a, b);

		assertTrue(Utilities.getMaximumDegree(graph) == 2);

		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		assertTrue(Utilities.getMaximumDegree(graph) == 2);
		this.graph.addEdge(d, e);
		this.graph.addEdge(e, f);
		this.graph.addEdge(f, d);
		assertTrue(Utilities.getMaximumDegree(graph) == 2);

	}

	@Test
	public void testGetDiameter() {
		//TODO
	}

	@Test
	public void testIsEmpty() {
		this.graph = new UndirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		assertTrue(Utilities.isEmpty(graph));
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		assertTrue(Utilities.isEmpty(graph));
		this.graph.addEdge(a, b);
		assertFalse(Utilities.isEmpty(graph));
		this.graph.removeVertex(a);
		assertTrue(Utilities.isEmpty(graph));
	}

	@Test
	public void testConstructPowerGraph() {
		//TODO
	}

	@Test
	public void testGetDensity() {
		this.graph = new SimpleUndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(a, d);
		this.graph.addEdge(a, f);

		assertTrue(Utilities.getDensity(graph) == 3 / 15);

		this.graph.addEdge(e, d);
		assertTrue(Utilities.getDensity(graph) == 4 / 15);
	}

	@Test
	public void testGetAverageDegree() {
		this.graph = new SimpleUndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		assertTrue(Utilities.getAverageDegree(graph) == 0);

		this.graph.addEdge(a, b);

	
		assertTrue(Utilities.getAverageDegree(graph) == 2d / this.graph
				.getOrder());
		this.graph.addEdge(a, d);
		assertTrue(Utilities.getAverageDegree(graph) == 4d / this.graph
				.getOrder());
		this.graph.addEdge(a, f);
		assertTrue(Utilities.getAverageDegree(graph) == 6d / this.graph
				.getOrder());
	}

	@Test
	public void testGetDegreeHistograph() {
		this.graph = new SimpleUndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		long[] hist = Utilities.getDegreeHistograph(graph);
		
		
		this.graph.addEdge(a, b);
		hist = Utilities.getDegreeHistograph(graph);
		assertTrue(hist.length == 2);
		assertTrue(hist[0] == 4);
		assertTrue(hist[1] == 2);
		
	}

	@Test
	public void testGetDegreeSequence() {
		this.graph = new SimpleUndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(a, d);
		this.graph.addEdge(a, f);
		long[] i = Utilities.getDegreeSequence(graph);
		assertTrue(i[0] == 3);
		assertTrue(i[1] == 1);
		assertTrue(i[2] == 0);
		assertTrue(i[3] == 1);
		assertTrue(i[4] == 0);
		assertTrue(i[5] == 1);
	}

	@Test
	public void testIsIndependentSet() {
		this.graph = new SimpleUndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(b, c);
		this.graph.addEdge(a, c);
		
		assertTrue(Utilities.isIndependentSet(graph, a,f,e));
		assertFalse(Utilities.isIndependentSet(graph, a,b,c));
	}
	
	@Test
	public void testIsIndependentSetI() {
		this.graph = new SimpleUndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(b, c);
		this.graph.addEdge(a, c);
		
		Set<String> v = new HashSet<String>();
		v.add(a);
		v.add(f);
		v.add(e);
		assertTrue(Utilities.isIndependentSet(graph, v));
		
		v = new HashSet<String>();
		v.add(a);
		v.add(b);
		v.add(c);
		assertFalse(Utilities.isIndependentSet(graph, v));
	}

	@Test
	public void testIsClique() {
		this.graph = new SimpleUndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(b, c);
		this.graph.addEdge(a, c);
		
		assertTrue(Utilities.isClique(graph, a,b,c));
		assertFalse(Utilities.isClique(graph, a,b,f));
	}
	
	@Test
	public void testIsCliqueI() {
		this.graph = new SimpleUndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);
		this.graph.addVertex(a);
		this.graph.addVertex(b);
		this.graph.addVertex(c);
		this.graph.addVertex(d);
		this.graph.addVertex(e);
		this.graph.addVertex(f);

		this.graph.addEdge(a, b);
		this.graph.addEdge(b, c);
		this.graph.addEdge(a, c);
		
		Set<String> v = new HashSet<String>();
		v.add(a);
		v.add(b);
		v.add(c);
		assertTrue(Utilities.isClique(graph, v));
		
		v = new HashSet<String>();
		v.add(a);
		v.add(b);
		v.add(f);
		assertFalse(Utilities.isClique(graph, v));
	}

}
