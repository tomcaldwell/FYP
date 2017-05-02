package org.gt4j.annas.graph.io;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.misc.Graph6;

/**
 * Implementation of a graph exporter. Exports the given graph in graph6 file format.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class Graph6Exporter<V, E extends EdgeInterface<V>> implements
		GraphExporter<V, E> {

	@Override
	public void exportGraph(OutputStream writer, GraphInterface<V, E> graph) {
		new PrintWriter(writer, true).println(Graph6.encodeGraph(graph));
	}

	@Override
	public void exportGraph(GraphInterface<V, E> graph) {
		System.out.println(Graph6.encodeGraph(graph));
	}

}
