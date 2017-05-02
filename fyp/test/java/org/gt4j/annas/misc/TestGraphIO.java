package org.gt4j.annas.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.gt4j.annas.DataPath;
import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.IntegerEdge;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.graph.io.Graph6Exporter;
import org.gt4j.annas.graph.io.Graph6Importer;
import org.gt4j.annas.graph.io.GraphExporter;
import org.gt4j.annas.graph.io.GraphImporter;
import org.gt4j.annas.graph.io.TGFExporter;
import org.gt4j.annas.graph.io.TGFImporter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestGraphIO {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGraph6() throws IOException {
		final GraphImporter<Integer, IntegerEdge> gi = new Graph6Importer();

		final UndirectedGraph<Integer, IntegerEdge> graph = new UndirectedGraph<Integer, IntegerEdge>(
				IntegerEdge.class);
		gi.importGraph(DataPath.PATH + "graph6/graph2.g6", graph);

		Assert.assertTrue(graph.getOrder() == 2);
		Assert.assertTrue(graph.getSize() == 0);

		graph.addVertices(2, 3, 4, 5, 6);
		graph.addEdge(0, 1);
		graph.addEdge(5, 6);
		graph.addEdge(4, 5);
		graph.addEdge(4, 6);

		final GraphExporter<Integer, IntegerEdge> ge = new Graph6Exporter<Integer, IntegerEdge>();

		final File F = new File(DataPath.PATH + "Graph6.tmp");

		F.createNewFile();
		F.deleteOnExit();
		ge.exportGraph(new FileOutputStream(F), graph);

		final UndirectedGraph<Integer, IntegerEdge> graph1 = new UndirectedGraph<Integer, IntegerEdge>(
				IntegerEdge.class);
		gi.importGraph(DataPath.PATH + "Graph6.tmp", graph1);

		Assert.assertTrue(graph1.getOrder() == 7);
		Assert.assertTrue(graph1.getSize() == 4);

	}

	@Test
	public void testTGF() throws IOException {
		final GraphImporter<String, DefaultEdge> gi = new TGFImporter();

		final UndirectedGraph<String, DefaultEdge> graph = new UndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);

		graph.addVertices("0", "1", "2", "3", "4", "5", "6");
		graph.addEdge("0", "1");
		graph.addEdge("5", "6");
		graph.addEdge("4", "5");
		graph.addEdge("4", "6");

		final GraphExporter<String, DefaultEdge> ge = new TGFExporter<String, DefaultEdge>();

		final File F = new File(DataPath.PATH + "TGFgraph.tmp");
		F.createNewFile();
		F.deleteOnExit();
		ge.exportGraph(new FileOutputStream(F), graph);

		final UndirectedGraph<String, DefaultEdge> graph1 = new UndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);
		gi.importGraph(DataPath.PATH + "TGFgraph.tmp", graph1);

		Assert.assertTrue(graph1.getOrder() == 7);
		Assert.assertTrue(graph1.getSize() == 4);
		Assert.assertTrue(graph1.containsEdge("0", "1"));
		Assert.assertTrue(graph1.containsEdge("5", "6"));
		Assert.assertTrue(graph1.containsEdge("4", "5"));
		Assert.assertTrue(graph1.containsEdge("4", "6"));

		Assert.assertFalse(graph1.containsEdge("0", "6"));
		Assert.assertFalse(graph1.containsEdge("5", "1"));
		Assert.assertFalse(graph1.containsEdge("4", "1"));
	}

}
